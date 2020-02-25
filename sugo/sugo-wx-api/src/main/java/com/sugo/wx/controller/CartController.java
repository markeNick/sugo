package com.sugo.wx.controller;

import com.sugo.common.system.SystemConfig;
import com.sugo.common.util.JacksonUtil;
import com.sugo.common.util.ResponseUtil;
import com.sugo.sql.entity.*;
import com.sugo.sql.service.coupon.CouponVerifyService;
import com.sugo.sql.service.goods.GoodsProductService;
import com.sugo.sql.service.goods.GoodsService;
import com.sugo.sql.service.user.AddressService;
import com.sugo.sql.service.coupon.CouponService;
import com.sugo.sql.service.coupon.CouponUserService;
import com.sugo.sql.service.user.CartService;
import com.sugo.wx.annotation.LoginUser;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.sugo.wx.util.WxResponseCode.GOODS_NO_STOCK;
import static com.sugo.wx.util.WxResponseCode.GOODS_UNSHELVE;
@RestController
@RequestMapping("/wx/cart")
@Validated
public class CartController {
    private final Log logger = LogFactory.getLog(AddressController.class);

    @Autowired
    private CartService cartService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsProductService productService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponUserService couponUserService;
    @Autowired
    private CouponVerifyService couponVerifyService;

    /**
     * 购物车商品信息
     * @param userId
     * @return
     */
    @GetMapping("index")
    public Object index(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        List<SugoCart> cartList1 = cartService.queryByUid(userId);
        List<SugoCart> cartlist2 = new ArrayList<>();
        // 过滤掉失效的商品
        for (SugoCart cart : cartList1) {
            SugoGoods goods = goodsService.findById(cart.getGoodsId());
            if(goods == null || !goods.getIsOnSale()) {
                cartService.deleteById(cart.getId());
                logger.debug("系统自动删除失效购物车商品 goodsId=" + cart.getGoodsId() + " productId=" + cart.getProductId());
            } else {
                cartlist2.add(cart);
            }
        }

        Integer goodsCount = 0; // 商品总数
        BigDecimal goodsAmount = new BigDecimal(0.00);  //商品总价格
        Integer checkedGoodsCount = 0;  // 选中商品数量
        BigDecimal checkedGoodsAmount = new BigDecimal(0.00);   // 选中商品总价格

        // 从过滤后的购物车商品表中计算
        for (SugoCart cart : cartlist2) {
            goodsCount += cart.getNumber();
            goodsAmount = goodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            if(cart.getChecked()) {
                checkedGoodsCount += cart.getNumber();
                checkedGoodsAmount = checkedGoodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            }
        }
        Map<String, Object> cartTotal = new HashMap<>();
        cartTotal.put("goodsCount", goodsCount);
        cartTotal.put("goodsAmount", goodsAmount);
        cartTotal.put("checkedGoodsCount", checkedGoodsCount);
        cartTotal.put("checkedGoodsAmount", checkedGoodsAmount);

        Map<String, Object> result = new HashMap<>();
        result.put("cartList", cartlist2);
        result.put("cartTotal", cartTotal);

        return ResponseUtil.ok(result);
    }

