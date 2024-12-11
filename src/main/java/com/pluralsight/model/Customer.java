package com.pluralsight.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class Customer {
    private int CustomerID;
    private String name;
    private String email;
}
