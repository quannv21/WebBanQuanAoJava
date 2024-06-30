package com.hutech.demo.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên Quốc Gia không được để trống")
    @Size(min = 5,message = "Tên Quốc Gia phải có ít nhất 5 ký tự")
    @Column(name = "name_country")
    private String nameCountry;

    @OneToMany(mappedBy = "country")
    private List<Product> product;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;
}
