package com.example.soleproject.service;

import com.example.soleproject.dto.ClubForm;
import com.example.soleproject.dto.ScholarshipForm;
import com.example.soleproject.entity.ClubScnu;
import com.example.soleproject.entity.ScholarshipScnu;
import com.example.soleproject.repository.ClubRepository;
import com.example.soleproject.repository.MainRepository;
import com.example.soleproject.repository.ScholarshipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ScholarshipService {

    @Autowired
    private ScholarshipRepository scholarshipRepository;
    @Autowired
    private MainRepository mainRepository;

    public void write(ScholarshipScnu scholarshipScnu, MultipartFile file) throws Exception{
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
        scholarshipScnu.setFilename(fileName);
        // 파일 경로 이름
        scholarshipScnu.setFilepath("/img/" + fileName);
        scholarshipRepository.save(scholarshipScnu);
        mainRepository.save(scholarshipScnu.toMainEntity());
    }

    public void create(ScholarshipScnu scholarshipScnu){
        scholarshipScnu.setFilename("base.png");
        scholarshipScnu.setFilepath("/img/base.png");
        scholarshipRepository.save(scholarshipScnu);
        mainRepository.save(scholarshipScnu.toMainEntity());
    }

    public void update(ScholarshipScnu scholarshipScnu, MultipartFile file) throws Exception{
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
        scholarshipScnu.setFilename(fileName);
        // 파일 경로 이름
        scholarshipScnu.setFilepath("/img/" + fileName);
        scholarshipRepository.save(scholarshipScnu);
    }

    public List<ScholarshipScnu> index(){
        return scholarshipRepository.findAll();
    }

    public ScholarshipScnu show(Long id){
        return scholarshipRepository.findById(id).orElse(null);
    }

    public ScholarshipScnu create(ScholarshipForm dto) {
        ScholarshipScnu scholarshipScnu = dto.toEntity();
        if (scholarshipScnu.getId() != null){
            return null;
        }
        return scholarshipRepository.save(scholarshipScnu);
    }
    public ScholarshipScnu update(Long id, ScholarshipForm dto) {
        // 1: DTO -> 엔티티
        ScholarshipScnu scholarshipScnu = dto.toEntity();
        // 2: 타겟 조회
        ScholarshipScnu target = scholarshipRepository.findById(id).orElse(null);
        // 3: 잘못된 요청 처리
        if (target == null || id != scholarshipScnu.getId()) {
            return null;
        }
        // 4: 업데이트
        ScholarshipScnu updated = scholarshipRepository.save(target);
        return updated;

    }
    public ScholarshipScnu delete (Long id){
        // 대상 찾기
        ScholarshipScnu target = scholarshipRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if (target == null) {
            return null;
        }

        // 대상 삭제
        scholarshipRepository.delete(target);
        return target;
    }





    public String update2(ScholarshipForm form, MultipartFile file) throws IOException {
        ScholarshipScnu scholarshipScnu = form.toEntity();
        ScholarshipScnu target = scholarshipRepository.findById(scholarshipScnu.getId()).orElse(null);
        if (target != null) {
            if (file.isEmpty() == true) {
                scholarshipScnu.setFilename("base.png");
                scholarshipScnu.setFilepath(scholarshipScnu.getFilepath());
                scholarshipRepository.save(scholarshipScnu);
                String page = "redirect:/scnu/scholarship";
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
                scholarshipScnu.setFilename(fileName);
                // 파일 경로 이름
                scholarshipScnu.setFilepath("/img/" + fileName);
                scholarshipRepository.save(scholarshipScnu);
                String page = "redirect:/scnu/";
                return page;
            }
        }
        String page = "redirect:/scnu/";
        return page;
    }
    public String delete2(Long id, RedirectAttributes rttr ) {
        ScholarshipScnu scholarshipScnu = scholarshipRepository.findById(id).orElse(null);
        if (scholarshipScnu != null) {
            scholarshipRepository.delete(scholarshipScnu);
            rttr.addFlashAttribute("msg", "삭제 완료");
            String page = "redirect:/scnu/scholarship";
            return page;
        }
        String page = "redirect:/scnu/scholarship";
        return page;
    }



}