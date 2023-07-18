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
@Table(name = "CUSTOMER_DETAILS")
public class CustomerDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull(message = "Customer Id Should not be Null")
	@Column(name = "CUSTOMER_ID")
	private long customerId;
	
	@NotNull(message = "Customer Name Should not be Null")
	@Column(name = "CUSTOMER_NAME", length = 20)
	private String customerName;
	
	@NotNull(message = "Balance Amount Should not be Null")
	@Column(name = "BALANCE_AMOUNT")
	private long balanceAmount;
	
	@NotNull(message = "Travel End Date Time Should not be Null")
	@DateTimeFormat(pattern = ApplicationConstants.DATE_TIME_FORMAT)
	@Column(name = "LAST_TRAVEL_END_DATE_TIME")
	private LocalDateTime lastTravelEndDateTime;

}
