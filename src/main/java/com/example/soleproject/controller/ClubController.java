package com.example.soleproject.controller;

import com.example.soleproject.config.auth.dto.SessionUser;
import com.example.soleproject.dto.ClubForm;
import com.example.soleproject.entity.ClubScnu;
import com.example.soleproject.repository.ClubRepository;
import com.example.soleproject.repository.MainRepository;
import com.example.soleproject.service.ClubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j

public class ClubController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private ClubService clubService;
    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private MainRepository mainRepository;


    @GetMapping("/scnu/clubwrite")
    public String scnuNew(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "/scnu/write/clubwrite";
    }


    @PostMapping("/scnu/clubcreate")
    public String scnuCreate(ClubForm clubForm, MultipartFile file) throws Exception {
        ClubScnu clubScnu = clubForm.toEntity();

        // 파일이 존재 여부에 따라 저장방식 구분
        if (file.isEmpty() == true) {
            clubService.create(clubScnu);
        } else {
            clubService.write(clubScnu, file);
        }

        return "redirect:/scnu/club";

    }

    @GetMapping("scnu/club/{id}")
    public String scnuView(@PathVariable Long id, Model model) {
        ClubScnu clubScnu = clubRepository.findById(id).orElse(null);
        model.addAttribute("clubScnu", clubScnu);
        return "/scnu/view/clubview";
    }


    @GetMapping("/scnu/club")
    public String scnuIndex(Model model) {
        List<ClubScnu> clubScnuEntityList = clubRepository.findAll();
        model.addAttribute("clubScnu", clubScnuEntityList);
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "/scnu/album/club";
    }


    @GetMapping("/scnu/club/{id}/edit")
    public String edit1(@PathVariable Long id, Model model) {
        ClubScnu clubScnuEntity = clubRepository.findById(id).orElse(null);
        model.addAttribute("clubScnu", clubScnuEntity);

        return "/scnu/edit/clubedit";
    }

    @PostMapping("/scnu/club/update")
    public String scnuUpdate(ClubForm form, MultipartFile file) throws Exception {
        String s = clubService.update2(form, file);
        return s;
    }

    @GetMapping("/scnu/club/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        String ss = clubService.delete2(id, rttr);
        return ss;
    }
}