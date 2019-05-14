package com.example.income_and_expense

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRecordedItem.setOnClickListener {
            toast("btnRecordedItem")
        }

        btnNewItem.setOnClickListener {
            startActivity(Intent(this@MainActivity, InputDataActivity::class.java))
        }

    }

    private fun toast(s: String) {
        Toast.makeText(this@MainActivity, s, Toast.LENGTH_SHORT).show()
    }
}
