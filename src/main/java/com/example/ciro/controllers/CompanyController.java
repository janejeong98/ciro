package com.example.ciro.controllers;

import com.example.ciro.model.Address;
import com.example.ciro.model.Company;
import com.example.ciro.model.CompareResponse;
import com.example.ciro.model.Phone;
import com.example.ciro.services.SimilarityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ComponentScan
public class CompanyController {

    @Autowired
    private Map<Integer, Company> idToCompanyMap;

    @Autowired
    private Map<Integer, Address> idToAddressMap;

    @Autowired
    private Map<Integer, Phone> idToPhoneMap;

    @Autowired
    private SimilarityService similarityService;

    @ResponseBody
    @GetMapping("/address/compare")
    public CompareResponse compareAddress(Address address) {
        return similarityService.getHighestSimilarityObject(idToAddressMap, address);
    }

    @ResponseBody
    @GetMapping("/company/compare")
    public CompareResponse compareAddress(Company company) {
        return similarityService.getHighestSimilarityObject(idToCompanyMap, company);
    }

    @ResponseBody
    @GetMapping("/phone/compare")
    public CompareResponse compareAddress(Phone phone) {
        return similarityService.getHighestSimilarityObject(idToPhoneMap, phone);
    }

}
