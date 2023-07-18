package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.TaxiStops;

/**
 * @author MayilaiMuthu
 * @since 16-07-2023
 *
 */
public interface TaxiStopsRepository extends JpaRepository<TaxiStops, Long> {
	
	List<TaxiStops> findByStationName(String stationName);

}