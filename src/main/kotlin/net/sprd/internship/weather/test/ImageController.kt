package net.sprd.internship.weather.test

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ImageController {

    @GetMapping("/image", produces = [MediaType.TEXT_HTML_VALUE])
    fun deliverImage() : String {
        return """
             <!DOCTYPE html>
<html>
<head>
<title>Page Title</title>
</head>
<body>

<h1>My bla</h1>
 <img src="/Hauskatze_langhaar.jpg"
</body>
</html> 
        """.trimIndent()
    }
}