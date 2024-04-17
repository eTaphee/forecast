//package im.etap.forecast.application.api.service
//
//import im.etap.forecast.application.api.dto.ForecastInfoResponse
//import im.etap.forecast.domain.entity.ForecastInfo
//import im.etap.forecast.domain.entity.ForecastSync
//import im.etap.forecast.domain.repository.ForecastSyncRepository
//import io.kotest.matchers.equals.shouldBeEqual
//import io.kotest.matchers.shouldBe
//import io.mockk.every
//import io.mockk.impl.annotations.InjectMockKs
//import io.mockk.impl.annotations.MockK
//import io.mockk.junit5.MockKExtension
//import io.mockk.mockk
//import org.junit.jupiter.api.DisplayName
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import java.time.LocalDate
//import java.time.LocalDateTime
//import java.time.LocalTime
//import java.util.*
//
//@ExtendWith(MockKExtension::class)
//class ForecastQueryServiceTest {
//    @MockK
//    private lateinit var forecastSyncRepository: ForecastSyncRepository
//
//    @InjectMockKs
//    private lateinit var forecastQueryService: ForecastQueryService
//
//    @Test
//    @DisplayName("동기화 요청이 없는 경우 빈 리스트 반환")
//    fun getVillageForecast_return_emptyList_when_forecastSync_not_exists() {
//        // given
//        val lat = 37.7437027777777
//        val lng = 127.049844444444
//
//        every {
//            forecastSyncRepository.findByBaseDateTimeAndLocation(any(), any())
//        } returns Optional.empty()
//
//        // when
//        val result = forecastQueryService.getVillageForecast(lat, lng)
//
//        // then
//        result shouldBe emptyList()
//    }
//
//    @Test
//    @DisplayName("동기화 요청이 있는 경우 리스트 반환")
//    fun getVillageForecast_return_list_when_forecastSync_exists() {
//        // given
//        val lat = 37.7437027777777
//        val lng = 127.049844444444
//
//        val baseDateTime = LocalDateTime.parse("2024-04-17T12:00:00")
//        val forecastDate = LocalDate.parse("2024-04-18")
//        val forecastTime = LocalTime.parse("13:00:00")
//
//        val forecastSync = mockk<ForecastSync>()
//        val forecastInfo =
//            ForecastInfo("CAT", forecastDate, forecastTime, "VALUE", forecastSync)
//
//        every { forecastSync.baseDateTime } returns baseDateTime
//        every { forecastSync.forecastInfos } returns listOf(forecastInfo)
//
//        every {
//            forecastSyncRepository.findByBaseDateTimeAndLocation(any(), any())
//        } returns Optional.of(forecastSync)
//
//        // when
//        val result = forecastQueryService.getVillageForecast(lat, lng)
//
//        // then
//        result.size shouldBe 1
//        result shouldBeEqual forecastSync.forecastInfos.map { ForecastInfoResponse.from(it) }
//    }
//}