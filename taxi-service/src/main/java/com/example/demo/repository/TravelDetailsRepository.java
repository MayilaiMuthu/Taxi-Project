package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.TravelDetails;

/**
 * @author MayilaiMuthu
 * @since 16-07-2023
 *
 */
public interface TravelDetailsRepository extends JpaRepository<TravelDetails, Integer> {

}