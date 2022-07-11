package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class EditActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_menu_form)

        val btnEdit : Button = findViewById(R.id.editButton)
        val txtMenuName : EditText = findViewById(R.id.textMenuName)
        val txtDescription : EditText = findViewById(R.id.textDescription)
        val txtPrice : EditText = findViewById(R.id.textPrice)

        txtMenuName.setText(intent.getStringExtra("name") ?: null)
        txtDescription.setText(intent.getStringExtra("description") ?: null)
        txtPrice.setText(intent.getStringExtra("price") ?: null)

        val idMenu = intent.getStringExtra("id") ?: null
        btnEdit.setOnClickListener{
            val url = URL("https://menuapi-ow1.conveyor.cloud/UpdateData")

            thread{
                with(url.openConnection() as HttpURLConnection) {
                    val name = txtMenuName.text.toString()
                    val desc = txtDescription.text.toString()
                    val price = txtPrice.text.toString()


                    doOutput = true
                    doInput = true
                    requestMethod = "PUT"
                    addRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                    with (outputStream.bufferedWriter()) {
                        write("name=${Uri.encode(name)}&description=${Uri.encode(desc)}&price=${Uri.encode(price)}&id=${Uri.encode(idMenu)}")
                        flush()

                    }

                    if (responseCode == 200){
                        val result = inputStream.bufferedReader().readText()

                        // Fungsi Jika Berhasil
                        runOnUiThread {
                            Log.d("data", result)
                            val intent = Intent(this@EditActivity, MenuActivity::class.java)
                            intent.putExtra("status","berhasil edit Menu")
                            startActivity(intent)
                            Toast.makeText(this@EditActivity, "Menu Berhasil Diubah", Toast.LENGTH_SHORT).show()
                        }
                    }

                    else{
                        runOnUiThread{
                            Toast.makeText(this@EditActivity, "Error Harap Hubungi admin", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }
}