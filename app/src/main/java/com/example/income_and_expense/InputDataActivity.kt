package com.example.income_and_expense

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.example.income_and_expense.database.IncomeExpenseDBHelper
import kotlinx.android.synthetic.main.activity_input_data.*
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Toast
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_AMOUNT
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_DATE
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_MONTH
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_TITLE
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_TYPE
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.COLUMN_YEAR
import com.example.income_and_expense.database.IncomeExpenseContract.IncomeExpenseEntry.Companion.TABLE_NAME


class InputDataActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    private var onItemSelectedListener: AdapterView.OnItemSelectedListener = this
    private lateinit var mDatabase: SQLiteDatabase
    private var mType = Common.TYPE_EXPENSE
    private var mAmount = 0
    private var mTitle = ""
    private var mDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_data)
        supportActionBar?.title = "สร้างรายการใหม่"

        val dbHelper = IncomeExpenseDBHelper(this)
        mDatabase = dbHelper.writableDatabase

        radioIncome.setOnClickListener {
            radioIncome.isChecked = true
            buildSpinner()
        }
        radioExpense.setOnClickListener {
            radioExpense.isChecked = true
            buildSpinner()
        }

        tvDate.setOnClickListener { setCalendar() }
        btnSaveData.setOnClickListener { saveData() }
        buildSpinner()
        setCurrentDate()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setCurrentDate() {
        val c = Calendar.getInstance()
        mDate =  simpleDateFormat(c.time)!!
        tvDate.text =mDate
    }

    private fun buildSpinner() {
        val resource = if (radioIncome.isChecked) R.array.incomeItem else R.array.expenseItem
        val adapter = ArrayAdapter.createFromResource(
            this,
            resource,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = onItemSelectedListener
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mTitle = parent?.getItemAtPosition(position).toString()
    }

    private fun setCalendar() {
        val datePicker = DatePickerFragment()
        datePicker.show(supportFragmentManager, "DatePickerFragment")
    }

    private fun saveData() {
        mType = if (radioIncome.isChecked) Common.TYPE_INCOME else Common.TYPE_EXPENSE

        if (tvDate.text.toString().trim().isEmpty()) {
            return
        }

        mAmount = if (editAmount.text.toString().trim().isEmpty()) 0 else editAmount.text.toString().toInt()

        if (editAmount.text.toString().trim().isEmpty() || mAmount == 0) {
            return
        }

        val dateArray = mDate.split("-")
        val cv = ContentValues()
        cv.put(COLUMN_DATE, dateArray[0])
        cv.put(COLUMN_MONTH, dateArray[1])
        cv.put(COLUMN_YEAR, dateArray[2])
        cv.put(COLUMN_TYPE, mType)
        cv.put(COLUMN_TITLE, mTitle)
        cv.put(COLUMN_AMOUNT, mAmount)
        mDatabase.insert(TABLE_NAME, null, cv)

        startActivity(Intent(this, IncomeExpenseActivity::class.java))
    }

    @SuppressLint("SimpleDateFormat")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val c = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        mDate = simpleDateFormat(c.time)!!
        tvDate.text = mDate
    }

    @SuppressLint("SimpleDateFormat")
    private fun simpleDateFormat(time: Date): String? {
        val format = SimpleDateFormat("dd-MM-yyyy")
        return format.format(time)
    }

    private fun log(s: String) {
        Log.d("InputDataActivityA", s)
    }

    private fun toast(s: String) {
        Toast.makeText(this@InputDataActivity, s, Toast.LENGTH_SHORT).show()
    }
}


