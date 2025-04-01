package net.sprd.internship.weather.openweathermap

import org.springframework.http.HttpMethod
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import java.net.URI

@Service
class OpenWeatherMapService {

    fun getCurrentWeather(latitude: Double, longitude: Double): CurrentWeatherDto {
        // https://api.openweathermap.org/data/2.5/forecast?lat=51.2&lon=12.22&appid=8d151fa8160db5287a61fc40f0b091e5&units=metric
        val requestUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=${latitude}&lon=${longitude}&appid=8d151fa8160db5287a61fc40f0b091e5&units=metric"
        val weatherData = RestClient.create(requestUrl).get().retrieve().body<WeatherData>()
        System.out.println(weatherData!!.list[0].weather[0].description)
        System.out.println(weatherData.list[1].weather[0].description)
        System.out.println(weatherData.list[2].weather[0].description)


        val temps = listOf(
            TemperatureEntry(weatherData.list[0].main.temp, weatherData.list[0].weather[0].description),
            TemperatureEntry(weatherData.list[1].main.temp, weatherData.list[1].weather[0].description),
            TemperatureEntry(weatherData.list[2].main.temp, weatherData.list[2].weather[0].description)
        )
        return CurrentWeatherDto(weatherData.city.name, temps )
    }
}

data class WeatherData(val city:City,val list: List<Forecast>)
data class Forecast(val main:Main,val weather: List<WeatherEntry>)
data class WeatherEntry(val main:String,val description:String)
data class Main(val temp:Double)
data class City(val name:String)

data class CurrentWeatherDto(val city: String, val temperatures:List<TemperatureEntry>)
data class TemperatureEntry(val temperature: Double, val condition: String)