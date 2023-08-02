package com.example.soleproject.controller;

import com.example.soleproject.config.auth.dto.SessionUser;
import com.example.soleproject.dto.ClubForm;
import com.example.soleproject.dto.ScholarshipForm;
import com.example.soleproject.entity.ClubScnu;
import com.example.soleproject.entity.ScholarshipScnu;
import com.example.soleproject.repository.ClubRepository;
import com.example.soleproject.repository.ScholarshipRepository;
import com.example.soleproject.service.ClubService;
import com.example.soleproject.service.ScholarshipService;
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

public class ScholarshipController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private ScholarshipService scholarshipService;
    @Autowired
    private ScholarshipRepository scholarshipRepository;


    @GetMapping("/scnu/scholarshipwrite")
    public String scnuNew(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "/scnu/write/scholarshipwrite";
    }


    @PostMapping("/scnu/scholarshipcreate")
    public String scnuCreate(ScholarshipForm scholarshipForm, MultipartFile file) throws Exception {
        ScholarshipScnu scholarshipScnu = scholarshipForm.toEntity();

        // 파일이 존재 여부에 따라 저장방식 구분
        if (file.isEmpty() == true) {
            scholarshipService.create(scholarshipScnu);
        } else {
            scholarshipService.write(scholarshipScnu, file);
        }

        return "redirect:/scnu/scholarship";

    }

    @GetMapping("/scnu/scholarship/{id}")
    public String scnuView(@PathVariable Long id, Model model) {
        ScholarshipScnu scholarshipScnu = scholarshipRepository.findById(id).orElse(null);
        model.addAttribute("scholarshipScnu", scholarshipScnu);
        return "/scnu/view/scholarshipview";
    }


    @GetMapping("/scnu/scholarship")
    public String scnuIndex(Model model) {
        List<ScholarshipScnu> scholarshipScnuEntityList = scholarshipRepository.findAll();
        model.addAttribute("scholarshipScnu", scholarshipScnuEntityList);
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "/scnu/album/scholarship";
    }


    @GetMapping("/scnu/scholarship/{id}/edit")
    public String edit1(@PathVariable Long id, Model model) {
        ScholarshipScnu scholarshipScnuEntity = scholarshipRepository.findById(id).orElse(null);
        model.addAttribute("scholarshipScnu", scholarshipScnuEntity);

        return "/scnu/edit/scholarshipedit";
    }

    @PostMapping("/scnu/scholarship/update")
    public String scnuUpdate(ScholarshipForm form, MultipartFile file) throws Exception {
        String s = scholarshipService.update2(form, file);
        return s;
    }

    @GetMapping("/scnu/scholarship/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        String ss = scholarshipService.delete2(id, rttr);
        return ss;
    }
}