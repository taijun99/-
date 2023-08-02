package com.example.soleproject.controller;

import com.example.soleproject.entity.MainEntity;
import com.example.soleproject.repository.MainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainApiController {

    @Autowired
    private MainRepository mainRepository;

    @GetMapping("/api/main/")
    public List<MainEntity> main(){
        return
        mainRepository.findAll();

    }

}
