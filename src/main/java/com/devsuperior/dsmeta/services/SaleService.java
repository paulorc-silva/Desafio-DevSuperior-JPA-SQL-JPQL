package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {
	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<ReportDTO> makeReport(String initialDate, String finalDate, String sellerName, Pageable pageable) {
		LocalDate ldFinalDate = (!"".equals(finalDate)) ?
				LocalDate.parse(finalDate) :
				LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		LocalDate ldInitialDate = (!"".equals(initialDate)) ?
				LocalDate.parse(initialDate) :
				ldFinalDate.minusYears(1L);

		return repository.makeReport(ldInitialDate, ldFinalDate, sellerName, pageable);
	}

	public List<SummaryDTO> makeSummary(String initialDate, String finalDate) {
		LocalDate ldFinalDate = (!"".equals(finalDate)) ?
				LocalDate.parse(finalDate) :
				LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		LocalDate ldInitialDate = (!"".equals(initialDate)) ?
				LocalDate.parse(initialDate) :
				ldFinalDate.minusYears(1L);

		return repository.makeSummary(ldInitialDate, ldFinalDate);
	}
}
