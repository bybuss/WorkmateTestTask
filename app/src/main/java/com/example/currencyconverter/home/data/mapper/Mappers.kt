package com.example.currencyconverter.home.data.mapper

import com.example.currencyconverter.home.data.data_source.remote.dto.RateDto
import com.example.currencyconverter.home.domain.models.Rate

fun RateDto.toDomain() = Rate(currency = this.currency, value = this.value)

fun List<RateDto>.toDomain() = map { it.toDomain() }