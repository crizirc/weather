package net.sprd.internship.weather.openweathermap

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Service
class OpenWeatherMapService(@Value("\${appId}") private val appId:String) {

    @Cacheable("getCurrentWeather")
    fun getCurrentWeather(latitude: Double, longitude: Double, anzahleintraege: Int): CurrentWeatherDto {
        val requestUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=${latitude}&lon=${longitude}&appid=${appId}&units=metric"
        val weatherData = RestClient.create(requestUrl).get().retrieve().body<WeatherData>()

        System.out.println(weatherData!!.list[0].dt_txt)
        System.out.println(weatherData.list[1].weather[0].description)
        System.out.println(weatherData.list[2].weather[0].description)


        val temps = mutableListOf<TemperatureEntry>()
        for(i in 0..<anzahleintraege) {
            temps.add(TemperatureEntry(weatherData.list[i].main.temp, weatherData.list[i].weather[0].description,weatherData.list[i].dt_txt))

        }

        return CurrentWeatherDto(
            weatherData.city.name, temps

        )
    }
}

data class WeatherData(val city:City,val list: List<Forecast>)
data class Forecast(val main:Main,val weather: List<WeatherEntry>,val dt_txt:String)
data class WeatherEntry(val main:String,val description:String)
data class Main(val temp:Double)
data class City(val name:String)


data class CurrentWeatherDto(val city: String, val temperatures:List<TemperatureEntry>)
data class TemperatureEntry(val temperature: Double, val condition: String,val dateTime: String)
