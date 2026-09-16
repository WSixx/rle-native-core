package br.com.lucad.rlenativecore

import androidx.lifecycle.ViewModel
import br.com.lucad.rlecore.RleNative
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class RleUiState(
    val inputText: String = "",
    val outputText: String = "",
)

class RleViewModel(private val nativeLib: RleNative = RleNative()) : ViewModel() {

    private val _uiState = MutableStateFlow(RleUiState())
    val uiState: StateFlow<RleUiState> = _uiState.asStateFlow()

    private var compressed: ByteArray? = null


    fun onInputTextChanged(newText: String) {
        _uiState.update { it.copy(inputText = newText) }
    }

    fun compress() {
        compressed = nativeLib.compressRle(uiState.value.inputText.toByteArray())
        _uiState.update {
            it.copy(
                outputText = compressed.contentToString()
            )
        }
    }

    fun decompress() {
        val decompressRle = compressed?.let { nativeLib.decompressRle(it) }
        _uiState.update {
            it.copy(
                outputText = decompressRle?.toString(Charsets.UTF_8) ?: "ERROR"
            )
        }
    }
}
