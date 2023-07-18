package com.example.demo.config;

import javax.validation.Valid;

import com.example.demo.dto.BookTaxiInput;

/**
 * @author MayilaiMuthu
 * @since 16-07-2023
 *
 */

public interface TaxiBookingMethods {
	
	APIResponse<String> healthCheck();
	
	APIResponse<?> bookTaxi(@Valid BookTaxiInput bookTaxiInput);

}
