package com.example.currencyconverter.profile.data.mapper

import com.example.currencyconverter.profile.data.data_source.room.dbo.AccountDbo
import com.example.currencyconverter.profile.domain.models.Account

fun AccountDbo.toDomain() = Account(code = code, amount = amount)

fun Account.toDbo() = AccountDbo(code = code, amount = amount)