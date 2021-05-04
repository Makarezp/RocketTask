
import com.squareup.moshi.Json

data class Rocket(
    @Json(name = "active")
    val active: Boolean,
    @Json(name = "boosters")
    val boosters: Int,
    @Json(name = "company")
    val company: String,
    @Json(name = "cost_per_launch")
    val costPerLaunch: Int,
    @Json(name = "country")
    val country: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "first_flight")
    val firstFlight: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "stages")
    val stages: Int,
    @Json(name = "success_rate_pct")
    val successRatePct: Int,
    @Json(name = "type")
    val type: String,
    @Json(name = "wikipedia")
    val wikipedia: String
)
