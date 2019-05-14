package com.example.income_and_expense.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_AMOUNT
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_DATE
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_MONTH
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_TITLE
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_TYPE
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_YEAR
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.TABLE_NAME

class IncomeExpenseDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE $TABLE_NAME (" +
                "$_ID INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", $COLUMN_DATE INTEGER" +
                ", $COLUMN_MONTH INTEGER" +
                ", $COLUMN_YEAR INTEGER" +
                ", $COLUMN_TYPE TEXT" +
                ", $COLUMN_TITLE TEXT" +
                ", $COLUMN_AMOUNT INTEGER )"

        db!!.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $COLUMN_TITLE")
    }

    companion object {
        const val DATABASE_NAME = "income_expense.db"
        const val DATABASE_VERSION = 1
    }
}