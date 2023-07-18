package com.example.demo.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.config.ApplicationConstants;

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
@Table(name = "TAXI_DETAILS")
public class TaxiDetails {

	@Id
	@NotNull(message = "Taxi Name Should not be Null")
	@Column(name = "TAXI_NAME", length = 20)
	private String taxiName;
	
	@NotNull(message = "Amount Should not be Null")
	@Column(name = "AMOUNT")
	private long amount;
	
	@Min(value = 1, message = "Avarage Speed Should not be less then zero")
	@NotNull(message = "Avarage Speed Should not be Null")
	@Column(name = "AVG_SPEED")
	private int avgSpeed;
	
	@NotNull(message = "Current Station Name Should not be Null")
	@Column(name = "CURRENT_STATION_NAME", length = 20)
	private String currentStationName;
	
	@DateTimeFormat(pattern = ApplicationConstants.DATE_TIME_FORMAT)
	@Column(name = "LAST_TRAVEL_END_DATE_TIME")
	private LocalDateTime lastTravelEndDateTime;
	
	@Column(name = "LAST_TRAVEL_ID")
	private long lastTravelId;
	
}
