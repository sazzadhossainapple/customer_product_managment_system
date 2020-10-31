package com.example.customerproduct.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String email;
    private String gender;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cid",referencedColumnName = "id")
    private List<Product>products;





}
