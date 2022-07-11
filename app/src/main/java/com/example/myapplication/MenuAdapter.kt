package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MenuAdapter(val activity: MenuActivity, val menu : JSONArray): RecyclerView.Adapter<MenuAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtMenuTitle : TextView = itemView.findViewById(R.id.tvMenuTitle)
        val txtMenuDesc : TextView = itemView.findViewById(R.id.tvMenuDescription)
        val txtMenuPrice : TextView = itemView.findViewById(R.id.tvMenuPrice)
        val deleteButton : Button = itemView.findViewById(R.id.deleteButton)
        val editButton : Button = itemView.findViewById(R.id.editButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_menu,parent,false)
            return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = menu.getJSONObject(position)

        holder.txtMenuTitle?.text = item["name"].toString()
        holder.txtMenuDesc?.text = item["description"].toString()
        holder.txtMenuPrice?.text = item["price"].toString()

        holder.editButton?.setOnClickListener{
            val intent = Intent(activity,EditActivity::class.java)
            intent.putExtra("id", item["id"].toString())
            intent.putExtra("name", item["name"].toString())
            intent.putExtra("description", item["description"].toString())
            intent.putExtra("price",item["price"].toString())
            activity.startActivity(intent)
        }
        holder.deleteButton?.setOnClickListener {
            val id = item["id"].toString()
            val url = URL("https://menuapi-ow1.conveyor.cloud/DeleteData?id=$id")
            thread {
                with(url.openConnection() as HttpURLConnection) {


                    doInput = true
                    requestMethod = "DELETE"
                    addRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                    with(outputStream.bufferedWriter()) {
                        flush()

                    }

                    if (responseCode == 200) {
                        activity.runOnUiThread {
                            Toast.makeText(activity, "Berhasil Hapus Data", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        activity.runOnUiThread {
                            Toast.makeText(
                                activity,
                                "Error Harap Hubungi admin",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }


                    activity.refresh()
                }


            }
        }
    }
    override fun getItemCount(): Int = menu.length()



}