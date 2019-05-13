package com.example.income_and_expense

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_input_data.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_data)

        inputDate.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus) {
                toast("true")
            } else {
                toast("false")
            }
        }
    }

    private fun toast(s: String) {
        Toast.makeText(this@MainActivity, s, Toast.LENGTH_SHORT).show()
    }
}
