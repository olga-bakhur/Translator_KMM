package com.bakhur.translator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bakhur.translator.android.core.presentation.NavArguments.LANGUAGE_CODE
import com.bakhur.translator.android.core.presentation.NavArguments.VOICE_RESULT
import com.bakhur.translator.android.core.presentation.Routes
import com.bakhur.translator.android.core.presentation.Routes.TRANSLATE
import com.bakhur.translator.android.core.theme.TranslatorTheme
import com.bakhur.translator.android.translation.presentation.AndroidTranslateViewModel
import com.bakhur.translator.android.translation.presentation.TranslateScreen
import com.bakhur.translator.android.voice_to_text.presentation.AndroidVoiceToTextViewModel
import com.bakhur.translator.android.voice_to_text.presentation.components.VoiceToTextScreen
import com.bakhur.translator.translation.NetworkConstants.DEFAULT_FROM_LANGUAGE
import com.bakhur.translator.translation.presentation.TranslateEvent
import com.bakhur.translator.voice_to_text.presentation.VoiceToTextEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TranslatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TranslateRoot()
                }
            }
        }
    }
}

@Composable
fun TranslateRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = TRANSLATE
    ) {
        composable(route = TRANSLATE) { backStackEntry ->
            val viewModel: AndroidTranslateViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val voiceResult by backStackEntry
                .savedStateHandle
                .getStateFlow<String?>(VOICE_RESULT, null)
                .collectAsStateWithLifecycle()

            LaunchedEffect(key1 = voiceResult) {
                viewModel.onEvent(TranslateEvent.SubmitVoiceResult(voiceResult))
                backStackEntry.savedStateHandle[VOICE_RESULT] = null
            }

            TranslateScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        is TranslateEvent.RecordAudio -> {
                            navController.navigate(Routes.VOICE_TO_TEXT + "/${state.fromLanguage.language.langCode}")
                        }

                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }

        composable(
            route = Routes.VOICE_TO_TEXT + "/{$LANGUAGE_CODE}",
            arguments = listOf(
                navArgument(LANGUAGE_CODE) {
                    type = NavType.StringType
                    defaultValue = DEFAULT_FROM_LANGUAGE
                }
            )
        ) { backStackEntry ->
            val languageCode = backStackEntry.arguments?.getString(LANGUAGE_CODE)
                ?: DEFAULT_FROM_LANGUAGE
            val viewModel: AndroidVoiceToTextViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            VoiceToTextScreen(
                state = state,
                languageCode = languageCode,
                onResult = { spokenText ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        VOICE_RESULT, spokenText
                    )
                    navController.popBackStack()
                },
                onEvent = { event ->
                    when (event) {
                        VoiceToTextEvent.Close -> navController.popBackStack()
                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    TranslatorTheme {
        TranslateRoot()
    }
}
