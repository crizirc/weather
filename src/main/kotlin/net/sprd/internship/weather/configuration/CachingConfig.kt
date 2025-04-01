package net.sprd.internship.weather.configuration

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class CachingConfig {
    fun cacheManager(): CacheManager {
        return ConcurrentMapCacheManager("getCurrentWeather")
    }
}