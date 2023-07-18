package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CustomerDetails;

/**
 * @author MayilaiMuthu
 * @since 16-07-2023
 *
 */
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Long> {

}