package at.tfro.sonic_link

import androidx.compose.runtime.Composable

enum class Permission {
    NETWORK,
}

@Composable
expect fun RequestPermission(permission: Permission, onPermissionResult: (Boolean) -> Unit)