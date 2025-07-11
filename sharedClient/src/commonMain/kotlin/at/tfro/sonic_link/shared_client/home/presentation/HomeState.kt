package at.tfro.sonic_link.shared_client.home.presentation

import at.tfro.sonic_link.shared_client.core.presentation.StringValue

data class HomeState(
    val isLoading: Boolean = false,
    val error: StringValue? = null,
    val sideMenuOpen: Boolean = false,
)