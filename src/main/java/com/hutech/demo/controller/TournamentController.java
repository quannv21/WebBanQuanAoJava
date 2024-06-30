package com.hutech.demo.controller;

import com.hutech.demo.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tournaments")
@RequiredArgsConstructor
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;

}
