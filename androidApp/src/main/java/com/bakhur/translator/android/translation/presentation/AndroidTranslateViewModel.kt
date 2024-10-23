package com.bakhur.translator.android.translation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bakhur.translator.translation.domain.history.HistoryDataSource
import com.bakhur.translator.translation.domain.translate.TranslateUseCase
import com.bakhur.translator.translation.presentation.TranslateEvent
import com.bakhur.translator.translation.presentation.TranslateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTranslateViewModel @Inject constructor(
    private val translateUseCase: TranslateUseCase,
    private val historyDataSource: HistoryDataSource
) : ViewModel() {

    private val viewModel by lazy {
        TranslateViewModel(
            translateUseCase = translateUseCase,
            historyDataSource = historyDataSource,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: TranslateEvent) = viewModel.onEvent(event)
}