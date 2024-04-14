package im.etap.forecast.external.api.config

import im.etap.forecast.external.api.service.ForecastApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.support.RestTemplateAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import org.springframework.web.service.invoker.createClient
import org.springframework.web.util.DefaultUriBuilderFactory

@Configuration
class ForecastApiConfig(
    @Value("\${api-data-go-kr.url}")
    private val url: String
) {
    @Bean
    fun forecastApi(): ForecastApi {
        // TODO: restTemplate 재시도 전략
        return HttpServiceProxyFactory.builderFor(
            RestTemplateAdapter.create(RestTemplate().apply {
                messageConverters.add(MappingJackson2HttpMessageConverter())
                uriTemplateHandler = DefaultUriBuilderFactory(url)
            })
        )
            .build()
            .createClient<ForecastApi>()
    }
}