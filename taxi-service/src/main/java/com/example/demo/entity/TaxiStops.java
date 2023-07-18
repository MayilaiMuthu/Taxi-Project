package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MayilaiMuthu
 * @since 16-07-2023
 *
 */
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Entity
@Table(name = "TAXI_STOPS")
public class TaxiStops {

	@Id
	@Min(value = 0, message = "Distance Should not be less then 0")
	@NotNull(message = "Distance Should not be Null")
	@Column(name = "DISTANCE")
	private long distance;
	
	@NotNull(message = "Station Name Should not be Null")
	@Column(name = "STATION_NAME", length = 20)
	private String stationName;

}
