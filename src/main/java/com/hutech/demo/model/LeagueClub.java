package com.hutech.demo.model;
import jakarta.persistence.*;
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
@Table(name = "league_club")
public class LeagueClub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameLeague;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @OneToMany(mappedBy = "leagueClub")
    private List<TeamClub> teams;
}

/*
@NotBlank(message = "Tên Giải đấu  không được để trống")
    @Size(min = 50,message = "Tên Giải đấu khu vực phải có ít nhất 50 ký tự")
 */
