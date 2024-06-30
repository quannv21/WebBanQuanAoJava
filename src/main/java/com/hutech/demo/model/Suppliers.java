package com.hutech.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "suppliers")
public class Suppliers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "contact_fname")
    private String contactFName;

    @Column(name = "contact_lname")
    private String contactLName;

    @Column(name = "contact_title")
    private String contactTitle;

    private String address1;
    private String address2;
    private String city;
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    private String country;
    private String phone;
    private String fax;
    private String email;
    private String url;

    @Column(name = "payment_methods")
    private String paymentMethods;

    @Column(name = "discount_type")
    private String discountType;

    @Column(name = "type_goods")
    private String typeGoods;

    private String notes;

    @Column(name = "discount_available")
    private boolean discountAvailable;

    @Column(name = "current_order")
    private Long currentOrder;

    private String logo;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "size_url")
    private String sizeURL;
}