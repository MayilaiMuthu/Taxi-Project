package com.example.demo.contoller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.APIResponse;
import com.example.demo.dto.BookTaxiInput;
import com.example.demo.service.TaxiService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *@author MayilaiMuthu
 *@since 15-07-2013
 */

@RequestMapping("/taxi")
@AllArgsConstructor
@Slf4j
@RestController
public class TaxiController {
	
	private TaxiService service;

	@GetMapping("/health-check")
	public ResponseEntity<APIResponse<String>> healthCheck() {
		log.info("CURD Controller : Method Name is => healthCheck()");
		return new ResponseEntity<>(service.healthCheck(), HttpStatus.OK);
	}
	
	@PostMapping("/book-taxi")
	public ResponseEntity<APIResponse<?>> bookTaxi(@RequestBody @Valid BookTaxiInput bookTaxiInput) {
		log.info("Taxi Controller : Method Name is => bookTaxi()");
		log.debug("Inputs are : " + bookTaxiInput);
		return new ResponseEntity<>(service.bookTaxi(bookTaxiInput), HttpStatus.CREATED);
	}

}
