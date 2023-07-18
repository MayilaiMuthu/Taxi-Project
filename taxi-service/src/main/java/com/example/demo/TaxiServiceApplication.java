package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

/**
 * @author MayilaiMuthu
 * @since 15-07-2023
 *
 */

@EnableCaching
@EnableEncryptableProperties
@ComponentScan(basePackages = "com.example.*") 
@SpringBootApplication
public class TaxiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxiServiceApplication.class, args);
	}

}
