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

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_form)


        val buttonRegister =
            findViewById<Button>(R.id.registerButton)


        buttonRegister.setOnClickListener{
            val url = URL("https://menuapi-ow1.conveyor.cloud/Register")
            val inputUsername =
                findViewById<EditText>(R.id.userName).text.toString()
            val inputPassword =
                findViewById<EditText>(R.id.passWord).text.toString()
            val confirmPassword =
                findViewById<EditText>(R.id.confirmPassword).text.toString()

            if(confirmPassword != inputPassword){
                Toast.makeText(
                    this@RegisterActivity,
                    "Password tidak sama",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            thread {
                with(url.openConnection() as HttpURLConnection) {
                    requestMethod = "POST"
                    addRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                    with(outputStream.bufferedWriter()){
                        write("name=${Uri.encode(inputUsername)}&password=${Uri.encode(inputPassword)}")
                        flush()
                    }
                    if (responseCode == 200) {
                        runOnUiThread {
                            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))

                            Toast.makeText(
                                this@RegisterActivity,
                                "Akun Berhasil Di Buat",
                                Toast.LENGTH_SHORT
                            ).show()
                            this@RegisterActivity.finish()
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(
                                this@RegisterActivity,
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