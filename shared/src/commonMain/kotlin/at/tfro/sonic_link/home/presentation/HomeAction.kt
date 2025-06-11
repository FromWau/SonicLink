package at.tfro.sonic_link.home.presentation

sealed interface HomeAction {
    data object OnBack : HomeAction
    data object OnImportMusic : HomeAction
    data object OnMusicList : HomeAction
}