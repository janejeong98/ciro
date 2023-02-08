package com.example.ciro.model;

import lombok.Data;

@Data
public class Company extends BaseCompanyObject implements Comparable<Company> {

    private String company_name;
    private Address address;
    private Phone phone;

    @Override
    public String toString() {
        return getId() + " "
                + company_name + " "
                + address.toString() + " "
                + phone.toString();
    }

    @Override
    public int compareTo(Company o) {
        return this.hashCode() - o.hashCode();
    }
}
