package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.TaxiDetails;

/**
 * @author MayilaiMuthu
 * @since 16-07-2023
 *
 */
public interface TaxiDetailsRepository extends JpaRepository<TaxiDetails, String> {

}