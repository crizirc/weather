package net.sprd.internship.weather

import net.sprd.internship.weather.openweathermap.OpenWeatherMapService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController(val service: OpenWeatherMapService) {

    @GetMapping("/", produces = [MediaType.TEXT_HTML_VALUE])
    fun deliverImage() : String {
        //☀🌥☁🌧⛈
        val weather = service.getCurrentWeather(51.33, 12.35,5)
        val tableRows = mutableListOf<String>()
        weather.temperatures.forEach { temperature ->
            tableRows.add("""
    <tr>
        <td>${weather.city}</td>
        <td>${temperature.temperature}°C </td>
        <td>${temperature.condition}</td>
        <td>${temperature.dateTime}</td>
    </tr>            
        """.trimIndent()) }
        return """
             <!DOCTYPE html>
<html>
<head>
   <style>
   
   td {
     padding: 20px;
     padding-top: 6px;
   }
   </style>
  <title>Wetter</title>
</head>
<body>
  <div>
    <div style="font-size:13px;">
      ${weather.temperatures[0].dateTime}
    </div>
    <div style="font-size:24px">
      ${weather.city}
    </div>
    <div style="font-size: 40px;">
      ☀
        ${weather.temperatures[0].temperature}°C
    </div>
  </div>
  <table style="font-size:18px;">
    ${tableRows.joinToString("")}        
  </table>
</body>
</html> 
        """.trimIndent()
    }
}