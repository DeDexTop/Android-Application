package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonLogin: Button = findViewById(R.id.loginButton)
        val buttonRegister: Button = findViewById(R.id.registerButton)


        buttonRegister.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
        }

        buttonLogin.setOnClickListener {


                val url = URL("https://menuapi-ow1.conveyor.cloud/Login")
                val inputUsername =
                    findViewById<EditText>(R.id.editTextTextEmailAddress)
                val inputPassword =
                    findViewById<EditText>(R.id.editTextTextPassword)


                thread {
                    with(url.openConnection() as HttpURLConnection) {


                        requestMethod = "POST"
                        addRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                        with(outputStream.bufferedWriter()){
                            write("name=${Uri.encode(inputUsername.text.toString())}&password=${Uri.encode(inputPassword.text.toString())}")
                            flush()
                        }
                        if (responseCode == 200) {
                            runOnUiThread {
                                startActivity(Intent(this@MainActivity, MenuActivity::class.java))
                                inputUsername.text.clear()
                                inputPassword.text.clear()
                            }
                        } else {
                            runOnUiThread {
                                Toast.makeText(
                                     this@MainActivity,
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
