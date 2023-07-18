package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.example.demo.config.APIResponse;
import com.example.demo.config.ApplicationConstants;
import com.example.demo.config.Message;
import com.example.demo.config.TaxiBookingMethods;
import com.example.demo.dto.BookTaxiInput;
import com.example.demo.entity.CustomerDetails;
import com.example.demo.entity.TaxiDetails;
import com.example.demo.entity.TaxiStops;
import com.example.demo.entity.TravelDetails;
import com.example.demo.repository.CustomerDetailsRepository;
import com.example.demo.repository.TaxiDetailsRepository;
import com.example.demo.repository.TaxiStopsRepository;
import com.example.demo.repository.TravelDetailsRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author MayilaiMuthu
 * @since 15-07-2013
 */

@Slf4j
@Service
public class TaxiService implements TaxiBookingMethods {

	@Autowired
	private CustomerDetailsRepository customerDetailsRepository;

	@Autowired
	private TaxiStopsRepository taxiStopsRepository;

	@Autowired
	private TaxiDetailsRepository taxiDetailsRepository;

	@Autowired
	private TravelDetailsRepository travelDetailsRepository;

	@Autowired
	private Message message;

	@Override
	public APIResponse<String> healthCheck() {
		log.info("Taxi Service : Method Name is => healthCheck() :: Execution Started..................");
		// Builder Design Pattern
		APIResponse<String> response = APIResponse.<String>builder().results("GOOD")
				.status(ApplicationConstants.MESSAGE_SUCCESS).build();
		log.info("The Output is : " + response.getResults());
		log.info("Taxi Service : Method Name is => healthCheck() :: Execution Ended....................");
		return response;
	}

