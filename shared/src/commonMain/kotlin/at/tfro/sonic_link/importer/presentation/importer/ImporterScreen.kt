package at.tfro.sonic_link.importer.presentation.importer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ImporterRoot(
    viewmodel: ImporterViewmodel = koinViewModel<ImporterViewmodel>(),
    onBack: () -> Unit,
) {
    val state by viewmodel.state.collectAsStateWithLifecycle()

    ImporterScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ImporterAction.OnBack -> onBack()
                else -> Unit
            }

            viewmodel.onAction(action)
        },
    )
}

@Composable
fun ImporterScreen(
    state: ImporterState,
    onAction: (ImporterAction) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text("Importer Screen", modifier = Modifier.fillMaxWidth())
        }
    }
}