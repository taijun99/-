package com.example.soleproject.repository;

import com.example.soleproject.entity.ExtracurricularScnu;
import com.example.soleproject.entity.MainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface MainRepository extends JpaRepository<MainEntity, Long> {
    @Override
    ArrayList<MainEntity> findAll();

    Optional<MainEntity> findByLatitude(String latitude);
}
