package com.multiplex.service;

import com.multiplex.dto.EarningsInputDto;
import com.multiplex.dto.EarningsOutputDto;

public interface EarningsService {
	EarningsOutputDto generateEarningsReport(EarningsInputDto earningsInputDto);
}
