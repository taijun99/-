package com.example.soleproject.repository;

import com.example.soleproject.entity.ClubScnu;
import com.example.soleproject.entity.ExtracurricularScnu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ExtracurricularRepository extends JpaRepository<ExtracurricularScnu,Long> {
    @Override
    ArrayList<ExtracurricularScnu> findAll();
}
