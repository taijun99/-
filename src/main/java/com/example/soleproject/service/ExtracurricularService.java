package com.example.soleproject.service;

import com.example.soleproject.dto.ClubForm;
import com.example.soleproject.dto.ExtracurricularForm;
import com.example.soleproject.entity.ExtracurricularScnu;
import com.example.soleproject.entity.MainEntity;
import com.example.soleproject.repository.ExtracurricularRepository;
import com.example.soleproject.repository.MainRepository;
import lombok.extern.slf4j.Slf4j;
import org.jboss.jandex.Main;
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
public class ExtracurricularService {

    @Autowired
    private ExtracurricularRepository extracurricularRepository;

    @Autowired
    private MainRepository mainRepository;

    public void write(ExtracurricularScnu extracurricularScnu, MultipartFile file) throws Exception{
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
        extracurricularScnu.setFilename(fileName);
        // 파일 경로 이름
        extracurricularScnu.setFilepath("/img/" + fileName);
        extracurricularRepository.save(extracurricularScnu);
        mainRepository.save(extracurricularScnu.toMainEntity());
    }

    public void create(ExtracurricularScnu extracurricularScnu){
        extracurricularScnu.setFilename("base.png");
        extracurricularScnu.setFilepath("/img/base.png");
        extracurricularRepository.save(extracurricularScnu);
        mainRepository.save(extracurricularScnu.toMainEntity());
    }

    public void update(ExtracurricularScnu extracurricularScnu, MultipartFile file) throws Exception{
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
        extracurricularScnu.setFilename(fileName);
        // 파일 경로 이름
        extracurricularScnu.setFilepath("/img/" + fileName);
        extracurricularRepository.save(extracurricularScnu);
    }

    public List<ExtracurricularScnu> index(){
        return extracurricularRepository.findAll();
    }

    public ExtracurricularScnu show(Long id){
        return extracurricularRepository.findById(id).orElse(null);
    }

    public ExtracurricularScnu create(ExtracurricularForm dto) {
        ExtracurricularScnu extracurricularScnu = dto.toEntity();
        if (extracurricularScnu.getId() != null){
            return null;
        }
        return extracurricularRepository.save(extracurricularScnu);
    }
    public ExtracurricularScnu update(Long id, ExtracurricularForm dto) {
        // 1: DTO -> 엔티티
        ExtracurricularScnu extracurricularScnu = dto.toEntity();
        // 2: 타겟 조회
        ExtracurricularScnu target = extracurricularRepository.findById(id).orElse(null);
        // 3: 잘못된 요청 처리
        if (target == null || id != extracurricularScnu.getId()) {
            return null;
        }
        // 4: 업데이트
        ExtracurricularScnu updated = extracurricularRepository.save(target);
        return updated;

    }
    public ExtracurricularScnu delete (Long id){
        // 대상 찾기
        ExtracurricularScnu target = extracurricularRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if (target == null) {
            return null;
        }

        // 대상 삭제
        extracurricularRepository.delete(target);
        return target;
    }





    public String update2(ExtracurricularForm form, MultipartFile file) throws IOException {
        ExtracurricularScnu extracurricularScnu = form.toEntity();
        ExtracurricularScnu target = extracurricularRepository.findById(extracurricularScnu.getId()).orElse(null);
        ArrayList<MainEntity> mainEntity = mainRepository.findAll();

        for(MainEntity entity : mainEntity) {
            if(target.getLatitude() == entity.getLatitude()){
                MainEntity target2 = entity;
                mainRepository.delete(target2);
            }
        }


        if (target != null) {
            if (file.isEmpty() == true) {
                extracurricularScnu.setFilename("base.png");
                extracurricularScnu.setFilepath(extracurricularScnu.getFilepath());
                extracurricularRepository.save(extracurricularScnu);
                mainRepository.save(extracurricularScnu.toMainEntity());
                String page = "redirect:/scnu/extracurricular";
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
                extracurricularScnu.setFilename(fileName);
                // 파일 경로 이름
                extracurricularScnu.setFilepath("/img/" + fileName);
                extracurricularRepository.save(extracurricularScnu);
                mainRepository.save(extracurricularScnu.toMainEntity());
                String page = "redirect:/scnu/";
                return page;
            }
        }
        String page = "redirect:/scnu/";
        return page;
    }
    public String delete2(Long id, RedirectAttributes rttr ) {
        ExtracurricularScnu target = extracurricularRepository.findById(id).orElse(null);
        //메인 삭제
        ArrayList<MainEntity> mainEntity = mainRepository.findAll();

        for(MainEntity entity : mainEntity) {
            if(target.getLatitude() == entity.getLatitude()){
                MainEntity target2 = entity;
                mainRepository.delete(target2);
            }
        }
        if (target != null) {
            extracurricularRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제 완료");
            String page = "redirect:/scnu/extracurricular";
            return page;
        }
        String page = "redirect:/scnu/extracurricular";
        return page;
    }



}