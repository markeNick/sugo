package com.sugo.wx.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sugo.sql.service.AdressService;

@RestController
@RequestMapping("/wx/address")
public class AddressController {
    private final Log logger = LogFactory.getLog(AddressController.class);

    @Autowired
    private AdressService adressService;
}