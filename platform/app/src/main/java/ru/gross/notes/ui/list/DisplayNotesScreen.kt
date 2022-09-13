package ru.gross.notes.ui.list

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.gross.notes.navigation.Navigator
import ru.gross.notes.ui.Margin12
import ru.gross.notes.ui.Margin8
import ru.gross.notes.ui.ProgressIndicator
import ru.gross.notes.ui.ScreenPadding
import ru.gross.notes.ui.theme.ApplicationTheme
import ru.gross.notes.utils.collectState
import ru.gross.notes.utils.onEffect
import java.util.*

private const val COLUMN_COUNT = 2
private const val TITLE_MAX_LINES = 3
private const val CONTENT_MAX_LINES = 6

@Suppress("UnnecessaryVariable") // for smart cast state variable
@Composable
internal fun DisplayNotesScreen(
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel = hiltViewModel(),
    navigator: Navigator
) {
    viewModel.onEffect {
        when (it) {
            is Effect.DisplayNote -> {
                navigator.showNoteDetail(it.note)
            }
        }
    }
    val state by viewModel.collectState()
    when (val immutableState = state) {
        State.LoadingList -> ProgressIndicator()
        is State.DisplayList -> {
            LoadedNotesScreen(
                modifier = modifier,
                items = immutableState.list,
                onClick = { viewModel.submitEvent(Event.ClickNote(it)) }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LoadedNotesScreen(
    modifier: Modifier = Modifier,
    items: List<NoteView> = emptyList(),
    onClick: (NoteView) -> Unit = {}
) {
    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        LazyVerticalGrid(
            modifier = modifier.fillMaxSize(),
            columns = GridCells.Fixed(COLUMN_COUNT),
            verticalArrangement = Arrangement.spacedBy(Margin12),
            horizontalArrangement = Arrangement.spacedBy(Margin12),
            contentPadding = ScreenPadding
        ) {
            items(
                items = items,
                key = { requireNotNull(it.id) }
            ) {
                NoteItem(
                    title = it.title,
                    content = it.content,
                    onClick = { onClick(it) }
                )
            }
        }
    }
}

@Composable
private fun NoteItem(
    title: String?,
    content: String?,
    onClick: () -> Unit
) = Card(
    modifier = Modifier
        .fillMaxSize()
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = LocalIndication.current,
            onClick = onClick
        )
) {
    Column(modifier = Modifier.padding(Margin8)) {
        title?.let {
            Text(
                text = it,
                maxLines = TITLE_MAX_LINES,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body1
            )
        }
        content?.let {
            Text(
                text = it,
                maxLines = CONTENT_MAX_LINES,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body2,
                modifier = if (title != null) Modifier.padding(top = Margin8) else Modifier
            )
        }
    }
}

@Composable
private fun PreviewLoadedNotesScreenBase() = ApplicationTheme() {
    LoadedNotesScreen(
        items = listOf(
            NoteView(
                id = UUID.randomUUID().toString(),
                title = "Сделать выводы по работе",
                content = "Собрать фитбэк по проекту и сделать выводы о качестве и надежности написанного кода, соответствия корпоративному стилю, а также дизайну в целом"
            )
        )
    )
}

@Preview(
    device = Devices.PIXEL_4,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF000000
)
@Composable
private fun PreviewMainScreenNight() = PreviewLoadedNotesScreenBase()

@Preview(
    device = Devices.PIXEL_4,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun PreviewMainScreenDay() = PreviewLoadedNotesScreenBase()
