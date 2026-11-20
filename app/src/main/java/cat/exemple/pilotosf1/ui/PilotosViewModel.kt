package cat.exemple.pilotosf1.ui

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cat.exemple.pilotosf1.data.F1ApiService
import cat.exemple.pilotosf1.model.Piloto
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Guarda l'estat i la llògica
private val Context.configuracioDataStore by preferencesDataStore(name = "configuracio")
private val CLAU_TEMA = stringPreferencesKey("tema")

class PilotosViewModel(application: Application) : AndroidViewModel(application) {

    var pilotos by mutableStateOf(emptyList<Piloto>())
        private set

    var carregant by mutableStateOf(true)
        private set

    var missatgeError by mutableStateOf<String?>(null)
        private set

    var numeroDetall by mutableStateOf<Int?>(null)
        private set

    var nomesFavorits by mutableStateOf(false)
        private set

    var tema by mutableStateOf("SISTEMA")
        private set

    var configuracioOberta by mutableStateOf(false)
        private set


    private var iniciat = false
    private val api = F1ApiService()

    fun iniciar() {
        if (iniciat) return
        iniciat = true
        carregarPilotos()
        carregarTema()
    }

    private fun carregarTema() {
        viewModelScope.launch {
            val preferencies = getApplication<Application>().configuracioDataStore.data.first()
            tema = preferencies[CLAU_TEMA] ?: "SISTEMA"
        }
    }

    fun carregarPilotos() {
        viewModelScope.launch {
            carregant = true
            missatgeError = null
            try {
                pilotos = api.getPilotos()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("PilotosF1", "No s'han pogut carregar els pilotos", e)
                missatgeError = "No s'han pogut carregar els pilotos."
            } finally {
                carregant = false
            }
        }
    }

    fun obrirDetall(numero: Int) {
        numeroDetall = numero
    }

    fun tancarDetall() {
        numeroDetall = null
    }

    fun canviarFiltre() {
        nomesFavorits = !nomesFavorits
    }

    fun canviarFavorit(numero: Int) {
        pilotos = pilotos.map { piloto ->
            if (piloto.numero == numero) {
                piloto.copy(esFavorito = !piloto.esFavorito)
            } else {
                piloto
            }
        }
    }

    fun canviarTema(nouTema: String) {
        tema = nouTema
        viewModelScope.launch {
            getApplication<Application>().configuracioDataStore.edit { preferencies ->
                preferencies[CLAU_TEMA] = nouTema
            }
        }
    }

    fun obrirConfiguracio() {
        configuracioOberta = true
    }

    fun tancarConfiguracio() {
        configuracioOberta = false
    }
}