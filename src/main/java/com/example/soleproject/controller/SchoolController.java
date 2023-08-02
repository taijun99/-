package com.example.soleproject.controller;

import com.example.soleproject.config.auth.dto.SessionUser;
import com.example.soleproject.dto.ClubForm;
import com.example.soleproject.dto.SchoolForm;
import com.example.soleproject.entity.ClubScnu;
import com.example.soleproject.entity.SchoolScnu;
import com.example.soleproject.repository.ClubRepository;
import com.example.soleproject.repository.SchoolRepository;
import com.example.soleproject.service.ClubService;
import com.example.soleproject.service.SchoolService;
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

public class SchoolController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private SchoolService schoolService;
    @Autowired
    private SchoolRepository schoolRepository;


    @GetMapping("/scnu/schoolwrite")
    public String scnuNew(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "/scnu/write/schoolwrite";
    }


    @PostMapping("/scnu/schoolcreate")
    public String scnuCreate(SchoolForm schoolForm, MultipartFile file) throws Exception {
        SchoolScnu schoolScnu = schoolForm.toEntity();

        // 파일이 존재 여부에 따라 저장방식 구분
        if (file.isEmpty() == true) {
            schoolService.create(schoolScnu);
        } else {
            schoolService.write(schoolScnu, file);
        }

        return "redirect:/scnu/school";

    }

    @GetMapping("scnu/school/{id}")
    public String scnuView(@PathVariable Long id, Model model) {
        SchoolScnu schoolScnu = schoolRepository.findById(id).orElse(null);
        model.addAttribute("schoolScnu", schoolScnu);
        return "/scnu/view/schoolview";
    }


    @GetMapping("/scnu/school")
    public String scnuIndex(Model model) {
        List<SchoolScnu> schoolScnuEntityList = schoolRepository.findAll();
        model.addAttribute("schoolScnu", schoolScnuEntityList);
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "/scnu/album/school";
    }


    @GetMapping("/scnu/school/{id}/edit")
    public String edit1(@PathVariable Long id, Model model) {
        SchoolScnu schoolScnuEntity = schoolRepository.findById(id).orElse(null);
        model.addAttribute("schoolScnu", schoolScnuEntity);

        return "/scnu/edit/schooledit";
    }

    @PostMapping("/scnu/school/update")
    public String scnuUpdate(SchoolForm form, MultipartFile file) throws Exception {
        String s = schoolService.update2(form, file);
        return s;
    }

    @GetMapping("/scnu/school/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        String ss = schoolService.delete2(id, rttr);
        return ss;
    }
}
