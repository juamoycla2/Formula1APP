package cat.exemple.pilotosf1.data

import cat.exemple.pilotosf1.model.Piloto
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import org.json.JSONArray

// Demana dades a la API y els parseja
class F1ApiService {
    private val client = HttpClient(Android) {
        expectSuccess = true
    }

    suspend fun getPilotosJson(): String =
        client.get("https://api.openf1.org/v1/drivers?session_key=latest").bodyAsText()

    fun parsePilotos(resposta: String): List<Piloto> {
        val array = JSONArray(resposta)
        val pilotos = mutableListOf<Piloto>()

        for (index in 0 until array.length()) {
            val obj = array.getJSONObject(index)
            val piloto = Piloto(
                numero = obj.getInt("driver_number"),
                nom = obj.getString("full_name"),
                acronim = obj.optString("name_acronym"),
                equip = obj.optString("team_name"),
                foto = obj.optString("headshot_url")
                    .takeIf { it.isNotBlank() && it != "null" }
            )
            pilotos.add(piloto)
        }

        return pilotos
    }

    suspend fun getPilotos(): List<Piloto> = parsePilotos(getPilotosJson())

}