	@Override
	public APIResponse<?> bookTaxi(@Valid BookTaxiInput bookTaxiInput) {
		log.info("Taxi Service : Method Name is => bookTaxi() :: Execution Started............");
		APIResponse<?> response = new APIResponse<>();
		if (bookTaxiInput != null) {
			if (LocalDateTime.now().isBefore(bookTaxiInput.getTravelStartDateTime())) {
				CustomerDetails customerDetails = customerDetailsRepository.findById(bookTaxiInput.getCustomerId())
						.orElse(null);
				if (customerDetails != null) {
					String sName = validateStations(bookTaxiInput.getStartStationName());
					if (sName != null) {
						String eName = validateStations(bookTaxiInput.getEndStationName());
						if (eName != null) {
							String taxiName = findTaxi(sName);
							if (taxiName == null) {
								List<String> list = findNearByStations(sName);
								for (int i = 0; i < list.size(); i++) {
									String nearByTaxi = findTaxi(list.get(i));
									if (nearByTaxi != null) {
										taxiName = nearByTaxi;
									}
								}
							}
							if (taxiName != null) {
								TaxiDetails taxiDetails = taxiDetailsRepository.findById(taxiName).orElse(null);
								String taxiStationName = taxiDetails.getCurrentStationName();
								long reachDistance = calculateTravelDistance(taxiStationName,
										bookTaxiInput.getStartStationName());
								LocalDateTime rechTime = calculateTravelEndTime(reachDistance, taxiName,
										bookTaxiInput.getTravelStartDateTime());
								long taxiDistance = calculateTravelDistance(taxiStationName,
										bookTaxiInput.getEndStationName());
								LocalDateTime endTime = calculateTravelEndTime(taxiDistance, taxiName,
										bookTaxiInput.getTravelStartDateTime());
								long customerDistance = calculateTravelDistance(bookTaxiInput.getStartStationName(),
										bookTaxiInput.getEndStationName());
								long travelCost = calculateMoney(customerDistance);
								if (travelCost <= customerDetails.getBalanceAmount()) {
									TravelDetails travelDetails = new TravelDetails();
									travelDetails.setCustomerId(bookTaxiInput.getCustomerId());
									travelDetails.setEndStationName(bookTaxiInput.getEndStationName());
									travelDetails.setLastTravelEndDateTime(endTime);
									travelDetails.setLastTravelStartDateTime(bookTaxiInput.getTravelStartDateTime());
									travelDetails.setStartStationName(bookTaxiInput.getStartStationName());
									travelDetails.setTaxiName(taxiName);
									travelDetails.setTravelAmount(travelCost);
									TravelDetails details = travelDetailsRepository.save(travelDetails);
									travelDetailsRepository.flush();
									taxiDetails.setAmount(travelCost);
									taxiDetails.setLastTravelId(details.getTravelId());
									taxiDetails.setCurrentStationName(bookTaxiInput.getEndStationName());
									taxiDetails.setLastTravelEndDateTime(endTime);
									taxiDetailsRepository.save(taxiDetails);
									taxiDetailsRepository.flush();
									customerDetails.setLastTravelEndDateTime(endTime);
									customerDetails.setBalanceAmount(customerDetails.getBalanceAmount() - travelCost);
									customerDetailsRepository.save(customerDetails);
									customerDetailsRepository.flush();
									message.setOutput("Your Taxi Name is " + taxiName + "& it is reach by " + rechTime);
									response = APIResponse.builder().results(message.getOutput())
											.status(ApplicationConstants.MESSAGE_SUCCESS).build();
								} else {
									response = APIResponse.builder().results("Insufficient Balance.........")
											.status(ApplicationConstants.MESSAGE_FAIL).build();
									log.error("The Output is " + ": " + response.getResults());
								}
							} else {
								response = APIResponse.builder().results("Taxi are currently unavailable.........")
										.status(ApplicationConstants.MESSAGE_FAIL).build();
								log.error("The Output is " + ": " + response.getResults());
							}
						} else {
							response = APIResponse.builder().results("End Station Name is Invalid")
									.status(ApplicationConstants.MESSAGE_ERROR).build();
							log.error("The Output is " + ": " + response.getResults());
						}
					} else {
						response = APIResponse.builder().results("Start Station Name is Invalid")
								.status(ApplicationConstants.MESSAGE_ERROR).build();
						log.error("The Output is " + ": " + response.getResults());
					}
				} else {
					response = APIResponse.builder().results("Customer Id is Invalid")
							.status(ApplicationConstants.MESSAGE_ERROR).build();
					log.error("The Output is " + ": " + response.getResults());
				}
			} else {
				response = APIResponse.builder().results("Start Time is Invalid")
						.status(ApplicationConstants.MESSAGE_ERROR).build();
				log.error("The Output is " + ": " + response.getResults());
			}

//				message.setOutput("Saved CURDS are ");
//				List<MCURD> list = new ArrayList<>();
//				curds.stream().forEach(a -> {
//					if (a.getId() != null) {
//						MCURD mcurd = repository.findById(a.getId()).orElse(null);
//						if (mcurd == null) {
//							MCURD m = MCURD.build(a.getId(), a.getName(), balance);
//							list.add(m);
//							message.setOutput(message.getOutput() + ": " + a);
//						} else {
//							message.setOutput(message.getOutput() + ": " + "Already Found : " + a.getId());
//						}
//					} else {
//						message.setOutput(message.getOutput() + ": " + "CURD ID is NULL");
//					}
//				});
//				if (!list.isEmpty()) {
//					repository.saveAll(list);
//					repository.flush();
//					response = APIResponse.builder().results(message.getOutput())
//							.status(ApplicationConstants.MESSAGE_SUCCESS).build();
//					log.info("The Output is " + ": " + response.getResults());
//				} else {
//					response = APIResponse.builder().results(message.getOutput())
//							.status(ApplicationConstants.MESSAGE_FAIL).build();
//					log.error("The Output is " + ": " + response.getResults());
//				}
		} else {
			response = APIResponse.builder().results("Book Taxi Input is NULL")
					.status(ApplicationConstants.MESSAGE_ERROR).build();
			log.error("The Output is " + ": " + response.getResults());
		}

		log.info("Taxi Service : Method Name is => bookTaxi() :: Execution Ended.............");
		return response;
	}

	public String validateStations(String stationName) {
		log.info("Taxi Service : Method Name is => validateStations() :: Execution Started............");
		log.debug("Inputs are : " + stationName);
		List<TaxiStops> taxiStops = taxiStopsRepository.findByStationName(stationName);
		String result = null;
		if (!taxiStops.isEmpty()) {
			result = ApplicationConstants.MESSAGE_SUCCESS;
		}
		log.info("Taxi Service : Method Name is => validateStations() :: Execution Ended.............");
		return result;
	}

