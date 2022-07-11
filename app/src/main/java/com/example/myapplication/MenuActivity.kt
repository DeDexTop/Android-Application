package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MenuActivity: AppCompatActivity() {
    lateinit var recyclerView: RecyclerView

    override fun onRestart() {
        super.onRestart()
        refresh()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_form)

        recyclerView = findViewById(R.id.rv_menuList)
        val floatingActionButton : FloatingActionButton = findViewById(R.id.floatingActionButton)
        //val editButton = findViewById<Button>(R.id.editButton)

        refresh()

        floatingActionButton.setOnClickListener{
            startActivity(Intent(this@MenuActivity, AddActivity::class.java))
        }
    }
    fun refresh(){
        val url = URL("https://menuapi-ow1.conveyor.cloud/GetData")

        thread {
            with(url.openConnection() as HttpURLConnection) {

                val result = inputStream.bufferedReader().readText()

                val a = JSONArray(result)

                if (responseCode == 200){
                    runOnUiThread{
                        recyclerView.adapter = MenuAdapter(this@MenuActivity,a)
                        recyclerView.layoutManager = LinearLayoutManager(this@MenuActivity)
                    }
                }
                else{
                    runOnUiThread {
                        Toast.makeText(this@MenuActivity, "Error Harap Hubungi admin", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

}}