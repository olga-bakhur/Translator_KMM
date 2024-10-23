package com.bakhur.translator.android.voice_to_text.presentation.components

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bakhur.translator.android.R
import com.bakhur.translator.android.core.theme.LightBlue
import com.bakhur.translator.android.core.theme.TranslatorTheme
import com.bakhur.translator.translation.NetworkConstants.DEFAULT_FROM_LANGUAGE
import com.bakhur.translator.voice_to_text.presentation.DisplayState
import com.bakhur.translator.voice_to_text.presentation.VoiceToTextEvent
import com.bakhur.translator.voice_to_text.presentation.VoiceToTextState

@Composable
fun VoiceToTextScreen(
    state: VoiceToTextState,
    languageCode: String,
    onResult: (String) -> Unit,
    onEvent: (VoiceToTextEvent) -> Unit
) {
    val context = LocalContext.current
    val recordAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            val isPermanentlyDeclined = !isGranted && !(context as ComponentActivity)
                .shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)

            onEvent(
                VoiceToTextEvent.PermissionResult(
                    isGranted = isGranted,
                    isPermanentlyDeclined = isPermanentlyDeclined
                )
            )
        }
    )

    LaunchedEffect(recordAudioLauncher) {
        recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    Scaffold(
        floatingActionButton = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(
                    onClick = {
                        if (state.displayState == DisplayState.DISPLAYING_RESULTS) {
                            onResult(state.spokenText)
                        } else {
                            onEvent(VoiceToTextEvent.ToggleRecording(languageCode))
                        }
                    },
                    shape = RoundedCornerShape(100f),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(75.dp)
                ) {
                    AnimatedContent(
                        targetState = state.displayState,
                        label = stringResource(R.string.accept_recording_result)
                    ) { displayState ->
                        when (displayState) {
                            DisplayState.SPEAKING -> {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = stringResource(R.string.stop_recording),
                                    modifier = Modifier.size(50.dp)
                                )
                            }

                            DisplayState.DISPLAYING_RESULTS -> {
                                Icon(
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = stringResource(R.string.apply),
                                    modifier = Modifier.size(50.dp)
                                )
                            }

                            else -> {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.mic),
                                    contentDescription = stringResource(R.string.record_audio),
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        }
                    }
                }

                if (state.displayState == DisplayState.DISPLAYING_RESULTS) {
                    IconButton(
                        onClick = {
                            onEvent(VoiceToTextEvent.ToggleRecording(languageCode))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = stringResource(R.string.record_again),
                            tint = LightBlue
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = {
                        onEvent(VoiceToTextEvent.Close)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = stringResource(id = R.string.close)
                    )
                }

                if (state.displayState == DisplayState.SPEAKING) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(id = R.string.listening),
                        color = LightBlue
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 100.dp)
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedContent(
                    targetState = state.displayState,
                    label = stringResource(R.string.listening_state)
                ) { displayState ->
                    when (displayState) {
                        DisplayState.WAITING_TO_TALK -> {
                            Text(
                                text = stringResource(R.string.start_talking),
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center
                            )
                        }

                        DisplayState.SPEAKING -> {
                            VoiceRecorderDisplay(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                powerRatios = state.powerRatios
                            )
                        }

                        DisplayState.DISPLAYING_RESULTS -> {
                            Text(
                                text = state.spokenText,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center
                            )
                        }

                        DisplayState.ERROR -> {
                            Text(
                                text = state.recordError
                                    ?: stringResource(id = R.string.error_unknown),
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        null -> Unit
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun VoiceToTextPreview() {
    TranslatorTheme {
        VoiceToTextScreen(
            state = VoiceToTextState(),
            languageCode = DEFAULT_FROM_LANGUAGE,
            onResult = {},
            onEvent = {}
        )
    }
}