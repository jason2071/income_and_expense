package com.example.income_and_expense

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.income_and_expense.database.IncomeExpenseDBHelper
import android.provider.BaseColumns._ID
import android.util.Log
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_AMOUNT
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_DATE
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_MONTH
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_TITLE
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_TYPE
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_YEAR
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.TABLE_NAME
import kotlinx.android.synthetic.main.activity_income_expense.*


class IncomeExpenseActivity : AppCompatActivity() {

    private lateinit var mDatabase: SQLiteDatabase
    private var amountIncome = 0
    private var amountExpense = 0
    private var amountTotal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income_expense)
        toolbar.title = ""
        setSupportActionBar(toolbar)

        val dbHelper = IncomeExpenseDBHelper(this)
        mDatabase = dbHelper.writableDatabase

        Common.amountList = getAmountList()

        for (i in 0 until Common.amountList.size) {
            val item = Common.amountList[i]
            if (item.type == Common.TYPE_INCOME) amountIncome += item.amount
            if (item.type == Common.TYPE_EXPENSE) amountExpense += item.amount
        }

        amountTotal = amountIncome - amountExpense

        tvDisplayIncome.text = amountIncome.toString()
        tvDisplayExpense.text = amountExpense.toString()
        tvDisplayTotal.text = amountTotal.toString()
    }

    private fun getAmountList(): ArrayList<Amount> {
        Common.amountList.clear()
        val cursor = getAllItem()
        cursor.moveToFirst()
        val amountList = ArrayList<Amount>()
        while (!cursor.isAfterLast) {
            amountList.add(getAmount(cursor)!!)
            cursor.moveToNext()
        }
        cursor.close()
        return amountList
    }

    private fun getAmount(cursor: Cursor): Amount? {
        try {
            val id = cursor.getLong(cursor.getColumnIndex(_ID))
            val date = cursor.getInt(cursor.getColumnIndex(COLUMN_DATE))
            val month = cursor.getInt(cursor.getColumnIndex(COLUMN_MONTH))
            val year = cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR))
            val type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE))
            val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
            val amount = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT))
            return Amount(id, date, month, year, type, title, amount)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun getAllItem(): Cursor {
        return mDatabase.query(
            TABLE_NAME
            , null
            , null
            , null
            , null
            , null
            , "$_ID DESC"
        )
    }

    private fun log(s: String) {
        Log.d("IncomeExpenseActivityA", s)
    }
}
