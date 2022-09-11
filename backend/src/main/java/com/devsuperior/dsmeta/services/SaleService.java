package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository saleRepository;
	
	@Transactional(readOnly = true)
	public Page<Sale> findSales(String minDate, String maxDate, Pageable pageable) {
		
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate lastYear = today.minusYears(1);
	
		LocalDate min = null;
		LocalDate max = null;
		
		if(minDate.equals("")) {
			min = lastYear;
		} else {
			min = LocalDate.parse(minDate);
		}
			
		if(maxDate.equals("")) {
			max = today;
		} else {
			max = LocalDate.parse(maxDate);
		}
		
		//Maneira alternativa usando ternarios:
		//LocalDate min = minDate.equals("") ? today.minusDays(365) : LocalDate.parse(minDate);
		//LocalDate max = maxDate.equals("") ? today : LocalDate.parse(maxDate);
		
		return saleRepository.findSales(min, max, pageable);
	}
}
