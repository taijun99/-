package com.example.soleproject.repository;

import com.example.soleproject.entity.ClubScnu;
import com.example.soleproject.entity.ScholarshipScnu;
import com.example.soleproject.entity.SchoolScnu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface SchoolRepository extends JpaRepository<SchoolScnu,Long> {
    @Override
    ArrayList<SchoolScnu> findAll();
}
