package at.tfro.sonic_link.library.presentation

import at.tfro.sonic_link.core.presentation.StringValue
import at.tfro.sonic_link.library.domain.Music

data class MusicListState(
    val isLoading: Boolean = false,
    val errorMessage: StringValue? = null,
    val musicList: List<Music> = listOf(
        Music(
            id = "1",
            title = "Smells Like Teen Spirit",
            artists = listOf("Nirvana"),
            album = "Nevermind",
            albumArtUrl = "https://lastfm.freetls.fastly.net/i/u/770x0/49cc807f69d59746b6b04be3434e6637.jpg#49cc807f69d59746b6b04be3434e6637",
        ),
        Music(
            id = "2",
            title = "Mr. Brightside",
            artists = listOf("The Killers"),
            album = "Hot Fuss",
            albumArtUrl = "https://lastfm.freetls.fastly.net/i/u/770x0/fae9c99bbb6ae827b508a97328551912.jpg#fae9c99bbb6ae827b508a97328551912",
        ),
        Music(
            id = "3",
            title = "Feel Good Inc.",
            artists = listOf("Gorillaz"),
            album = "Demon Days",
            albumArtUrl = "https://lastfm.freetls.fastly.net/i/u/770x0/271483e955d2b255160f3361a7f5fb78.jpg#271483e955d2b255160f3361a7f5fb78",
        ),
        Music(
            id = "4",
            title = "Chop Suey!",
            artists = listOf("System of a Down"),
            album = "Toxicity",
            albumArtUrl = "https://lastfm.freetls.fastly.net/i/u/770x0/8e1d82d468a2e1aa8727038e083a898e.jpg#8e1d82d468a2e1aa8727038e083a898e",
        ),
        Music(
            id = "5",
            title = "In the End",
            artists = listOf("Linkin Park"),
            album = "Hybrid Theory",
            albumArtUrl = "https://lastfm.freetls.fastly.net/i/u/770x0/53a7bd85bc99272900f620792bf43452.jpg#53a7bd85bc99272900f620792bf43452",
        )
    ),
    val selectedMusicItem: Music? = null,
)