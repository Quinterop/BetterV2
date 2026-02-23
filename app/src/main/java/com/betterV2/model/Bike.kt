import org.json.JSONObject
import java.time.Instant
import java.time.format.DateTimeParseException

data class Bike(
    val batteryLevel: Int,
    val electric: Boolean,
    val name: String,
    val rate: Int,
    val available: Boolean,
    val dockPosition: Int,
    val numberOfRates: Int,
    val stationId: Int,
    val lastRateDate: Instant,
    val blockCause: String,
) {
    companion object {
        fun fromJson(json: JSONObject) = Bike(
            batteryLevel = parseBatteryLevel(json.optString("bikeBatteryLevel")),
            electric      = json.optString("bikeElectric") == "yes",
            name          = json.optString("bikeName"),
            rate          = parseInt(json.opt("bikeRate")),
            available     = json.optString("bikeStatus") == "disponible",
            dockPosition  = parseInt(json.opt("dockPosition")),
            numberOfRates = parseInt(json.opt("numberOfRates")),
            stationId     = parseInt(json.opt("stationId")),
            lastRateDate  = parseDate(json.optString("lastRateDate")),
            blockCause    = json.optString("bikeBlockCause"),
        )

        private fun parseBatteryLevel(value: String?): Int =
            value?.split("_")?.firstOrNull()?.toIntOrNull() ?: 0

        private fun parseInt(value: Any?): Int = when (value) {
            is Int -> value
            else   -> value?.toString()?.toIntOrNull() ?: 0
        }

        private fun parseDate(value: String?): Instant = try {
            if (value.isNullOrEmpty()) Instant.EPOCH
            else Instant.parse(value)
        } catch (e: DateTimeParseException) {
            Instant.EPOCH
        }
    }
}
