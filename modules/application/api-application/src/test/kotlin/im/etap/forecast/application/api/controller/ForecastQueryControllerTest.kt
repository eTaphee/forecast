package im.etap.forecast.application.api.controller

import com.ninjasquad.springmockk.MockkBean
import im.etap.forecast.core.dto.ForecastInfoResponse
import im.etap.forecast.domain.service.ForecastQueryService
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDate
import java.time.LocalTime

@WebMvcTest(ForecastQueryController::class)
class ForecastQueryControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var forecastQueryService: ForecastQueryService

    @Test
    @DisplayName("날씨 정보가 없으면 204 반환")
    fun getVillageForecast_return_204_when_forecastInfos_Empty() {
        // given
        every {
            forecastQueryService.getVillageForecast(any<Double>(), any<Double>())
        } returns emptyList()

        // when & then
        mockMvc.get("/forecast/village") {
            param("lat", "37.7437027777777")
            param("lng", "127.049844444444")
        }.andExpect {
            status { isNoContent() }
        }.andDo { print() }
    }

    @Test
    @DisplayName("날씨 정보가 있으면 200 반환")
    fun getVillageForecast_return_200_when_forecastInfos_NotEmpty() {
        // given
        val forecastDate = LocalDate.parse("2024-04-18")
        val forecastTime = LocalTime.parse("13:00:00")
        val baseDate = LocalDate.parse("2024-04-17")
        val baseTime = LocalTime.parse("12:00:00")

        every {
            forecastQueryService.getVillageForecast(any<Double>(), any<Double>())
        } returns listOf(
            ForecastInfoResponse(
                "CAT",
                forecastDate,
                forecastTime,
                "VALUE",
                baseDate,
                baseTime
            )
        )

        // when & then
        mockMvc.get("/forecast/village") {
            param("lat", "37.7437027777777")
            param("lng", "127.049844444444")
        }.andExpect {
            status { isOk() }
            jsonPath("$[0].category") { value("CAT") }
            jsonPath("$[0].forecastDate") { value("2024-04-18") }
            jsonPath("$[0].forecastTime") { value("13:00:00") }
            jsonPath("$[0].forecastValue") { value("VALUE") }
            jsonPath("$[0].baseDate") { value("2024-04-17") }
            jsonPath("$[0].baseTime") { value("12:00:00") }
        }.andDo { print() }
    }
}