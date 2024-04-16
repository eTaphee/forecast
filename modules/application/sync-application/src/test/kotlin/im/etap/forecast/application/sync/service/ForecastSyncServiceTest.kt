package im.etap.forecast.application.sync.service

import im.etap.forecast.application.sync.dto.ForecastSyncRequest
import im.etap.forecast.application.sync.dto.getPoint
import im.etap.forecast.application.sync.event.ForecastSyncEvent
import im.etap.forecast.domain.entity.ForecastSync
import im.etap.forecast.domain.repository.ForecastSyncRepository
import io.mockk.called
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.ApplicationEventPublisher
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockKExtension::class)
internal class ForecastSyncServiceTest {

    @MockK
    private lateinit var forecastSyncRepository: ForecastSyncRepository

    @MockK(relaxUnitFun = true)
    private lateinit var eventPublisher: ApplicationEventPublisher

    @InjectMockKs
    private lateinit var forecastSyncService: ForecastSyncService

    private val forecastSyncRequest =
        ForecastSyncRequest(37.7437027777777, 127.049844444444)

    @Test
    @DisplayName("동기화 요청이 없는 경우 저장&발행 호출 검증")
    fun saveVillageForecastSyncRequest_verify_save_publish_when_request_not_exists() {
        // given
        every {
            forecastSyncRepository.findByBaseDateTimeAndLocation(any(), any())
        } returns Optional.empty()

        every {
            forecastSyncRepository.save(any<ForecastSync>())
        } returns ForecastSync(LocalDateTime.now(), forecastSyncRequest.getPoint())

        // when
        forecastSyncService.saveVillageForecastSyncRequest(forecastSyncRequest)

        // then
        verify { forecastSyncRepository.save(any<ForecastSync>()) }
        verify { eventPublisher.publishEvent(any<ForecastSyncEvent>()) }
    }

    @Test
    @DisplayName("동기화 요청이 있는 경우 저장&발행 wasNotCalled 검증 ")
    fun saveVillageForecastSyncRequest_verify_wasNot_called_publish_event_when_request_exists() {
        // given
        every {
            forecastSyncRepository.findByBaseDateTimeAndLocation(any(), any())
        } returns Optional.of(
            ForecastSync(LocalDateTime.now(), forecastSyncRequest.getPoint())
        )

        // when
        forecastSyncService.saveVillageForecastSyncRequest(forecastSyncRequest)

        // then
        verify { forecastSyncRepository.save(any<ForecastSync>()) wasNot called }
        // 아래 코드가 동작이 안됨.. 왜일까?
//        verify { eventPublisher.publishEvent(any<ForecastSyncEvent>()) wasNot called }
        verify(exactly = 0) { eventPublisher.publishEvent(any<ForecastSyncEvent>()) }
    }
}