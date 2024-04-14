package im.etap.forecast.external.api.dto.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import im.etap.forecast.external.api.dto.ForecastInfo
import im.etap.forecast.external.api.dto.VillageForecastResponse

internal class VillageForecastResponseDeserializer :
    StdDeserializer<VillageForecastResponse>(VillageForecastResponse::class.java) {

    companion object {
        private val EMPTY_LIST = listOf<ForecastInfo>()
    }

    override fun deserialize(
        jp: JsonParser,
        ctxt: DeserializationContext
    ): VillageForecastResponse {
        val root: JsonNode? = jp.codec.readTree(jp)

        val response = root?.get("response")
        val header = response?.get("header")
        val resultCode = header?.get("resultCode")?.asText() ?: "99"
        val resultMessage = header?.get("resultMsg")?.asText() ?: "Unknown Error"

        val body = response?.get("body")
        val pageNo = body?.get("pageNo")?.asInt() ?: 0
        val numOfRows = body?.get("numOfRows")?.asInt() ?: 0
        val totalCount = body?.get("totalCount")?.asInt() ?: 0
        val items = body?.get("items")?.get("item")?.map {
            jp.codec.treeToValue(it, ForecastInfo::class.java)
        }?.toList() ?: EMPTY_LIST

        return VillageForecastResponse(
            resultCode,
            resultMessage,
            pageNo,
            numOfRows,
            totalCount,
            items
        )
    }
}