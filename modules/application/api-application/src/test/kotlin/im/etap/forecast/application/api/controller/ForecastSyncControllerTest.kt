package im.etap.forecast.application.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import im.etap.forecast.application.api.service.ForecastSyncApi
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(ForecastSyncController::class)
class ForecastSyncControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var forecastSyncApi: ForecastSyncApi

    @Autowired
    private lateinit var mapper: ObjectMapper

    private val forecastSyncRequest =
        ForecastSyncRequest(37.7437027777777, 127.049844444444)

    @Test
    @DisplayName("동기화 요청이 끝나지 않은 경우 202 반환")
    fun syncVillageForecast_return_204_when_api_return_204() {
        // given
        every {
            forecastSyncApi.requestVillageForecast(any<ForecastSyncRequest>())
        } returns ResponseEntity.accepted().build()

        // when & then
        mockMvc.post("/sync/village") {
            contentType = APPLICATION_JSON
            headers { this.accept = listOf(APPLICATION_JSON) }
            content = mapper.writeValueAsString(forecastSyncRequest)
        }.andExpect {
            status { isAccepted() }
        }.andDo { print() }
    }

    @Test
    @DisplayName("동기화 요청이 끝난 경우 200 반환")
    fun syncVillageForecast_return_200_when_api_return_200() {
        // given
        every {
            forecastSyncApi.requestVillageForecast(any<ForecastSyncRequest>())
        } returns ResponseEntity.ok().build()

        // when & then
        mockMvc.post("/sync/village") {
            contentType = APPLICATION_JSON
            headers { this.accept = listOf(APPLICATION_JSON) }
            content = mapper.writeValueAsString(forecastSyncRequest)
        }.andExpect {
            status { isOk() }
        }.andDo { print() }
    }
}