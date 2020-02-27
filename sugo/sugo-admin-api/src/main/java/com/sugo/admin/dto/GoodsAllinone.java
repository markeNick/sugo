package com.sugo.admin.dto;

import com.sugo.sql.entity.SugoGoods;
import com.sugo.sql.entity.SugoGoodsAttribute;
import com.sugo.sql.entity.SugoGoodsProduct;
import com.sugo.sql.entity.SugoGoodsSpecification;

public class GoodsAllinone {
    SugoGoods goods;
    SugoGoodsSpecification[] specifications;
    SugoGoodsAttribute[] attributes;
    SugoGoodsProduct[] products;

    public SugoGoods getGoods() {
        return goods;
    }

    public void setGoods(SugoGoods goods) {
        this.goods = goods;
    }

    public SugoGoodsProduct[] getProducts() {
        return products;
    }

    public void setProducts(SugoGoodsProduct[] products) {
        this.products = products;
    }

    public SugoGoodsSpecification[] getSpecifications() {
        return specifications;
    }

    public void setSpecifications(SugoGoodsSpecification[] specifications) {
        this.specifications = specifications;
    }

    public SugoGoodsAttribute[] getAttributes() {
        return attributes;
    }

    public void setAttributes(SugoGoodsAttribute[] attributes) {
        this.attributes = attributes;
    }

}
