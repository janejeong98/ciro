package com.example.ciro.model;

import lombok.Data;

@Data
public class Phone extends BaseCompanyObject {

    private Integer company_id;
    private String phone_number;

    @Override
    public String toString() {
        return getId() + " "
                + company_id + " "
                + phone_number;
    }

}
