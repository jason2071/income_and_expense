package com.example.income_and_expense.database

import android.provider.BaseColumns

class IncomeExpenseContract {
    class IncomeExpenseEntry : BaseColumns {
        companion object {
            const val TABLE_NAME = "table_income_expense"
            const val COLUMN_DATE = "date"
            const val COLUMN_MONTH = "month"
            const val COLUMN_YEAR = "year"
            const val COLUMN_TYPE = "type"
            const val COLUMN_TITLE = "title"
            const val COLUMN_AMOUNT = "amount"
        }
    }
}