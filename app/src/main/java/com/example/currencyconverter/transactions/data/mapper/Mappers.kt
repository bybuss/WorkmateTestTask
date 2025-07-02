package com.example.currencyconverter.transactions.data.mapper

import com.example.currencyconverter.transactions.data.data_source.room.dbo.TransactionDbo
import com.example.currencyconverter.transactions.domain.models.Transaction

fun TransactionDbo.toDomain() = Transaction(
    id = id,
    from = from,
    to = to,
    fromAmount = fromAmount,
    toAmount = toAmount,
    dateTime = dateTime
)

fun Transaction.toDbo() = TransactionDbo(
    id = id,
    from = from,
    to = to,
    fromAmount = fromAmount,
    toAmount = toAmount,
    dateTime = dateTime
)