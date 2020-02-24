package com.sugo.wx.controller;

import com.sugo.sql.entity.SugoRegion;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sugo.sql.service.user.AdressService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wx/address")
@Validated
public class AddressController {
    private final Log logger = LogFactory.getLog(AddressController.class);

    @Autowired
    private AdressService adressService;

//    @Autowired
//    private SugoRegionService sugoRegionService;

}