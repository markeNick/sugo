package com.sugo.common.annotator.Validator;

import com.sugo.common.util.RegexUtil;
import com.sugo.common.util.ResponseUtil;
import com.sugo.sql.entity.SugoAddress;
import org.springframework.util.StringUtils;

public class AddressVaildator {

    public static Object validateAddress(SugoAddress address) {
        String name = address.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }

        // 测试收货手机号码是否正确
        String mobile = address.getTel();
        if (StringUtils.isEmpty(mobile)) {
            return ResponseUtil.badArgument();
        }
        if (!RegexUtil.isMobileExact(mobile)) {
            return ResponseUtil.badArgument();
        }

        String province = address.getProvince();
        if (StringUtils.isEmpty(province)) {
            return ResponseUtil.badArgument();
        }

        String city = address.getCity();
        if (StringUtils.isEmpty(city)) {
            return ResponseUtil.badArgument();
        }

        String county = address.getCounty();
        if (StringUtils.isEmpty(county)) {
            return ResponseUtil.badArgument();
        }

        String areaCode = address.getAreaCode();
        if (StringUtils.isEmpty(areaCode)) {
            return ResponseUtil.badArgument();
        }

        String detailedAddress = address.getAddressDetail();
        if (StringUtils.isEmpty(detailedAddress)) {
            return ResponseUtil.badArgument();
        }

        Boolean isDefault = address.getIsDefault();
        if (isDefault == null) {
            return ResponseUtil.badArgument();
        }
        return null;
    }
}