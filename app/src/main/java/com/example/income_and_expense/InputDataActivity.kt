package com.example.income_and_expense

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import android.provider.BaseColumns._ID
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_AMOUNT
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_DATE
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_MONTH
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_TITLE
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_TYPE
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_YEAR
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.TABLE_NAME
import com.example.income_and_expense.database.IncomeExpenseDBHelper
import kotlinx.android.synthetic.main.activity_input_data.*
import java.text.SimpleDateFormat
import java.util.*


class InputDataActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var mDatabase: SQLiteDatabase
    private var mType = "-"
    private var mAmount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_data)

        val dbHelper = IncomeExpenseDBHelper(this)
        mDatabase = dbHelper.writableDatabase

        btnAddDate.setOnClickListener { setCalendar() }

        btnSaveData.setOnClickListener { saveData() }
    }

    private fun setCalendar() {
        val datePicker = DatePickerFragment()
        datePicker.show(supportFragmentManager, "date picker")
    }

    private fun saveData() {
        if (radioIncome.isChecked) {
            mType = "+"
        } else if (radioExpense.isChecked){
            mType = "-"
        }

        if (editDate.text.toString().trim().isEmpty()) {
            return
        }

        if (editTitle.text.toString().trim().isEmpty()) {
            return
        }

        mAmount = editAmount.text.toString().toInt()

        if (editAmount.text.toString().trim().isEmpty() || mAmount == 0) {
            return
        }

        val dateString = editDate.text.toString()
        val title = editTitle.text.toString()
        val amount = editAmount.text.toString()

        val dateArray = dateString.split("-")
        val d = dateArray[0]
        val m = dateArray[1]
        val y = dateArray[2]

        val cv =ContentValues()
        cv.put(COLUMN_DATE, d)
        cv.put(COLUMN_MONTH, m)
        cv.put(COLUMN_YEAR, y)
        cv.put(COLUMN_TYPE, mType)
        cv.put(COLUMN_TITLE, title)
        cv.put(COLUMN_AMOUNT, amount)

        mDatabase.insert(TABLE_NAME, null, cv)
        editDate.text!!.clear()
        editTitle.text!!.clear()
        editAmount.setText(0)
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

    @SuppressLint("SimpleDateFormat")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val c = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val format = SimpleDateFormat("dd-MM-yyyy")
        val currentDateString = format.format(c.time)
        editDate.setText(currentDateString)
    }

    private fun log(s: String) {
        Log.d("InputDataActivityA", s)
    }

    private fun toast(s: String) {
        Toast.makeText(this@InputDataActivity, s, Toast.LENGTH_SHORT).show()
    }
}


