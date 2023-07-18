package com.example.demo.dto;

import java.time.LocalDateTime;
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
@AllArgsConstructor
@NoArgsConstructor
public class BookTaxiInput {
	
	@NotNull(message = "Customer Id Should not be Null")
	private long customerId;
	
	@NotNull(message = "Start Station Name Should not be Null")
	private String startStationName;
	
	@NotNull(message = "End Station Name Should not be Null")
	private String endStationName;
	
	@NotNull(message = "Travel Start Date Time Should not be Null")
	@DateTimeFormat(pattern = ApplicationConstants.DATE_TIME_FORMAT)
	private LocalDateTime travelStartDateTime;

}