	public String findTaxi(String stationName) {
		log.info("Taxi Service : Method Name is => findTaxi() :: Execution Started............");
		log.debug("Inputs are : " + stationName);
		String response = null;
		Sort sort = Sort.by(Direction.ASC, "amount");
		List<TaxiDetails> taxiDetails = taxiDetailsRepository.findAll(sort);
		if (!taxiDetails.isEmpty()) {
			List<String> list = new ArrayList<>();
			taxiDetails.forEach(a -> {
				if ((a.getLastTravelEndDateTime() == null || a.getLastTravelEndDateTime().isBefore(LocalDateTime.now()))
						&& a.getCurrentStationName().equals(stationName)) {
					list.add(a.getTaxiName());
				}
			});
			if (!list.isEmpty()) {
				response = list.get(0);
			}
			log.info("The Output is " + ": " + response);
		}
		log.info("Taxi Service : Method Name is => findTaxi() :: Execution Ended.............");
		return response;
	}

	public List<String> findNearByStations(String stationName) {
		log.info("Taxi Service : Method Name is => findNearByStations() :: Execution Started............");
		log.debug("Inputs are : " + stationName);
		Sort sort = Sort.by(Direction.ASC, "distance");
		List<TaxiStops> taxiStops = taxiStopsRepository.findAll(sort);
		List<String> list = new ArrayList<>();
		if (!taxiStops.isEmpty()) {
			for (int i = 0; i < taxiStops.size(); i++) {
				if (taxiStops.get(i).getStationName().equals(stationName)) {
					list.add(taxiStops.get(i + 1).getStationName());
					if (i != 0) {
						list.add(taxiStops.get(i - 1).getStationName());
					}
				}
			}
		}
		log.info("Taxi Service : Method Name is => findNearByStations() :: Execution Ended.............");
		return list;
	}

	public long calculateTravelDistance(String startStationName, String endStationName) {
		log.info("Taxi Service : Method Name is => calculateTravelDistance() :: Execution Started............");
		log.debug("Inputs are : {}, {}", startStationName, endStationName);
		Sort sort = Sort.by(Direction.ASC, "distance");
		List<TaxiStops> taxiStops = taxiStopsRepository.findAll(sort);
		List<Long> list = new ArrayList<>();
		if (!taxiStops.isEmpty()) {
			taxiStops.stream().forEach(a -> {
				if (a.getStationName().equals(startStationName) || a.getStationName().equals(endStationName)) {
					list.add(a.getDistance());
				}
			});
		}
		long result = Math.max(list.get(0), list.get(1)) - Math.max(list.get(0), list.get(1));
		log.info("Taxi Service : Method Name is => calculateTravelDistance() :: Execution Ended.............");
		return result;
	}

	public long calculateMoney(long distance) {
		log.info("Taxi Service : Method Name is => calculateMoney() :: Execution Started............");
		log.debug("Inputs are : " + distance);
		long result = ((distance - ApplicationConstants.DISCOUNT_DISTANCE) * ApplicationConstants.KM_PRICE)
				+ ApplicationConstants.BASE_FARE;
		log.info("Taxi Service : Method Name is => calculateMoney() :: Execution Ended.............");
		return result;
	}

	public LocalDateTime calculateTravelEndTime(long distance, String taxiName, LocalDateTime startTime) {
		log.info("Taxi Service : Method Name is => calculateTravelEndTime() :: Execution Started............");
		log.debug("Inputs are : {}, {}", distance, taxiName, startTime);
		int avgSpeed = 0;
		TaxiDetails taxiDetails = taxiDetailsRepository.findById(taxiName).orElse(null);
		if (taxiDetails != null) {
			avgSpeed = taxiDetails.getAvgSpeed();
		}
		LocalDateTime result = LocalDateTime.now().plusHours(distance / avgSpeed);
		log.info("Taxi Service : Method Name is => calculateTravelEndTime() :: Execution Ended.............");
		return result;
	}

}
