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

    fun onInputTextChanged(newText: String) {
        _uiState.update { it.copy(inputText = newText) }
    }

    fun compress() {
        _uiState.update {
            it.copy(
                outputText = nativeLib.compressRle(uiState.value.inputText.toByteArray())
                    .contentToString()
            )
        }
    }

    fun decompress() {
        // TODO:
    }
}
