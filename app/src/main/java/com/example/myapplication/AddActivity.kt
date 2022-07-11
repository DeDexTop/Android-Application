package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class AddActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_menu_form)


        val buttonAdd = findViewById<Button>(R.id.addButton)

    buttonAdd.setOnClickListener {
        val url = URL("https://menuapi-ow1.conveyor.cloud/InsertData")
        val inputMenuName = findViewById<EditText>(R.id.textMenuName).text.toString()
        val inputDescription = findViewById<EditText>(R.id.textDescription).text.toString()
        val inputPrice = findViewById<EditText>(R.id.textPrice).text.toString()
        thread {
            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "POST"
                addRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                with(outputStream.bufferedWriter()) {
                    write(
                        "name=${Uri.encode(inputMenuName.toString())}&description=${
                            Uri.encode(
                                inputDescription.toString()
                            )
                        }&price=${Uri.encode(inputPrice.toString())}"
                    )
                    flush()
                }
                if (responseCode == 200) {
                    runOnUiThread {
                        Toast.makeText(
                            this@AddActivity,
                            "Data Berhasil di Masukan",
                            Toast.LENGTH_SHORT
                        ).show()
                        this@AddActivity.finish()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            this@AddActivity,
                            "Error Harap Hubungi admin",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
    }
}