package com.example.soleproject.controller;

import com.example.soleproject.config.auth.dto.SessionUser;
import com.example.soleproject.dto.ClubForm;
import com.example.soleproject.dto.ExtracurricularForm;
import com.example.soleproject.entity.ExtracurricularScnu;
import com.example.soleproject.repository.ExtracurricularRepository;
import com.example.soleproject.service.ExtracurricularService;
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

public class ExtracurricularController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private ExtracurricularService extracurricularService;
    @Autowired
    private ExtracurricularRepository extracurricularRepository;


    @GetMapping("/scnu/extracurricularwrite")
    public String scnuNew(Model model) {

        return "/scnu/write/extracurricularwrite";
    }


    @PostMapping("/scnu/extracurricularcreate")
    public String scnuCreate(ExtracurricularForm extracurricularForm, MultipartFile file) throws Exception {
        ExtracurricularScnu Scnu = extracurricularForm.toEntity();

        // 파일이 존재 여부에 따라 저장방식 구분
        if (file.isEmpty() == true) {
            extracurricularService.create(Scnu);
        } else {
            extracurricularService.write(Scnu, file);
        }

        return "redirect:/scnu/extracurricular";

    }

    @GetMapping("scnu/extracurricular/{id}")
    public String scnuView(@PathVariable Long id, Model model) {
        ExtracurricularScnu ExtracurricularScnu = extracurricularRepository.findById(id).orElse(null);
        model.addAttribute("extracurricularScnu", ExtracurricularScnu);
        return "/scnu/view/extracurricularview";
    }


    @GetMapping("/scnu/extracurricular")
    public String scnuIndex(Model model) {
        List<ExtracurricularScnu> ExtracurricularScnuEntityList = extracurricularRepository.findAll();
        model.addAttribute("extracurricularScnu", ExtracurricularScnuEntityList);
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "/scnu/album/extracurricular";
    }


    @GetMapping("/scnu/extracurricular/{id}/edit")
    public String edit1(@PathVariable Long id, Model model) {
        ExtracurricularScnu ExtracurricularScnuEntity = extracurricularRepository.findById(id).orElse(null);
        model.addAttribute("extracurricularScnu", ExtracurricularScnuEntity);

        return "/scnu/edit/extracurricularedit";
    }

    @PostMapping("/scnu/extracurricular/update")
    public String scnuUpdate(ExtracurricularForm form, MultipartFile file) throws Exception {
        String s = extracurricularService.update2(form, file);
        return s;
    }

    @GetMapping("/scnu/extracurricular/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        String ss = extracurricularService.delete2(id, rttr);
        return ss;
    }
}
