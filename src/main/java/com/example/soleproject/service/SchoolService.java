package com.example.soleproject.service;

import com.example.soleproject.dto.ClubForm;
import com.example.soleproject.dto.SchoolForm;
import com.example.soleproject.entity.ClubScnu;
import com.example.soleproject.entity.MainEntity;
import com.example.soleproject.entity.SchoolScnu;
import com.example.soleproject.repository.ClubRepository;
import com.example.soleproject.repository.MainRepository;
import com.example.soleproject.repository.SchoolRepository;
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
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private MainRepository mainRepository;

    public void write(SchoolScnu schoolScnu, MultipartFile file) throws Exception{
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
        schoolScnu.setFilename(fileName);
        // 파일 경로 이름
        schoolScnu.setFilepath("/img/" + fileName);
        schoolRepository.save(schoolScnu);
        mainRepository.save(schoolScnu.toMainEntity());
    }

    public void create(SchoolScnu schoolScnu){
        schoolScnu.setFilename("base.png");
        schoolScnu.setFilepath("/img/base.png");
        schoolRepository.save(schoolScnu);
        mainRepository.save(schoolScnu.toMainEntity());
    }

    public void update(SchoolScnu schoolScnu, MultipartFile file) throws Exception{
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
        schoolScnu.setFilename(fileName);
        // 파일 경로 이름
        schoolScnu.setFilepath("/img/" + fileName);
        schoolRepository.save(schoolScnu);
    }

    public List<SchoolScnu> index(){
        return schoolRepository.findAll();
    }

    public SchoolScnu show(Long id){
        return schoolRepository.findById(id).orElse(null);
    }

    public SchoolScnu create(SchoolForm dto) {
        SchoolScnu schoolScnu = dto.toEntity();
        if (schoolScnu.getId() != null){
            return null;
        }
        return schoolRepository.save(schoolScnu);
    }
    public SchoolScnu update(Long id, SchoolForm dto) {
        // 1: DTO -> 엔티티
        SchoolScnu schoolScnu = dto.toEntity();
        // 2: 타겟 조회
        SchoolScnu target = schoolRepository.findById(id).orElse(null);
        // 3: 잘못된 요청 처리
        if (target == null || id != schoolScnu.getId()) {
            return null;
        }
        // 4: 업데이트
        SchoolScnu updated = schoolRepository.save(target);
        return updated;

    }
    public SchoolScnu delete (Long id){
        // 대상 찾기
        SchoolScnu target = schoolRepository.findById(id).orElse(null);


        // 잘못된 요청 처리
        if (target == null) {
            return null;
        }

        // 대상 삭제
        schoolRepository.delete(target);
        return target;
    }





    public String update2(SchoolForm form, MultipartFile file) throws IOException {
        SchoolScnu schoolScnu = form.toEntity();
        SchoolScnu target = schoolRepository.findById(schoolScnu.getId()).orElse(null);
        ArrayList<MainEntity> mainEntity = mainRepository.findAll();

        for(MainEntity entity : mainEntity) {
            if(target.getLatitude() == entity.getLatitude()){
                MainEntity target2 = entity;
                mainRepository.delete(target2);
            }
        }


        if (target != null) {
            if (file.isEmpty() == true) {
                schoolScnu.setFilename("base.png");
                schoolScnu.setFilepath(schoolScnu.getFilepath());
                schoolRepository.save(schoolScnu);
                mainRepository.save(schoolScnu.toMainEntity());
                String page = "redirect:/scnu/school";
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
                schoolScnu.setFilename(fileName);
                // 파일 경로 이름
                schoolScnu.setFilepath("/img/" + fileName);
                schoolRepository.save(schoolScnu);
                mainRepository.save(schoolScnu.toMainEntity());
                String page = "redirect:/scnu/";
                return page;
            }
        }
        String page = "redirect:/scnu/";
        return page;
    }
    public String delete2(Long id, RedirectAttributes rttr ) {
        SchoolScnu target = schoolRepository.findById(id).orElse(null);

        //메인 삭제
        ArrayList<MainEntity> mainEntity = mainRepository.findAll();

        for(MainEntity entity : mainEntity) {
            if(target.getLatitude() == entity.getLatitude()){
                MainEntity target2 = entity;
                mainRepository.delete(target2);
            }
        }
        if (target != null) {
            schoolRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제 완료");
            String page = "redirect:/scnu/school";
            return page;
        }
        String page = "redirect:/scnu/school";
        return page;
    }



}