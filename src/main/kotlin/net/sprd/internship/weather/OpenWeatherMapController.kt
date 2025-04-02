package net.sprd.internship.weather

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController

class OpenWeatherMapController{

    @GetMapping("/openweathermap/current")
    fun getCurrentWeather():String{
        return ""
    }
}