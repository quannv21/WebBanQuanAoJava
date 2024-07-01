package com.hutech.demo.controller;

import com.hutech.demo.NotFoundByIdException;
import com.hutech.demo.model.TeamClub;
import com.hutech.demo.service.TeamClubService;
import com.hutech.demo.service.LeagueClubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TeamClubController {
    @Autowired
    private final TeamClubService teamClubService;
    @Autowired
    private final LeagueClubService leagueClubService; // Assuming you have a LeagueClubService

    @GetMapping("/teamclubs/add")
    public String showFormAdd(Model model)
    {
        model.addAttribute("teamClub",new TeamClub());
        model.addAttribute("leagueClubs", leagueClubService.getAllLeagueClubs()); // Add all leagueClubs to the model
        return "/teamclubs/add-teamclub";
    }

    @PostMapping("/teamclubs/add")
    public String saveTeamClub(@Valid TeamClub teamClub, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "/teamclubs/add-teamclub";
        }
        teamClubService.addTeamClub(teamClub);
        return "redirect:/teamclubs";
    }

    @GetMapping("/teamclubs")
    public String showListTeamClub(Model model)
    {
        model.addAttribute("teamClubs",teamClubService.getAllTeamClubs());
        return "/teamclubs/teamclubs-list";
    }

    @GetMapping("/teamclubs/edit/{id}")
    public String formTeamClub(@PathVariable("id")Long id, Model model)
    {
        try {
            TeamClub teamClub =  teamClubService.getById(id);
            model.addAttribute("teamClub", teamClub);
            model.addAttribute("leagueClubs", leagueClubService.getAllLeagueClubs()); // Add all leagueClubs to the model
            return "/teamclubs/edit-teamclub";
        } catch (NotFoundByIdException e) {
            return "redirect:/teamclubs";
        }
    }

    @PostMapping("/teamclubs/edit/{id}")
    public String formUpdateTeamClub(@ModelAttribute("teamClub") TeamClub teamClub, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "/teamclubs/edit-teamclub";
        }
        teamClubService.updateTeamClub(teamClub);
        return "redirect:/teamclubs";
    }
}