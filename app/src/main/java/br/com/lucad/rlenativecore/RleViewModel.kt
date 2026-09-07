package br.com.lucad.rlenativecore

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class RleUiState(
    val inputText: String = "",
    val outputText: String = "",
)

class RleViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RleUiState())
    val uiState: StateFlow<RleUiState> = _uiState.asStateFlow()

    fun onInputTextChanged(newText: String) {
        _uiState.update { it.copy(inputText = newText) }
    }

    fun compress() {
        // TODO:
    }

    fun decompress() {
        // TODO:
    }
}
