package im.etap.forecast.external.api.service

import im.etap.forecast.external.api.dto.VillageForecastResponse
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange

/**
 * 공공 데이터 포털 단기예보 API
 */
interface ForecastApi {
    @GetExchange("/1360000/VilageFcstInfoService_2.0/getVilageFcst?dataType=JSON")
    @Throws(RuntimeException::class)
    fun getVillageForecast(
        @RequestParam("serviceKey") serviceKey: String,
        @RequestParam("pageNo") pageNo: Int,
        @RequestParam("numOfRows") numOfRows: Int,
        @RequestParam("base_date") baseDate: String,
        @RequestParam("base_time") baseTime: String,
        @RequestParam("nx") x: Int,
        @RequestParam("ny") y: Int
    ): VillageForecastResponse
}