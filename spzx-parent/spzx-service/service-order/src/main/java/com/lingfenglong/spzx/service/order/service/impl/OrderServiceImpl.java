package com.lingfenglong.spzx.service.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.common.exception.CommonGlobalRuntimeException;
import com.lingfenglong.spzx.feign.cart.CartFeignClient;
import com.lingfenglong.spzx.feign.product.ProductFeignClient;
import com.lingfenglong.spzx.feign.user.UserAddressFeignClient;
import com.lingfenglong.spzx.model.dto.h5.OrderInfoDto;
import com.lingfenglong.spzx.model.entity.order.OrderInfo;
import com.lingfenglong.spzx.model.entity.order.OrderItem;
import com.lingfenglong.spzx.model.entity.order.OrderLog;
import com.lingfenglong.spzx.model.entity.product.ProductSku;
import com.lingfenglong.spzx.model.entity.user.UserAddress;
import com.lingfenglong.spzx.model.entity.user.UserInfo;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.h5.TradeVo;
import com.lingfenglong.spzx.service.order.mapper.OrderInfoMapper;
import com.lingfenglong.spzx.service.order.mapper.OrderItemMapper;
import com.lingfenglong.spzx.service.order.mapper.OrderLogMapper;
import com.lingfenglong.spzx.service.order.service.OrderInfoService;
import com.lingfenglong.spzx.util.AuthContextUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderServiceImpl
        extends ServiceImpl<OrderInfoMapper, OrderInfo>
        implements OrderInfoService {

    private final CartFeignClient cartFeignClient;
    private final ProductFeignClient productFeignClient;
    private final UserAddressFeignClient userAddressFeignClient;
    private final OrderItemMapper orderItemMapper;
    private final OrderLogMapper orderLogMapper;

    public OrderServiceImpl(
            CartFeignClient cartFeignClient,
            ProductFeignClient productFeignClient,
            UserAddressFeignClient userAddressFeignClient,
            OrderItemMapper orderItemMapper,
            OrderLogMapper orderLogMapper) {
        this.cartFeignClient = cartFeignClient;
        this.productFeignClient = productFeignClient;
        this.userAddressFeignClient = userAddressFeignClient;
        this.orderItemMapper = orderItemMapper;
        this.orderLogMapper = orderLogMapper;
    }

    @Override
    public TradeVo trade() {
        AtomicReference<BigDecimal> totalAmount = new AtomicReference<>();
        totalAmount.set(BigDecimal.ZERO);

        List<OrderItem> orderItemList = cartFeignClient.getAllChecked().getData()
                .stream()
                .peek(cartInfo -> totalAmount
                        .accumulateAndGet(
                                cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum())),
                                BigDecimal::add
                        )
                )
                .map(cartInfo -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setSkuId(cartInfo.getSkuId());
                    orderItem.setSkuName(cartInfo.getSkuName());
                    orderItem.setThumbImg(cartInfo.getImgUrl());
                    orderItem.setSkuPrice(cartInfo.getCartPrice());
                    orderItem.setSkuNum(cartInfo.getSkuNum());
                    return orderItem;
                })
                .toList();

        TradeVo tradeVo = new TradeVo();
        tradeVo.setTotalAmount(totalAmount.get());
        tradeVo.setOrderItemList(orderItemList);

        return tradeVo;
    }

    @Override
    public Long submitOrder(OrderInfoDto orderInfoDto) {
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();

        // 检查订单不为空
        checkOrderNotEmpty(orderItemList);

        // 检查库存充足
        checkStockEnough(orderItemList);

        // 创建订单
        OrderInfo orderInfo = createOrder(orderInfoDto);

        // 保存日志
        saveOrderLog(orderInfo);

        // 删除选中商品
        cartFeignClient.deleteChecked();

        return orderInfo.getId();
    }

    @Override
    public OrderInfo getOrderInfo(Long orderId) {
        return baseMapper.selectById(orderId);
    }

    @Override
    public TradeVo buy(Long skuId) {
        TradeVo tradeVo = new TradeVo();
        ProductSku productSku = productFeignClient.getBySkuId(skuId).getData();

        OrderItem orderItem = new OrderItem();
        orderItem.setSkuId(skuId);
        orderItem.setSkuName(productSku.getSkuName());
        orderItem.setThumbImg(productSku.getThumbImg());
        orderItem.setSkuPrice(productSku.getSalePrice());
        orderItem.setSkuNum(1);

        tradeVo.setTotalAmount(productSku.getSalePrice());
        tradeVo.setOrderItemList(Collections.singletonList(orderItem));

        return tradeVo;
    }

    @Override
    public PageInfo<OrderInfo> getOrderInfoPage(Integer pageNum, Integer pageSize, Integer orderStatus) {
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<OrderInfo>()
                .eq(orderStatus != null, OrderInfo::getOrderStatus, orderStatus)
                .eq(OrderInfo::getUserId, AuthContextUtil.getUserInfo().getId());

        PageHelper.startPage(pageNum, pageSize);
        List<OrderInfo> orderInfoList = baseMapper.selectList(wrapper);
        orderInfoList.forEach(orderInfo -> {
            LambdaQueryWrapper<OrderItem> w = new LambdaQueryWrapper<OrderItem>()
                    .eq(OrderItem::getOrderId, orderInfo.getId());
            List<OrderItem> orderItemList = orderItemMapper.selectList(w);
            orderInfo.setOrderItemList(orderItemList);
        });
        return new PageInfo<>(orderInfoList);
    }

    private void checkStockEnough(List<OrderItem> orderItemList) {
        orderItemList.forEach(orderItem -> {
            // 检查库存是否足够
            ProductSku productSku = productFeignClient.getBySkuId(orderItem.getSkuId()).getData();
            if (productSku == null) {
                throw new CommonGlobalRuntimeException(CommonResultCode.DATA_ERROR);
            }
            if (productSku.getStockNum() < orderItem.getSkuNum()) {
                throw new CommonGlobalRuntimeException(CommonResultCode.STOCK_LESS);
            }
        });
    }

    private static void checkOrderNotEmpty(List<OrderItem> orderItemList) {
        if (CollectionUtils.isEmpty(orderItemList)) {
            throw new CommonGlobalRuntimeException(CommonResultCode.EMPTY_ORDER);
        }
    }

    /**
     * 记录日志
     */
    private void saveOrderLog(OrderInfo orderInfo) {
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        OrderLog orderLog = new OrderLog();

        orderLog.setOrderId(orderInfo.getId());
        orderLog.setNote("提交订单");
        orderLog.setOperateUser(userInfo.getUsername());
        orderLog.setProcessStatus(0);

        orderLogMapper.insert(orderLog);
    }

    /**
     * 创建订单
     */
    private OrderInfo createOrder(OrderInfoDto orderInfoDto) {
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        OrderInfo orderInfo = new OrderInfo();
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();

        // 创建订单
        UserAddress userAddress = userAddressFeignClient.getUserAddress(orderInfoDto.getUserAddressId()).getData();

        orderInfo.setUserId(userInfo.getId());
        orderInfo.setNickName(userInfo.getNickName());
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));

        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getAddress());

        BigDecimal totalAmount = calculateTotalAmount(orderItemList);

        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setFeightFee(orderInfoDto.getFeightFee());
        orderInfo.setPayType(2);
        orderInfo.setOrderStatus(0);

        baseMapper.insert(orderInfo);

        // 保存订单明细
        orderItemList.forEach(orderItem -> {
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.insert(orderItem);
        });

        return orderInfo;
    }

    private static BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        // 计算总金额 totalAmount
        return orderItemList.stream()
                .map(orderItem -> {
                    BigDecimal count = new BigDecimal(orderItem.getSkuNum());
                    BigDecimal price = orderItem.getSkuPrice();
                    return price.multiply(count);
                })
                .reduce(BigDecimal::add)
                .orElseThrow(() -> new CommonGlobalRuntimeException(CommonResultCode.DATA_ERROR));
    }
}
