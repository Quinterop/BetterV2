data class Station(
    val lat: Double, val lon: Double,
    val code: String, val name: String?,
    val working: Boolean,
    val nbBike: Int, val nbEbike: Int,
    val nbFreeDock: Int, val nbFreeEDock: Int,
    val nbDock: Int, val nbEDock: Int,
    val nbBikeOverflow: Int, val nbEBikeOverflow: Int,
    val maxBikeOverflow: Int, val densityLevel: Int,
    val nbBikeBlockedToCollect: Int,
    val nbBikeBlockedToFixInStation: Int,
) {
    companion object {
        fun fromJson(json: JSONObject): Station {
            val s = json.getJSONObject("station")
            val gps = s.getJSONObject("gps")
            return Station(
                lat = gps.getDouble("latitude"),
                lon = gps.getDouble("longitude"),
                code = s.optString("code"),
                name = s.optString("name"),
                working = s.optString("state") == "Operative",
                nbBike = json.optInt("nbBike"),
                nbEbike = json.optInt("nbEbike"),
                nbFreeDock = json.optInt("nbFreeDock"),
                nbFreeEDock = json.optInt("nbFreeEDock"),
                nbDock = json.optInt("nbDock"),
                nbEDock = json.optInt("nbEDock"),
                nbBikeOverflow = json.optInt("nbBikeOverflow"),
                nbEBikeOverflow = json.optInt("nbEBikeOverflow"),
                maxBikeOverflow = json.optInt("maxBikeOverflow"),
                densityLevel = json.optInt("densityLevel"),
                nbBikeBlockedToCollect = json.optInt("nbBikeBlockedToCollect"),
                nbBikeBlockedToFixInStation = json.optInt("nbBikeBlockedToFixInStation"),
            )
        }
    }
}
