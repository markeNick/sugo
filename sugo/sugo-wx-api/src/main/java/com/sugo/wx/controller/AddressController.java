package com.sugo.wx.controller;


import com.sugo.common.annotator.Validator.AddressVaildator;
import com.sugo.common.util.RegexUtil;
import com.sugo.common.util.ResponseUtil;
import com.sugo.sql.entity.SugoAddress;
import com.sugo.sql.service.RegionService;
import com.sugo.wx.annotation.LoginUser;
import com.sugo.wx.service.GetRegionService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.sugo.sql.service.AddressService;

import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("/wx/address")
@Validated
public class AddressController extends GetRegionService {
    private final Log logger = LogFactory.getLog(AddressController.class);

    @Autowired
    private AddressService addressService;

    @Autowired
    private RegionService sugoRegionService;

    /**
     * 获取用户收货地址表
     * @param userId
     * @return
     */
    @GetMapping("list")
    public Object List(@LoginUser Integer userId) {
        if(userId == null) {
            return ResponseUtil.unlogin();
        }
        List<SugoAddress> addressList = addressService.queryByUid(userId);

        return ResponseUtil.okList(addressList);
    }

    /**
     * 获取收货地址详情
     * @param userId
     * @param id
     * @return
     */
    @GetMapping("detail")
    public Object detail(@LoginUser Integer userId, @NotNull Integer id) {
        if(userId == null) {
            return ResponseUtil.unlogin();
        }

        SugoAddress address = addressService.query(userId, id);
        if(address == null) {
            return ResponseUtil.badArgumentValue();
        }

        return ResponseUtil.ok(address);
    }

    /**
     * 添加或更新收货地址
     * @param userId
     * @param address
     * @return
     */
    @PostMapping("save")
    public Object save(@LoginUser Integer userId, @RequestBody SugoAddress address) {
        if(userId == null) {
            return ResponseUtil.unlogin();
        }

        // 校验地址
        Object error = AddressVaildator.validateAddress(address);
        if (error != null) {
            return error;
        }
        // 如果不存则添加，否则更新
        if(address.getId() == null || address.getId().equals(0)) {
            if(address.getIsDefault()) {
                // 将其他收货地址的默认选项重置为否
                addressService.resetDefault(userId);
            }
            address.setId(null);
            address.setUserId(userId);
            addressService.add(address);
        } else {
            SugoAddress sugoAddress = addressService.query(userId, address.getId());
            if(sugoAddress == null) {
                return ResponseUtil.badArgumentValue();
            }

            if(address.getIsDefault()) {
                addressService.resetDefault(userId);
            }

            address.setUserId(userId);
            addressService.update(address);
        }

        return ResponseUtil.ok(address.getId());
    }

    /**
     * 删除收货地址
     * @param userId
     * @param address
     * @return
     */
    @PostMapping("delete")
    public Object delete(@LoginUser Integer userId, @RequestBody SugoAddress address) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        // 地址编号为空
        if(address.getId() == null) {
            return ResponseUtil.badArgument();
        }
        // 地址编号不存在
        SugoAddress sugoAddress = addressService.query(userId, address.getId());
        if(sugoAddress == null) {
            return ResponseUtil.badArgumentValue();
        }

        addressService.delete(address.getId());
        return ResponseUtil.ok();
    }
}



















