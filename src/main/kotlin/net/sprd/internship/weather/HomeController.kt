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
    fun deliverImage(@RequestParam(required = false) lat: Double=51.33,@RequestParam(required = false) long: Double=12.35) : String {

        val weather = service.getCurrentWeather(lat, long,5)
        val tableRows = mutableListOf<String>()
        var index = 1
        weather.temperatures.forEach { temperature ->
            if (index != 1) {
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
            index= index.plus(1)

        }
        var condition = weather.temperatures[0].condition
        condition = conditionAsSymbol(condition)
        return """
             <!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Josefin+Sans:ital,wght@0,100..700;1,100..700&display=swap" rel="stylesheet">
  <link rel="manifest" href="/manifest.json">
  <title>Wetter</title>
  <script>
        if ('serviceWorker' in navigator) {
          navigator.serviceWorker
            .register('service-worker.js')
            .then(function (reg) {
              console.log('Registration successful: ' + reg.scope);
            })
            .catch(function (err) {
              console.error('Registration failed: ' + err);
            });
        }
  </script>
  <style>
    input{
      font-size: 2vw;
     } 
  /* Light mode */@media (prefers-color-scheme: light) {
      body {
          background-color: #ececec;
          color: #222;
         
      }
      input{color: #222;
  background-color: #ececec;
  border: solid #ececec;
}
      
      .currentweatherborder{
      border-bottom-color: #ececec;border-bottom-width: 4vw;border-bottom-style: solid;
      }
    
  }
  /* Dark mode */@media (prefers-color-scheme: dark) {
       
      body {
          background-color:#222;
          color: #ececec;
      }
      input{
      color: #ececec;
  background-color: #222;
  border: solid #4a4a4a;
}
      .currentweatherborder{
      border-bottom-color: #222;border-bottom-width: 5vw;border-bottom-style: solid;
      }
     
  }
   @media screen and (max-width: 600px) {
         td {
             padding: 10px;
             padding-top: 6px;
             font-size:4vw;
            }
       }
     
  </style>
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
      
      ${condition} ${weather.temperatures[0].temperature}°C
    </div>
  </div>
  <table id="weathertable" style="font-size:2vw;width: 100%; text-align:center">
    ${tableRows.joinToString("")}        
  </table>
  <div classes="positionform"style="display:flex; align-items:center; justify-content:center;">
  
  <form>
    <input type="text" id="lat" name="lat" value="Lat"><br>
    <input type="text" id="long" name="long" value="Long"><br>
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