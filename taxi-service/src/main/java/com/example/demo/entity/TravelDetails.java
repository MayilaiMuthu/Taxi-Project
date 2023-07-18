package com.example.demo.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "TRAVEL_DETAILS")
public class TravelDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull(message = "Travel Id Should not be Null")
	@Column(name = "TRAVEL_ID")
	private long travelId;
	
	@NotNull(message = "Taxi Name Should not be Null")
	@Column(name = "TAXI_NAME", length = 20)
	private String taxiName;
	
	@NotNull(message = "Travel Amount Should not be Null")
	@Column(name = "TRAVEL_AMOUNT")
	private long travelAmount;
	
	@NotNull(message = "Start Station Name Should not be Null")
	@Column(name = "START_STATION_NAME", length = 20)
	private String startStationName;
	
	@NotNull(message = "End Station Name Should not be Null")
	@Column(name = "END_STATION_NAME", length = 20)
	private String endStationName;
	
	@NotNull(message = "Travel Start Date Time Should not be Null")
	@DateTimeFormat(pattern = ApplicationConstants.DATE_TIME_FORMAT)
	@Column(name = "LAST_TRAVEL_START_DATE_TIME")
	private LocalDateTime lastTravelStartDateTime;
	
	@NotNull(message = "Travel End Date Time Should not be Null")
	@DateTimeFormat(pattern = ApplicationConstants.DATE_TIME_FORMAT)
	@Column(name = "LAST_TRAVEL_END_DATE_TIME")
	private LocalDateTime lastTravelEndDateTime;
	
	@NotNull(message = "Customer Id Should not be Null")
	@Column(name = "CUSTOMER_ID")
	private long customerId;

}
