package com.bakhur.translator.translate.presentation

import app.cash.turbine.test
import com.bakhur.translator.core.presentation.UiLanguage
import com.bakhur.translator.translate.data.local.FakeHistoryDataSource
import com.bakhur.translator.translate.data.remote.FakeTranslateClient
import com.bakhur.translator.translate.domain.history.HistoryItem
import com.bakhur.translator.translate.domain.translate.TranslateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TranslateViewModelTest {

    private lateinit var viewModel: TranslateViewModel
    private lateinit var fakeTranslateClient: FakeTranslateClient
    private lateinit var fakeHistoryDataSource: FakeHistoryDataSource

    @BeforeTest
    fun setUp() {
        fakeTranslateClient = FakeTranslateClient()
        fakeHistoryDataSource = FakeHistoryDataSource()

        val fakeTranslateUseCase = TranslateUseCase(
            translateClient = fakeTranslateClient,
            historyDataSource = fakeHistoryDataSource
        )

        viewModel = TranslateViewModel(
            translateUseCase = fakeTranslateUseCase,
            historyDataSource = fakeHistoryDataSource,
            coroutineScope = CoroutineScope(Dispatchers.Default)
        )
    }

    @Test
    fun `State and history items are properly combined`() = runBlocking {
        viewModel.state.test {
            val initialState = awaitItem()
            assertEquals(initialState, TranslateState())

            val historyItem = HistoryItem(
                id = 0,
                fromLanguageCode = "en",
                toLanguageCode = "ru",
                fromText = "from",
                toText = "to"
            )

            fakeHistoryDataSource.insertHistoryItem(historyItem)

            val state = awaitItem()

            val expectedItem = UiHistoryItem(
                id = historyItem.id!!,
                fromLanguage = UiLanguage.byCode(historyItem.fromLanguageCode),
                toLanguage = UiLanguage.byCode(historyItem.toLanguageCode),
                fromText = historyItem.fromText,
                toText = historyItem.toText
            )

            assertEquals(state.history.first(), expectedItem)
        }
    }

    @Test
    fun `Translate success - state properly updated`() = runBlocking {
        viewModel.state.test {
            awaitItem()

            viewModel.onEvent(TranslateEvent.ChangeTranslationText("test"))
            awaitItem()

            viewModel.onEvent(TranslateEvent.Translate)

            val loadingState = awaitItem()
            assertTrue { loadingState.isTranslating }

            val resultState = awaitItem()
            assertFalse { resultState.isTranslating }
            assertEquals(resultState.toText, fakeTranslateClient.translatedText)
        }
    }
}