package com.sell.controller;

import com.sell.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/companySystem")
public class CompanyController {

    @Autowired
    private CompanyService companySer;


}
