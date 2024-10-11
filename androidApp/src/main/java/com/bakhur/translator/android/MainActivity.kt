package com.bakhur.translator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bakhur.translator.android.core.Routes.TRANSLATE
import com.bakhur.translator.presentation.TranslateScreen
import com.bakhur.translator.translate.presentation.TranslateState
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
//            val viewModel: AndroidTranslateViewModel = hiltViewModel()
//            val state by viewModel.state.collectAsState()
//
//            TranslateScreen(
//                state = state,
//                onEvent = viewModel::onEvent
//            )

            // TODO: use Koin injection
            TranslateScreen(
                state = TranslateState(),
                onEvent = {}
            )
        }
    }
}


//
//@Preview
//@Composable
//fun DefaultPreview() {
//    TranslatorTheme {
//        TranslateRoot()
//    }
//}
