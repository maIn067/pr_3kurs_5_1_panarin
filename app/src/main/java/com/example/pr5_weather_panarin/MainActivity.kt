package com.example.pr5_weather_panarin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var city1: AppCompatEditText
    private lateinit var tvCity: AppCompatTextView
    private lateinit var tvTemp: AppCompatTextView
    private lateinit var tvPressure: AppCompatTextView
    private lateinit var tvWind: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        city1 = findViewById(R.id.city1)
        tvCity = findViewById(R.id.tv_city)
        tvTemp = findViewById(R.id.tv_temp)
        tvPressure = findViewById(R.id.tv_pressure)
        tvWind = findViewById(R.id.tv_wind)
        val btnGet: AppCompatButton = findViewById(R.id.btn_get)

        btnGet.setOnClickListener {
            getResult(city1.text.toString().trim())
        }
    }

    private fun getResult(city: String) {
        if (city.isEmpty()) {
            Toast.makeText(this, R.string.pods, Toast.LENGTH_SHORT).show()
            return
        }

        val key = "19a20e25fbd1facec9becba57b7a9f14"
        val url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=" + key + "&units=metric&lang=ru"

        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val obj = JSONObject(response)
                val name = obj.getString("name")
                val main = obj.getJSONObject("main")
                val temp = main.getString("temp")
                val pressure = main.getString("pressure")
                val wind = obj.getJSONObject("wind").getString("speed")

                Log.d("MyLog", "Город: $name")
                Log.d("MyLog", "Температура: $temp")

                tvCity.text = getString(R.string.city, name)
                tvTemp.text = getString(R.string.temp, temp)
                tvPressure.text = getString(R.string.pressure, pressure)
                tvWind.text = getString(R.string.wind, wind)
            },
            {
                Log.d("MyLog", "Volley error: $it")
                Toast.makeText(this, R.string.not_found, Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(stringRequest)
    }
}
