package ru.gross.notes.ui.detail

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.gross.notes.R
import ru.gross.notes.ui.AppDialog
import ru.gross.notes.ui.Margin24
import ru.gross.notes.ui.ProgressIndicator
import ru.gross.notes.ui.ScreenPadding
import ru.gross.notes.ui.theme.ApplicationTheme
import ru.gross.notes.utils.collectState
import ru.gross.notes.utils.onEffect

@Suppress("UnnecessaryVariable") // for smart cast state variable
@Composable
internal fun NoteDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteDetailsViewModel = hiltViewModel(),
    onNavigateUp: () -> Boolean
) = Column(
    modifier = modifier
        .fillMaxSize()
        .padding(ScreenPadding)
) {
    BackHandler {
        viewModel.submitEvent(Event.SaveChanges(false))
    }

    var openDeleteDialog by remember { mutableStateOf(false) }
    var openSaveDialog by remember { mutableStateOf(false) }
    viewModel.onEffect {
        when (it) {
            Effect.DisplayDeleteDialog -> {
                openDeleteDialog = true
            }
            Effect.DisplaySaveDialog -> {
                openSaveDialog = true
            }
            Effect.GoBack -> {
                onNavigateUp()
            }
        }
    }
    val state by viewModel.collectState()
    when (val immutableState = state) {
        State.LoadDetail -> ProgressIndicator()
        is State.DisplayDetail -> {
            NoteDetailLoadedScreen(
                title = immutableState.detail.title,
                onTitleChange = {
                    viewModel.submitEvent(Event.NewText.Title(it))
                },
                content = immutableState.detail.content,
                onContentChange = {
                    viewModel.submitEvent(Event.NewText.Content(it))
                }
            )
        }
    }
    if (openDeleteDialog) {
        AppDialog(
            title = stringResource(id = R.string.delete_note_confirmation_title),
            positiveButtonText = stringResource(id = R.string.default_confirm_btn_text).uppercase(),
            positiveButtonClick = { viewModel.submitEvent(Event.DeleteNote) },
            negativeButtonText = stringResource(id = R.string.default_negative_btn_text).uppercase(),
            onDismissRequest = { openDeleteDialog = false }
        )
    }
    if (openSaveDialog) {
        AppDialog(
            title = stringResource(id = R.string.add_note_confirmation_title),
            positiveButtonText = stringResource(id = R.string.default_confirm_btn_text).uppercase(),
            positiveButtonClick = { viewModel.submitEvent(Event.SaveChanges(true)) },
            negativeButtonText = stringResource(id = R.string.default_negative_btn_text).uppercase(),
            negativeButtonClick = { viewModel.submitEvent(Event.GoBack) },
            onDismissRequest = { openSaveDialog = false }
        )
    }
}

@Composable
private fun NoteDetailLoadedScreen(
    title: String? = null,
    content: String? = null,
    onTitleChange: (String) -> Unit = {},
    onContentChange: (String) -> Unit = {}
) = Column(modifier = Modifier.fillMaxSize()) {
    NoteTextField(
        value = title.orEmpty(),
        placeholderText = stringResource(id = R.string.note_title_text),
        onValueChange = onTitleChange,
        textStyle = MaterialTheme.typography.body1.copy(
            color = MaterialTheme.colors.onPrimary
        )
    )
    NoteTextField(
        value = content.orEmpty(),
        placeholderText = stringResource(id = R.string.note_content_text),
        onValueChange = onContentChange,
        textStyle = MaterialTheme.typography.body2.copy(
            color = MaterialTheme.colors.onPrimary
        ),
        modifier = Modifier.padding(top = Margin24)
    )
}

@Composable
private fun NoteTextField(
    value: String,
    placeholderText: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
) {

    @Composable
    fun Placeholder(text: String) {
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.onSurface.copy(alpha = .4f)
        )
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        modifier = modifier,
        cursorBrush = SolidColor(MaterialTheme.colors.secondary),
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Placeholder(text = placeholderText)
            }
            innerTextField()
        }
    )
}

@Composable
private fun PreviewNoteDetailLoadedScreenBase() = ApplicationTheme() {
    NoteDetailLoadedScreen("Test")
}

@Preview(
    device = Devices.PIXEL_4,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF000000
)
@Composable
private fun PreviewMainScreenNight() = PreviewNoteDetailLoadedScreenBase()

@Preview(
    device = Devices.PIXEL_4,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFF5F5F5
)
@Composable
private fun PreviewMainScreenDay() = PreviewNoteDetailLoadedScreenBase()