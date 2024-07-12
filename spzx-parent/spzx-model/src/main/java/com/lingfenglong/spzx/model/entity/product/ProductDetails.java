package com.lingfenglong.spzx.model.entity.product;


import com.lingfenglong.spzx.model.entity.base.BaseEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetails extends BaseEntity {

	private Long productId;
	private String imageUrls;

}