package im.etap.forecast.client.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import im.etap.forecast.client.ForecastSyncClient
import im.etap.forecast.exception.ErrorCode.FORECAST_SYNC_API_ERROR
import im.etap.forecast.exception.ErrorResponse
import im.etap.forecast.exception.ForecastException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.support.RestTemplateAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import org.springframework.web.service.invoker.createClient
import org.springframework.web.util.DefaultUriBuilderFactory

/**
 * 기상예보 동기화 API 설정
 */
@ComponentScan("org.springframework.boot.autoconfigure.jackson")
@Configuration
class ForecastSyncClientConfig(
    @Value("\${forecast-sync-api.url}")
    private val url: String
) {
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Bean
    fun forecastSyncClient(): ForecastSyncClient {
        // TODO: restTemplate timeout, retry, backoff
        return HttpServiceProxyFactory.builderFor(
            RestTemplateAdapter.create(RestTemplate().apply {
                interceptors.add(ForecastSyncClientInterceptor())
                messageConverters.add(MappingJackson2HttpMessageConverter())
                uriTemplateHandler = DefaultUriBuilderFactory(url)
                errorHandler = ForecastSyncApiErrorHandler(objectMapper)
            })
        )
            .build()
            .createClient<ForecastSyncClient>()
    }
}

/**
 * 기상예보 동기화 API 인터셉터
 *
 * API 서버의 문제가 아닌, 통신 중의 예외를 처리
 */
class ForecastSyncClientInterceptor :
    ClientHttpRequestInterceptor {
    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        try {
            return execution.execute(request, body)
        } catch (e: Exception) {
            throw ForecastException(FORECAST_SYNC_API_ERROR, e.message)
        }
    }
}

/**
 * 기상예보 동기화 API 에러 핸들러
 *
 * 4xx, 5xx 에러 발생 시 body 를 error response 로 변환 후 exception 발생
 *
 * @param objectMapper 스프링 빈 objectMapper(jackson-module-kotlin)
 */
class ForecastSyncApiErrorHandler(private val objectMapper: ObjectMapper) :
    ResponseErrorHandler {

    override fun hasError(response: ClientHttpResponse): Boolean {
        return response.statusCode.is4xxClientError || response.statusCode.is5xxServerError
    }

    override fun handleError(response: ClientHttpResponse) {
        objectMapper.readValue<ErrorResponse>(response.body)
            .let { throw it.toException() }
    }
}
