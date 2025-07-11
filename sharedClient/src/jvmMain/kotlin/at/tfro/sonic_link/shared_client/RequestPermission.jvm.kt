package at.tfro.sonic_link.shared_client

import androidx.compose.runtime.Composable

@Composable
actual fun RequestPermission(
    permission: Permission,
    onPermissionResult: (Boolean) -> Unit,
) {
    // No-op on desktop
    onPermissionResult(true)
}