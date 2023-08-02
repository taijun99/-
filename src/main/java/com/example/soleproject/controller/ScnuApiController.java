package com.example.soleproject.controller;

import com.example.soleproject.dto.ClubForm;
import com.example.soleproject.entity.ClubScnu;
import com.example.soleproject.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScnuApiController {
    @Autowired
    private ClubService clubService;


    @GetMapping("/api/scnu/{id}")
    public ClubScnu show(@PathVariable Long id) {
        return clubService.show(id);
    }

    @PostMapping("/api/scnu")
    public ResponseEntity<ClubScnu> create(@RequestBody ClubForm dto) { // @RequestBody -> JSON 데이터 받기
        ClubScnu created = clubService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/scnu/{id}")
    public ResponseEntity<ClubScnu> update(@PathVariable Long id,
                                           @RequestBody ClubForm dto) {
        ClubScnu updated = clubService.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/scnu/{id}")
    public ResponseEntity<ClubScnu> delete(@PathVariable Long id){
        ClubScnu deleted = clubService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @GetMapping("/scnu/api/scnu")
    public List<ClubScnu> index() {
        return clubService.index();
    }
}
