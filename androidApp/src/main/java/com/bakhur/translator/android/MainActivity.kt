package com.bakhur.translator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bakhur.translator.android.core.presentation.NavArguments.LANGUAGE_CODE
import com.bakhur.translator.android.core.presentation.Routes
import com.bakhur.translator.android.core.presentation.Routes.TRANSLATE
import com.bakhur.translator.android.core.theme.TranslatorTheme
import com.bakhur.translator.android.translation.presentation.AndroidTranslateViewModel
import com.bakhur.translator.android.translation.presentation.TranslateScreen
import com.bakhur.translator.translate.NetworkConstants.DEFAULT_FROM_LANGUAGE
import com.bakhur.translator.translate.presentation.TranslateEvent
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
        composable(route = TRANSLATE) {
            val viewModel: AndroidTranslateViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()

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
        ) {
            Text(text = "Voice to text screen")
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
