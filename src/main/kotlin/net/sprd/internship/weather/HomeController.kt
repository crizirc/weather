package net.sprd.internship.weather

import net.sprd.internship.weather.openweathermap.OpenWeatherMapService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController(val service: OpenWeatherMapService) {

    @GetMapping("/", produces = [MediaType.TEXT_HTML_VALUE])
    fun deliverImage() : String {
        val weather = service.getCurrentWeather(51.33, 12.35)
        return """
             <!DOCTYPE html>
<html>
<head>
<title>Wetter</title>
</head>
<body>
<table>
    <tr>
        <th>Ort</th>
        <th>Temp.</th>
        <th>Wetter</th>
    </tr>
    <tr>
        <td>${weather.city}</td>
        <td>${weather.temperature}°C </td>
        <td>${weather.condition}</td>
    </tr>
    
        
        
</table>
</body>
</html> 
        """.trimIndent()
    }
}