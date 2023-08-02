package com.example.soleproject.repository;

import com.example.soleproject.entity.ClubScnu;
import com.example.soleproject.entity.ExtracurricularScnu;
import com.example.soleproject.entity.ScholarshipScnu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ScholarshipRepository extends JpaRepository<ScholarshipScnu,Long> {
    @Override
    ArrayList<ScholarshipScnu> findAll();
}
