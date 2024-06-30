package com.hutech.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "teamClub_id")
    private TeamClub teamClub;

    private String thumnail;

    // Add total_quantity field
    private Integer total_quantity;

    // Add Brand field
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;


}