    /**
     * 加入商品到购物车
     * <p>
     * 如果已经存在购物车货品，则增加数量；
     * 否则添加新的购物车货品项。
     *
     * @param userId 用户ID
     * @param cart   购物车商品信息， { goodsId: xxx, productId: xxx, number: xxx }
     * @return 加入购物车操作结果
     */
    @PostMapping("add")
    public Object add(@LoginUser Integer userId, @RequestBody SugoCart cart) {
        if(userId == null) {
            return ResponseUtil.unlogin();
        }

        if(cart == null) {
            return ResponseUtil.badArgument();
        }
        Integer productId = cart.getProductId();
        Integer number = cart.getNumber().intValue();
        Integer goodsId = cart.getGoodsId();

        if(!ObjectUtils.allNotNull(productId, number, goodsId)) {
            return ResponseUtil.badArgument();
        }

        SugoGoods goods = goodsService.findById(goodsId);
        if(goods == null || !goods.getIsOnSale()) {
            return ResponseUtil.fail(GOODS_UNSHELVE, "商品已下架");
        }

        SugoGoodsProduct product = productService.findById(productId);
        SugoCart existCart = cartService.queryExist(goodsId, productId, userId);
        
        if(existCart == null) {
            if(product == null || number > product.getNumber()) {
                return ResponseUtil.fail(GOODS_NO_STOCK, "库存不足");
            }

            cart.setId(null);
            cart.setGoodsSn(goods.getGoodsSn());
            cart.setGoodsName(goods.getName());
            if(!StringUtils.isEmpty(product.getUrl())) {
                cart.setPicUrl(goods.getPicUrl());
            } else {
                cart.setPicUrl(product.getUrl());
            }
            cart.setPrice(product.getPrice());
            cart.setSpecifications(product.getSpecifications());
            cart.setUserId(userId);
            cart.setChecked(true);
            cartService.add(cart);
        } else {
            int num = existCart.getNumber() + number;
            if(num > product.getNumber()) {
                return ResponseUtil.fail(GOODS_NO_STOCK, "库存不足");
            }
            existCart.setNumber((short) num);
            if(cartService.updateById(existCart) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }

        return goodscount(userId);
    }

    /**
     * 立即购买
     * <p>
     * 和add方法的区别在于：
     * 1. 如果购物车内已经存在购物车货品，前者的逻辑是数量添加，这里的逻辑是数量覆盖
     * 2. 添加成功以后，前者的逻辑是返回当前购物车商品数量，这里的逻辑是返回对应购物车项的ID
     *
     * @param userId 用户ID
     * @param cart   购物车商品信息， { goodsId: xxx, productId: xxx, number: xxx }
     * @return 立即购买操作结果
     */
    @PostMapping("fastadd")
    public Object fastadd(@LoginUser Integer userId, @RequestBody SugoCart cart) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (cart == null) {
            return ResponseUtil.badArgument();
        }

        Integer productId = cart.getProductId();
        Integer number = cart.getNumber().intValue();
        Integer goodsId = cart.getGoodsId();

        if (!ObjectUtils.allNotNull(productId, number, goodsId)) {
            return ResponseUtil.badArgument();
        }

        if(number <= 0){
            return ResponseUtil.badArgument();
        }

        //判断商品是否可以购买
        SugoGoods goods = goodsService.findById(goodsId);
        if (goods == null || !goods.getIsOnSale()) {
            return ResponseUtil.fail(GOODS_UNSHELVE, "商品已下架");
        }

        SugoGoodsProduct product = productService.findById(productId);
        //判断购物车中是否存在此规格商品
        SugoCart existCart = cartService.queryExist(goodsId, productId, userId);
        if (existCart == null) {
            //取得规格的信息,判断规格库存
            if (product == null || number > product.getNumber()) {
                return ResponseUtil.fail(GOODS_NO_STOCK, "库存不足");
            }

            cart.setId(null);
            cart.setGoodsSn(goods.getGoodsSn());
            cart.setGoodsName((goods.getName()));
            if(StringUtils.isEmpty(product.getUrl())){
                cart.setPicUrl(goods.getPicUrl());
            }
            else{
                cart.setPicUrl(product.getUrl());
            }
            cart.setPrice(product.getPrice());
            cart.setSpecifications(product.getSpecifications());
            cart.setUserId(userId);
            cart.setChecked(true);
            cartService.add(cart);
        } else {
            //取得规格的信息,判断规格库存
            int num = number;
            if (num > product.getNumber()) {
                return ResponseUtil.fail(GOODS_NO_STOCK, "库存不足");
            }
            existCart.setNumber((short) num);
            if (cartService.updateById(existCart) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }

        return ResponseUtil.ok(existCart != null ? existCart.getId() : cart.getId());
    }

    /**
     * 修改购物车商品货品数量
     *
     * @param userId 用户ID
     * @param cart   购物车商品信息， { id: xxx, goodsId: xxx, productId: xxx, number: xxx }
     * @return 修改结果
     */
    @PostMapping("update")
    public Object update(@LoginUser Integer userId, @RequestBody SugoCart cart) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Integer id = cart.getId();
        Integer productId = cart.getProductId();
        Integer goodsId = cart.getGoodsId();
        Integer number = cart.getNumber().intValue();

        if(!ObjectUtils.allNotNull(id, productId, goodsId, number)) {
            return ResponseUtil.badArgument();
        }

        if(number <= 0) {
            return ResponseUtil.badArgument();
        }

        SugoCart existCart = cartService.findById(userId, id);

        if (existCart == null) {
            return ResponseUtil.badArgumentValue();
        }

        // 判断goodsId和productId是否与当前cart里的值一致
        if(!existCart.getGoodsId().equals(goodsId) || !existCart.getProductId().equals(productId)) {
            return ResponseUtil.badArgumentValue();
        }

        // 判断商品是否可以购买
        SugoGoods goods = goodsService.findById(goodsId);
        if(goods == null || !goods.getIsOnSale()) {
            return ResponseUtil.fail(GOODS_UNSHELVE, "商品已下架");
        }

        //取得规格的信息,判断规格库存
        SugoGoodsProduct goodsProduct = productService.findById(productId);
        if(goodsProduct == null || goodsProduct.getNumber() < number) {
            return ResponseUtil.fail(GOODS_UNSHELVE, "库存不足");
        }

        existCart.setNumber(number.shortValue());
        if(cartService.updateById(existCart) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok();
    }

    /**
     * 购物车商品货品勾选状态
     * <p>
     * 如果原来没有勾选，则设置勾选状态；如果商品已经勾选，则设置非勾选状态。
     *
     * @param userId 用户ID
     * @param body   购物车商品信息， { productIds: xxx, isChecked: 1/0 }
     * @return 购物车信息
     */
    @PostMapping("checked")
    public Object checked(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (body == null) {
            return ResponseUtil.badArgument();
        }

        List<Integer> productIds = JacksonUtil.parseIntegerList(body, "productIds");
        if (productIds == null) {
            return ResponseUtil.badArgument();
        }

        Integer checkValue = JacksonUtil.parseInteger(body, "isChecked");
        if (checkValue == null) {
            return ResponseUtil.badArgument();
        }
        Boolean isChecked = (checkValue == 1);

        cartService.updateCheck(userId, productIds, isChecked);
        return index(userId);
    }

    /**
     * 购物车商品货品数量
     * <p>
     * 如果用户没有登录，则返回空数据。
     *
     * @param userId 用户ID
     * @return 购物车商品货品数量
     */
    @GetMapping("goodscount")
    public Object goodscount(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.ok(0);
        }

        int goodsCount = 0;
        List<SugoCart> cartList = cartService.queryByUid(userId);
        for (SugoCart cart : cartList) {
            goodsCount += cart.getNumber();
        }

        return ResponseUtil.ok(goodsCount);
    }

