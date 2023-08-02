package com.example.soleproject.service;

import com.example.soleproject.dto.ClubForm;
import com.example.soleproject.dto.StudentForm;
import com.example.soleproject.entity.ClubScnu;
import com.example.soleproject.entity.MainEntity;
import com.example.soleproject.entity.StudentScnu;
import com.example.soleproject.repository.ClubRepository;
import com.example.soleproject.repository.MainRepository;
import com.example.soleproject.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MainRepository mainRepository;

    public void write(StudentScnu studentScnu, MultipartFile file) throws Exception{
        // 프로젝트 경로를 저장함
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img";

        //  log.info(file.getOriginalFilename());
        // 랜덤으로 이름만들기
        UUID uuid = UUID.randomUUID();

        // 랜덤이름을 파일이름앞에 붙이고 원래 파일이름을 뒤에 붙인다.
        String fileName = file.getOriginalFilename();

        // prijectPath에 name이라는 이름으로 저장
        File saveFile = new File(projectPath,fileName);

        // 예외처리 해줘야함 파일 저장
        file.transferTo(saveFile);

        // 파일이름
        studentScnu.setFilename(fileName);
        // 파일 경로 이름
        studentScnu.setFilepath("/img/" + fileName);
        studentScnu.setFilepath("/img/" + fileName);
        studentRepository.save(studentScnu);
        mainRepository.save(studentScnu.toMainEntity());
    }

    public void create(StudentScnu studentScnu){
        studentScnu.setFilename("base.png");
        studentScnu.setFilepath("/img/base.png");
        studentRepository.save(studentScnu);
        mainRepository.save(studentScnu.toMainEntity());
    }

    public void update(StudentScnu studentScnu, MultipartFile file) throws Exception{
        // 프로젝트 경로를 저장함
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img";

        //  log.info(file.getOriginalFilename());
        // 랜덤으로 이름만들기
        UUID uuid = UUID.randomUUID();

        // 랜덤이름을 파일이름앞에 붙이고 원래 파일이름을 뒤에 붙인다.
        String fileName = file.getOriginalFilename();

        // prijectPath에 name이라는 이름으로 저장
        File saveFile = new File(projectPath,fileName);

        // 예외처리 해줘야함 파일 저장
        file.transferTo(saveFile);

        // 파일이름
        studentScnu.setFilename(fileName);
        // 파일 경로 이름
        studentScnu.setFilepath("/img/" + fileName);
        studentRepository.save(studentScnu);
    }

    public List<StudentScnu> index(){
        return studentRepository.findAll();
    }

    public StudentScnu show(Long id){
        return studentRepository.findById(id).orElse(null);
    }

    public StudentScnu create(StudentForm dto) {
        StudentScnu studentScnu = dto.toEntity();
        if (studentScnu.getId() != null){
            return null;
        }
        return studentRepository.save(studentScnu);
    }
    public StudentScnu update(Long id, StudentForm dto) {
        // 1: DTO -> 엔티티
        StudentScnu studentScnu = dto.toEntity();
        // 2: 타겟 조회
        StudentScnu target = studentRepository.findById(id).orElse(null);
        // 3: 잘못된 요청 처리
        if (target == null || id != studentScnu.getId()) {
            return null;
        }
        // 4: 업데이트
        StudentScnu updated = studentRepository.save(target);
        return updated;

    }
    public StudentScnu delete (Long id){
        // 대상 찾기
        StudentScnu target = studentRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if (target == null) {
            return null;
        }

        // 대상 삭제
        studentRepository.delete(target);
        return target;
    }





    public String update2(StudentForm form, MultipartFile file) throws IOException {
        StudentScnu studentScnu = form.toEntity();
        StudentScnu target = studentRepository.findById(studentScnu.getId()).orElse(null);
        ArrayList<MainEntity> mainEntity = mainRepository.findAll();

        for(MainEntity entity : mainEntity) {
            if(target.getLatitude() == entity.getLatitude()){
                MainEntity target2 = entity;
                mainRepository.delete(target2);
            }
        }

        if (target != null) {
            if (file.isEmpty() == true) {
                studentScnu.setFilename("base.png");
                studentScnu.setFilepath(studentScnu.getFilepath());
                studentRepository.save(studentScnu);
                mainRepository.save(studentScnu.toMainEntity());
                String page = "redirect:/scnu/student";
                return page;
            } else {
                String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img";

                //  log.info(file.getOriginalFilename());
                // 랜덤으로 이름만들기
                UUID uuid = UUID.randomUUID();

                // 랜덤이름을 파일이름앞에 붙이고 원래 파일이름을 뒤에 붙인다.
                String fileName = file.getOriginalFilename();

                // prijectPath에 name이라는 이름으로 저장
                File saveFile = new File(projectPath,fileName);

                // 예외처리 해줘야함 파일 저장
                file.transferTo(saveFile);

                // 파일이름
                studentScnu.setFilename(fileName);
                // 파일 경로 이름
                studentScnu.setFilepath("/img/" + fileName);
                studentRepository.save(studentScnu);
                mainRepository.save(studentScnu.toMainEntity());
                String page = "redirect:/scnu/";
                return page;
            }
        }
        String page = "redirect:/scnu/";
        return page;
    }
    public String delete2(Long id, RedirectAttributes rttr ) {
        StudentScnu target = studentRepository.findById(id).orElse(null);
        ArrayList<MainEntity> mainEntity = mainRepository.findAll();

        for(MainEntity entity : mainEntity) {
            if(target.getLatitude() == entity.getLatitude()){
                MainEntity target2 = entity;
                mainRepository.delete(target2);
            }
        }

        if (target != null) {
            studentRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제 완료");
            String page = "redirect:/scnu/student";
            return page;
        }
        String page = "redirect:/scnu/student";
        return page;
    }



}