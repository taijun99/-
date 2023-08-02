package com.example.soleproject.controller;

import com.example.soleproject.entity.MainEntity;
import com.example.soleproject.repository.MainRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@Slf4j
public class MainController {

    @Autowired
    private MainRepository mainRepository;

    @GetMapping("/scnu/main")
    public String scnuMain2(Model model) {
        return "/scnu/main";
    }

    @GetMapping("/scnu/{latitude}")
    public String sangse(@PathVariable String latitude,Model model){

        Optional<MainEntity> byLatitude = mainRepository.findByLatitude(latitude);
        log.info(byLatitude.get().getTitle());

        model.addAttribute("scnu",byLatitude.get());

        return "/scnu/view";

    }

}
