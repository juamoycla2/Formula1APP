package cat.exemple.pilotosf1.model

// Dades del piloto
data class Piloto(
    val numero: Int,
    val nom: String,
    val acronim: String = "",
    val equip: String = "",
    val foto: String? = null,
    val esFavorito: Boolean = false
)