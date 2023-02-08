package com.example.ciro.config;

import com.example.ciro.model.Address;
import com.example.ciro.model.Company;
import com.example.ciro.model.Phone;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class CompanyConfig {

    private static final String ADDRESS_FILE_PATH = "data/address.json";
    private static final String COMPANY_FILE_PATH = "data/company.json";
    private static final String PHONE_FILE_PATH = "data/phone.json";

    @Bean
    public Map<Integer, Address> idToAddressMap(ObjectMapper objectMapper) throws IOException {
        File addressFile = new File(getClass().getResource(ADDRESS_FILE_PATH).getFile());
        String addressString = new String(Files.readAllBytes(addressFile.toPath()));

        List<Address> addressList = objectMapper.readValue(addressString, new TypeReference<List<Address>>() {});
        Map<Integer, Address> idToAddress = new HashMap<>();
        for (Address address: addressList) {
            idToAddress.put(address.getCompany_id(), address);
        }
        return idToAddress;
    }

    @Bean
    public Map<Integer, Phone> idToPhoneMap(ObjectMapper objectMapper) throws IOException {
        File phoneFile = new File(getClass().getResource(PHONE_FILE_PATH).getFile());
        String phoneString = new String(Files.readAllBytes(phoneFile.toPath()));

        List<Phone> phoneList = objectMapper.readValue(phoneString, new TypeReference<List<Phone>>() {});
        Map<Integer, Phone> idToPhone = new HashMap<>();
        for (Phone phone: phoneList) {
            idToPhone.put(phone.getCompany_id(), phone);
        }
        return idToPhone;
    }

    @Bean
    public Map<Integer, Company> idToCompanyMap(ObjectMapper objectMapper,
                                                 Map<Integer, Address> idToAddressMap,
                                                 Map<Integer, Phone> idToPhoneMap) throws IOException {
        File companyFile = new File(getClass().getResource(COMPANY_FILE_PATH).getFile());
        String companyString = new String(Files.readAllBytes(companyFile.toPath()));

        List<Company> companyList = objectMapper.readValue(companyString, new TypeReference<List<Company>>() {});
        Map<Integer, Company> idToCompany = new HashMap<>();
        for (Company company: companyList) {
            Address address = idToAddressMap.get(company.getId());
            Phone phone = idToPhoneMap.get(company.getId());
            company.setAddress(address);
            company.setPhone(phone);

            idToCompany.put(company.getId(), company);
        }
        return idToCompany;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new Jdk8Module());

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }

}
