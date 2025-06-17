package at.tfro.sonic_link.core.presentation.side_drawer

import at.tfro.sonic_link.app.Route
import at.tfro.sonic_link.core.presentation.StringValue

enum class DrawerDestination(val label: StringValue, val route: Route) {
    Library(
        route = Route.Library,
        label = StringValue.HardCoded("Library")
    ),
    Importer(
        route = Route.Import.ImportList,
        label = StringValue.HardCoded("Importer")
    ),
    Settings(
        route = Route.Settings,
        label = StringValue.HardCoded("Settings")
    ),
}