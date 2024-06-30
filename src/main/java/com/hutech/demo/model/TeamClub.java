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
@Table(name = "team_club")
public class TeamClub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên đội bóng không được để trống")
    @Size(min = 5,message = "Tên đội bóng phải có ít nhất 5 ký tự")
    @Column(name = "name_club")
    private String nameClub;

    @ManyToOne
    @JoinColumn(name = "league_club_id")
    private LeagueClub leagueClub;

    @OneToMany(mappedBy = "teamClub")
    private List<Product> products;

}
