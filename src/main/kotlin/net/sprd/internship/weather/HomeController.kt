package net.sprd.internship.weather

import net.sprd.internship.weather.openweathermap.OpenWeatherMapService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController(
    val service: OpenWeatherMapService
) {
    @GetMapping("/", produces = [MediaType.TEXT_HTML_VALUE])
    fun deliverImage(@RequestParam(required = false) lat: Double=51.33,@RequestParam(required = false) long: Double=12.35,@RequestParam(required = false) stepSize:Int=1 ) : String {

        val weather = service.getCurrentWeather(lat, long,5*stepSize)
        val tableRows = mutableListOf<String>()
        for (i in 0..(weather.temperatures.size -1) step stepSize){
            val temperature = weather.temperatures[i]

            var condition = temperature.condition
            condition = conditionAsSymbol(condition)
                tableRows.add(
                    """
    <tr>
        <td>${weather.city}</td>
        <td>${temperature.temperature}°C </td>
        <td title="${temperature.condition}">${condition}</td>
        <td>${temperature.dateTime}</td>
    </tr>            
        """.trimIndent()
                )

        }

        return """
             <!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Josefin+Sans:ital,wght@0,100..700;1,100..700&display=swap" rel="stylesheet">
<link href="style.css" rel="stylesheet">
  <link rel="manifest" href="/manifest.json">
  <title>Wetter</title>
 <script
 src="main.js"></script>
  
</head>
<body style = "font-family:Josefin Sans, sans-serif;">
  <div class= "currentweatherborder" style="text-align: center;">
    <div style="font-size:0.8vw;">
      ${weather.temperatures[0].dateTime}
    </div>
    <div style="font-size: 2vw">
      ${weather.city}
    </div>
    <div style="font-size: 5vw;" title="${weather.temperatures[0].condition}">
      
      ${conditionAsSymbol(weather.temperatures[0].condition)} ${weather.temperatures[0].temperature}°C
    </div>
  </div>
  <table id="weathertable" style="font-size:2vw;width: 100%; text-align:center">
    ${tableRows.joinToString("")}        
  </table>
  <div classes="positionform"style="display:flex; align-items:center; justify-content:center;">
  
  <form>
  <label for="lat">Lat</label><br>
    <input type="text" id="lat" name="lat"><br>
     <label for="long">Long</label><br>
    <input type="text" id="long" name="long"><br>
       <label for="stepSize">Step Size</label><br>
    <input type = "number" id ="stepSize" name="stepSize"><br>
        

    <input type="submit" value="Submit">
 
  </form>
  </div>
</body>
</html> 
        """.trimIndent()
    }

    private fun conditionAsSymbol(condition: String): String {
        var condition1 = condition
        if (condition1 == "scattered clouds") {
            return "\uD83C\uDF25"
        }
        if (condition1 == "clear sky") {
            return "☀"
        }
        if(condition1.contains("clouds")) {
            return "☁"
        }
        if(condition1.contains("rain")){
            condition1 = "\uD83C\uDF27"
        }
        if (condition1 == "thunderstorm") {
            condition1 = "⛈"
        }
        if (condition1.contains("snow")) {
            return "❄"
        }
        if (condition1 == "fog") {
            condition1 = "\uD83C\uDF2B"
        }
        return condition1
    }
}