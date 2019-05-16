package com.example.income_and_expense

data class Amount(
    var _id: Long
    , var date: Int
    , var month: Int
    , var year: Int
    , var type: String?
    , var title: String?
    , var amount: Int
)