package com.example.ciro.model;

import lombok.Data;

@Data
public class Address extends BaseCompanyObject {

    private Integer company_id;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private Integer postal_code;
    private String country;

    @Override
    public String toString() {
        return getId() + " "
                + company_id + " "
                + line1 + " "
                + line2 + " "
                + city + " "
                + state + " "
                + postal_code + " "
                + country;
    }

}
