package at.tfro.sonic_link.home.presentation

import at.tfro.sonic_link.core.presentation.StringValue

data class HomeState(
    val isLoading: Boolean = false,
    val error: StringValue? = null,
    val sideMenuOpen: Boolean = false,
)