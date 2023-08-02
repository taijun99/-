package com.example.soleproject.service;

import com.example.soleproject.dto.ClubForm;
import com.example.soleproject.entity.ClubScnu;
import com.example.soleproject.entity.MainEntity;
import com.example.soleproject.repository.ClubRepository;
import com.example.soleproject.repository.MainRepository;
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

public class ClubService {

    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private MainRepository mainRepository;

    public void write(ClubScnu clubScnu, MultipartFile file) throws Exception{
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
        clubScnu.setFilename(fileName);
        // 파일 경로 이름
        clubScnu.setFilepath("/img/" + fileName);
        clubRepository.save(clubScnu);
        mainRepository.save(clubScnu.toMainEntity());
    }


    public void create(ClubScnu clubScnu){
        clubScnu.setFilename("base.png");
        clubScnu.setFilepath("/img/base.png");
        clubRepository.save(clubScnu);
        mainRepository.save(clubScnu.toMainEntity());
    }

    public void update(ClubScnu clubScnu, MultipartFile file) throws Exception{
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
        clubScnu.setFilename(fileName);
        // 파일 경로 이름
        clubScnu.setFilepath("/img/" + fileName);
        clubRepository.save(clubScnu);
    }

    public List<ClubScnu> index(){
        return clubRepository.findAll();
    }

    public ClubScnu show(Long id){
        return clubRepository.findById(id).orElse(null);
    }

    public ClubScnu create(ClubForm dto) {
        ClubScnu clubScnu = dto.toEntity();
        if (clubScnu.getId() != null){
            return null;
        }
        return clubRepository.save(clubScnu);
    }
    public ClubScnu update(Long id, ClubForm dto) {
        // 1: DTO -> 엔티티
        ClubScnu clubScnu = dto.toEntity();
        // 2: 타겟 조회
        ClubScnu target = clubRepository.findById(id).orElse(null);
        // 3: 잘못된 요청 처리
        if (target == null || id != clubScnu.getId()) {
            return null;
        }
        // 4: 업데이트
        ClubScnu updated = clubRepository.save(target);
        return updated;

    }
    public ClubScnu delete (Long id){
        // 대상 찾기
        ClubScnu target = clubRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if (target == null) {
            return null;
        }

        // 대상 삭제
        clubRepository.delete(target);
        return target;
    }





    public String update2(ClubForm form, MultipartFile file) throws IOException {
        ClubScnu clubScnu = form.toEntity();
        ClubScnu target = clubRepository.findById(clubScnu.getId()).orElse(null);
        ArrayList<MainEntity> mainEntity = mainRepository.findAll();

        for(MainEntity entity : mainEntity) {
            if(target.getLatitude() == entity.getLatitude()){
                MainEntity target2 = entity;
                mainRepository.delete(target2);
            }
        }

        if (target != null) {
            if (file.isEmpty() == true) {
                clubScnu.setFilename("base.png");
                clubScnu.setFilepath(clubScnu.getFilepath());
                clubRepository.save(clubScnu);
                mainRepository.save(clubScnu.toMainEntity());
                String page = "redirect:/scnu/club";
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
                clubScnu.setFilename(fileName);
                // 파일 경로 이름
                clubScnu.setFilepath("/img/" + fileName);
                clubRepository.save(clubScnu);
                mainRepository.save(clubScnu.toMainEntity());
                String page = "redirect:/scnu/";
                return page;
            }
        }
        String page = "redirect:/scnu/";
        return page;
    }
    public String delete2(Long id, RedirectAttributes rttr ) {
        ClubScnu target = clubRepository.findById(id).orElse(null);
        ArrayList<MainEntity> mainEntity = mainRepository.findAll();

        for(MainEntity entity : mainEntity) {
            if(target.getLatitude() == entity.getLatitude()){
                MainEntity target2 = entity;
                mainRepository.delete(target2);
            }
        }
        if (target != null) {
            clubRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제 완료");
            String page = "redirect:/scnu/club";
            return page;
        }
        String page = "redirect:/scnu/club";
        return page;
    }



}