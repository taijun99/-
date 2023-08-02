package com.example.soleproject.repository;

import com.example.soleproject.entity.ClubScnu;
import com.example.soleproject.entity.SchoolScnu;
import com.example.soleproject.entity.StudentScnu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface StudentRepository extends JpaRepository<StudentScnu,Long> {
    @Override
    ArrayList<StudentScnu> findAll();
}
