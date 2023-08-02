package com.example.soleproject.repository;

import com.example.soleproject.entity.ClubScnu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ClubRepository extends JpaRepository<ClubScnu,Long> {
    @Override
    ArrayList<ClubScnu> findAll();
}
