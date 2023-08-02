package com.example.soleproject.controller;

import com.example.soleproject.config.auth.dto.SessionUser;
import com.example.soleproject.dto.ClubForm;
import com.example.soleproject.dto.StudentForm;
import com.example.soleproject.entity.ClubScnu;
import com.example.soleproject.entity.StudentScnu;
import com.example.soleproject.repository.ClubRepository;
import com.example.soleproject.repository.StudentRepository;
import com.example.soleproject.service.ClubService;
import com.example.soleproject.service.StudentService;
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

public class StudentController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;


    @GetMapping("/scnu/studentwrite")
    public String scnuNew(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "/scnu/write/studentwrite";
    }


    @PostMapping("/scnu/studentcreate")
    public String scnuCreate(StudentForm studentForm, MultipartFile file) throws Exception {
        StudentScnu studentScnu = studentForm.toEntity();

        // 파일이 존재 여부에 따라 저장방식 구분
        if (file.isEmpty() == true) {
            studentService.create(studentScnu);
        } else {
            studentService.write(studentScnu, file);
        }

        return "redirect:/scnu/student";

    }

    @GetMapping("scnu/student/{id}")
    public String scnuView(@PathVariable Long id, Model model) {
        StudentScnu studentScnu = studentRepository.findById(id).orElse(null);
        model.addAttribute("studentScnu", studentScnu);
        return "/scnu/view/studentview";
    }


    @GetMapping("/scnu/student")
    public String scnuIndex(Model model) {
        List<StudentScnu> studentScnuEntityList = studentRepository.findAll();
        model.addAttribute("studentScnu", studentScnuEntityList);
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "/scnu/album/student";
    }


    @GetMapping("/scnu/student/{id}/edit")
    public String edit1(@PathVariable Long id, Model model) {
        StudentScnu studentScnuEntity = studentRepository.findById(id).orElse(null);
        model.addAttribute("studentScnu", studentScnuEntity);

        return "/scnu/edit/studentedit";
    }

    @PostMapping("/scnu/student/update")
    public String scnuUpdate(StudentForm form, MultipartFile file) throws Exception {
        String s = studentService.update2(form, file);
        return s;
    }

    @GetMapping("/scnu/student/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        String ss = studentService.delete2(id, rttr);
        return ss;
    }
}