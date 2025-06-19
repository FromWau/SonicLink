package at.tfro.sonic_link.importer.presentation.import_media

import androidx.compose.runtime.Composable
import at.tfro.sonic_link.importer.presentation.importer_list.ImportListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ImportMediaScreenRoot(
    viewModel: ImportListViewModel = koinViewModel<ImportListViewModel>(),
    onBack: () -> Unit,
    onNav: (ImportMediaAction) -> Unit,
) {
    ImportMediaScreen(
        onAction = { action ->
            when (action) {
                is ImportMediaAction.OnBack -> onBack()
            }

            onNav(action)
        },
    )
}

@Composable
fun ImportMediaScreen(
    onAction: (ImportMediaAction) -> Unit,
) {
}