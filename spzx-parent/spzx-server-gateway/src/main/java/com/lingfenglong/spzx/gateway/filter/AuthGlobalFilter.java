package com.lingfenglong.spzx.gateway.filter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lingfenglong.spzx.common.exception.CommonGlobalRuntimeException;
import com.lingfenglong.spzx.model.entity.user.UserInfo;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.model.vo.common.ResultCode;
import com.lingfenglong.spzx.model.vo.common.SysUserResultCode;
import com.lingfenglong.spzx.util.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    private final RedisTemplate<String, JSONObject> redisTemplate;
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    public AuthGlobalFilter(RedisTemplate<String, JSONObject> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        log.info("path: {}", path);

        // 请求路径为/**/auth/**则需要进行认证
        if (antPathMatcher.match("/**/auth/**", path)) {
            UserInfo userInfo = getUserInfo(request);
            if (userInfo == null) {
                return responseResultCode(exchange.getResponse(), SysUserResultCode.LOGIN_AUTH);
            }
        }

        return chain.filter(exchange);
    }

    private Mono<Void> responseResultCode(ServerHttpResponse response, ResultCode resultCode) {
        // 设置响应头
        response.getHeaders()
                .setContentType(MediaType.APPLICATION_JSON);

        // 响应体
        Result<?> result = Result.build(null, resultCode);
        DataBuffer dataBuffer = response.bufferFactory()
                .wrap(JSON.toJSONBytes(result));

        return response.writeWith(Mono.just(dataBuffer));
    }

    private UserInfo getUserInfo(ServerHttpRequest request) {
        List<String> tokens = request.getHeaders().get("token");
        String token = null;
        if (tokens != null && !tokens.isEmpty()) {
            token = tokens.get(0);
        }

        return Optional.ofNullable(redisTemplate.opsForValue().get(RedisConstant.KEY_USER_TOKEN + token))
                .orElseThrow(() -> new CommonGlobalRuntimeException(SysUserResultCode.LOGIN_AUTH))
                .toJavaObject(UserInfo.class);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
