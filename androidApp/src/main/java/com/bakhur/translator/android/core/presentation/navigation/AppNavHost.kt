package com.bakhur.translator.android.core.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bakhur.translator.android.core.presentation.navigation.NavArguments.LANGUAGE_CODE
import com.bakhur.translator.android.core.presentation.navigation.NavArguments.VOICE_RESULT
import com.bakhur.translator.android.translation.presentation.AndroidTranslateViewModel
import com.bakhur.translator.android.translation.presentation.TranslateScreen
import com.bakhur.translator.android.voice_to_text.presentation.AndroidVoiceToTextViewModel
import com.bakhur.translator.android.voice_to_text.presentation.components.VoiceToTextScreen
import com.bakhur.translator.translation.presentation.TranslateEvent
import com.bakhur.translator.translation.presentation.util.NetworkConstants.DEFAULT_FROM_LANGUAGE
import com.bakhur.translator.voice_to_text.presentation.VoiceToTextEvent

private const val animationDuration = 400

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Translate.route
    ) {
        composable(
            route = Screen.Translate.route,
            enterTransition = { enterAnimation() },
            exitTransition = { exitAnimation() },
            popEnterTransition = { enterAnimation() },
            popExitTransition = { exitAnimation() }
        ) { backStackEntry ->
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
                            navController.navigate(
                                Screen.VoiceToText.createRoute(state.fromLanguage.language.langCode)
                            )
                        }

                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }

        composable(
            route = Screen.VoiceToText.route,
            arguments = listOf(
                navArgument(name = LANGUAGE_CODE) {
                    type = NavType.StringType
                    defaultValue = DEFAULT_FROM_LANGUAGE
                }
            ),
            enterTransition = { enterAnimation() },
            exitTransition = { exitAnimation() },
            popEnterTransition = { enterAnimation() },
            popExitTransition = { exitAnimation() }
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

fun AnimatedContentTransitionScope<NavBackStackEntry>.enterAnimation(): EnterTransition =
    fadeIn(
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = LinearEasing
        )
    ) + slideIntoContainer(
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = EaseIn
        ),
        towards = AnimatedContentTransitionScope.SlideDirection.Start
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.exitAnimation(): ExitTransition =
    fadeOut(
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = LinearEasing
        )
    ) + slideOutOfContainer(
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = EaseOut
        ),
        towards = AnimatedContentTransitionScope.SlideDirection.End
    )