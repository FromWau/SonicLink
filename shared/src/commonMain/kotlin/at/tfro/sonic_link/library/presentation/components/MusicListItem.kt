package at.tfro.sonic_link.library.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.tfro.sonic_link.core.presentation.PulseAnimation
import at.tfro.sonic_link.library.domain.Music
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.painterResource
import soniclink.shared.generated.resources.Res
import soniclink.shared.generated.resources.compose_multiplatform


@Composable
fun MusicListItem(
    music: Music,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(32.dp),
        modifier = modifier.clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier.width(100.dp),
                contentAlignment = Alignment.Center,
            ) {
                var imageLoadResult by remember {
                    mutableStateOf<Result<Painter>?>(null)
                }
                val painter = rememberAsyncImagePainter(
                    model = music.albumArtUrl!!,
                    onSuccess = {
                        imageLoadResult =
                            if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                                Result.success(it.painter)
                            } else {
                                Result.failure(Exception("Invalid image size"))
                            }
                    },
                    onError = {
                        it.result.throwable.printStackTrace()
                        imageLoadResult = Result.failure(it.result.throwable)
                    }
                )
                val painterState by painter.state.collectAsStateWithLifecycle()
                val transition by animateFloatAsState(
                    targetValue = if (painterState is AsyncImagePainter.State.Success) {
                        1f
                    } else {
                        0f
                    },
                    animationSpec = tween(durationMillis = 800)
                )

                when (val result = imageLoadResult) {
                    null -> PulseAnimation(
                        modifier = Modifier.size(60.dp)
                    )

                    else -> {
                        Image(
                            painter = if (result.isSuccess) painter else {
                                painterResource(Res.drawable.compose_multiplatform)
                            },
                            contentDescription = music.title,
                            contentScale = if (result.isSuccess) {
                                ContentScale.Crop
                            } else {
                                ContentScale.Fit
                            },
                            modifier = Modifier
                                .aspectRatio(
                                    ratio = 0.65f,
                                    matchHeightConstraintsFirst = true
                                )
                                .graphicsLayer {
                                    rotationX = (1f - transition) * 30f
                                    val scale = 0.8f + (0.2f * transition)
                                    scaleX = scale
                                    scaleY = scale
                                }
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = music.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                music.artists.firstOrNull()?.let { artist ->
                    Text(
                        text = artist,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(36.dp),
            )
        }
    }
}
