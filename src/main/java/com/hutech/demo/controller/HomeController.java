package com.hutech.demo.controller;

import com.hutech.demo.service.LeagueClubService;
import com.hutech.demo.service.TeamClubService;
import com.hutech.demo.service.TournamentCategoryService;
import com.hutech.demo.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    @Autowired
    private TournamentCategoryService tournamentCategoryService;

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private LeagueClubService leagueClubService;

    @Autowired
    private TeamClubService teamClubService;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("categories", tournamentCategoryService.getAllTournamentCategory());
        model.addAttribute("tournaments", tournamentService.getAllTournaments());
        var ok = leagueClubService.getAllLeagueClubs();
        model.addAttribute("leagues", leagueClubService.getAllLeagueClubs());
        model.addAttribute("teamclubs", teamClubService.getAllTeamClubs());
        return "home/home";
    }
}
