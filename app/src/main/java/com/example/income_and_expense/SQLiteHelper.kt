package com.example.income_and_expense

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_NAME = "db_incomde_expense"
const val VERSION = 1

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {

    private var sqLiteHelper: SQLiteHelper? = null

    fun getInstance(c: Context): SQLiteHelper? {
        if (sqLiteHelper != null) {
            sqLiteHelper = SQLiteHelper(c.applicationContext)
        }
        return sqLiteHelper
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE table_income_expense " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date INTEGER, " +
                "month INTEGER, " +
                "year INTEGER, " +
                "type TEXT, " +
                "title TEXT, " +
                "amount INTEGER"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}
