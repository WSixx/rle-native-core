package br.com.lucad.rlenativecore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.lucad.rlenativecore.ui.theme.RleNativeCoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RleNativeCoreTheme {
                MainApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun MainApp(
    modifier: Modifier = Modifier,
    viewModel: RleViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Content(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            inputText = uiState.inputText,
            outputText = uiState.outputText,
            onInputTextChange = viewModel::onInputTextChanged,
            onCompressClick = viewModel::compress,
            onDecompressClick = viewModel::decompress
        )
    }
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    inputText: String = "",
    outputText: String = "",
    onInputTextChange: (String) -> Unit = {},
    onCompressClick: () -> Unit = {},
    onDecompressClick: () -> Unit = {},
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "rle-native-core",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = onInputTextChange,
                        label = { Text(text = "Input") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        ElevatedButton(onClick = onCompressClick) {
                            Text(text = "Compress")
                        }
                        ElevatedButton(onClick = onDecompressClick) {
                            Text(text = "Decompress")
                        }
                    }
                    if (outputText.isNotEmpty()) {
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Result:",
                                    style = MaterialTheme.typography.labelLarge
                                )
                                Text(
                                    text = outputText,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentPreview() {
    RleNativeCoreTheme {
        Content(
            inputText = "AAAAABBBCC",
            outputText = "5A3B2C"
        )
    }
}
