package ru.gross.notes.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

private val AppBarIconSize = 48.dp
internal val ScreenPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)

internal val Margin8 = 8.dp
internal val Margin12 = 12.dp
internal val Margin24 = 24.dp


@Composable
internal fun AppDialog(
    title: String,
    positiveButtonText: String,
    positiveButtonClick: () -> Unit,
    onDismissRequest: () -> Unit,
    negativeButtonText: String,
    negativeButtonClick: () -> Unit = {},
) = AlertDialog(
    backgroundColor = MaterialTheme.colors.primaryVariant,
    onDismissRequest = onDismissRequest,
    title = { Text(text = title) },
    confirmButton = {
        TextButton(
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.secondary),
            onClick = {
                onDismissRequest()
                positiveButtonClick()
            }
        ) {
            Text(text = positiveButtonText)
        }
    },
    dismissButton = {
        TextButton(
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.secondary),
            onClick = {
                onDismissRequest()
                negativeButtonClick()
            }
        ) {
            Text(text = negativeButtonText)
        }
    }
)

@Composable
internal fun ProgressIndicator() = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    CircularProgressIndicator(color = MaterialTheme.colors.secondary)
}

@Composable
internal fun AppIconButton(
    vectorIcon: ImageVector,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.high,
        ) {
            Icon(
                imageVector = vectorIcon,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Composable
internal fun CentredTitleTopAppBar(
    title: @Composable () -> Unit,
    navigationIcon: @Composable (RowScope.() -> Unit)? = null
) = CompositionLocalProvider(LocalElevationOverlay provides null) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary
    ) {
        if (navigationIcon == null) {
            Spacer(modifier = Modifier.width(AppBarIconSize))
        } else {
            Row(verticalAlignment = Alignment.CenterVertically, content = navigationIcon)
        }

        Row(
            Modifier
                .fillMaxHeight()
                .weight(1f, true),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.h6) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                    content = title
                )
            }
        }

        Spacer(modifier = Modifier.width(AppBarIconSize))
    }
}