    /**
     * 购物车下单
     *
     * @param userId    用户ID
     * @param cartId    购物车商品ID：
     *                  如果购物车商品ID是空，则下单当前用户所有购物车商品；
     *                  如果购物车商品ID非空，则只下单当前购物车商品。
     * @param addressId 收货地址ID：
     *                  如果收货地址ID是空，则查询当前用户的默认地址。
     * @param couponId  优惠券ID：
     *                  如果优惠券ID是空，则自动选择合适的优惠券。
     * @return 购物车操作结果
     */
    @GetMapping("checkout")
    public Object checkout(@LoginUser Integer userId, Integer cartId, Integer addressId,
                           Integer couponId, Integer userCouponId, Integer grouponRulesId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        // 处理收货地址
        SugoAddress address = null;
        if(addressId == null || addressId.equals(0)) {
            address = addressService.findDefault(userId);
            // 如果仍然没有地址，则是没有收货地址，返回一个空的地址id=0
            if(address == null) {
                address = new SugoAddress();
                address.setId(0);
                addressId = 0;
            } else {
                addressId = address.getId();
            }
        } else {
            address = addressService.query(userId, addressId);
            if(address == null) {
                return ResponseUtil.badArgumentValue();
            }
        }

        // 处理未优惠的商品价格
        List<SugoCart> goodsList = null;
        if(cartId == null || cartId.equals(0)) {
            goodsList = cartService.queryByUidAndChecked(userId);
        } else {
            SugoCart cart = cartService.findById(userId, cartId);
            if(cart == null) {
                return ResponseUtil.badArgumentValue();
            }
            goodsList = new ArrayList<>(1);
            goodsList.add(cart);
        }
        BigDecimal goodsPrice = new BigDecimal(0.00);
        for (SugoCart cart : goodsList) {
            goodsPrice = goodsPrice.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
        }

        // 处理优惠券
        BigDecimal tmpCouponPrice = new BigDecimal(0.00);
        Integer tmpCouponId = 0;        // 临时优惠券ID
        Integer tmpUserCouponId = 0;    // 临时用户优惠券表ID
        int tmpCouponLength = 0;        // 临时可用优惠券数量
        List<SugoCouponUser> couponUserList = couponUserService.queryAll(userId);
        for (SugoCouponUser couponUser : couponUserList) {
            tmpUserCouponId = couponUser.getId();

            // 检查优惠券是否可用
            SugoCoupon coupon = couponVerifyService.checkCouponcheckCoupon(userId, couponUser.getCouponId(), tmpUserCouponId, goodsPrice);
            if (coupon == null) {
                continue;
            }

            tmpCouponLength++;

            // 求出最大优惠额度的优惠券
            if(tmpCouponPrice.compareTo(coupon.getDiscount()) == -1) {
                tmpCouponPrice = coupon.getDiscount();
                tmpCouponId = coupon.getId();
            }
        }

        BigDecimal couponPrice = new BigDecimal(0);

        /**
         * 这里存在三种情况
         *  1. 用户不想使用优惠券，则不处理
         *  2. 用户想自动使用优惠券，则选择合适优惠券
         *  3. 用户已选择优惠券，则测试优惠券是否合适
         */
        if(couponId == null || couponId.equals(-1)) {
            couponId = -1;
            userCouponId = -1;
        } else if(couponId.equals(0)) {
            couponPrice = tmpCouponPrice;
            couponId = tmpCouponId;
            userCouponId = tmpUserCouponId;
        } else {
            SugoCoupon coupon = couponVerifyService.checkCouponcheckCoupon(userId, couponId, userCouponId, goodsPrice);
            if(coupon == null) {    // 用户选择的优惠券有问题，则选择合适优惠券，否则使用用户选择的优惠券
                couponPrice = tmpCouponPrice;
                couponId = tmpCouponId;
                userCouponId = tmpUserCouponId;
            } else {
                couponPrice = coupon.getDiscount();
            }
        }

        // 计算运费：满88则免运费，否则算上8元运费
        BigDecimal freightPrice = new BigDecimal(0.00);
        if(goodsPrice.compareTo(SystemConfig.getFreightLimit()) < 0) {
            freightPrice = SystemConfig.getFreight();
        }

        // 可以使用的其他钱，例如用户积分
        BigDecimal integralPrice = new BigDecimal(0.00);

        // 订单费用
        BigDecimal orderTotalPrice = goodsPrice.add(freightPrice).subtract(couponPrice).max(new BigDecimal(0.00));

        BigDecimal actualPrice = orderTotalPrice.subtract(integralPrice);
        Map<String, Object> data = new HashMap<>();
        data.put("addressId", addressId);
        data.put("couponId", couponId);
        data.put("userCouponId", userCouponId);
        data.put("cartId", cartId);
        data.put("grouponRulesId", -1);
        data.put("grouponPrice", 0.00);
        data.put("checkedAddress", address);
        data.put("availableCouponLength", tmpCouponLength);
        data.put("goodsTotalPrice", goodsPrice);
        data.put("freightPrice", freightPrice);
        data.put("couponPrice", couponPrice);
        data.put("orderTotalPrice", orderTotalPrice);
        data.put("actualPrice", actualPrice);
        data.put("checkedGoodsList", goodsList);
        return ResponseUtil.ok(data);
    }

}




























