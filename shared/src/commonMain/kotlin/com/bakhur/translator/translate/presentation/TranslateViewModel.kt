package com.bakhur.translator.translate.presentation

import com.bakhur.translator.core.EMPTY
import com.bakhur.translator.core.domain.util.Resource
import com.bakhur.translator.core.domain.util.toCommonStateFlow
import com.bakhur.translator.core.presentation.UiLanguage
import com.bakhur.translator.translate.data.history.SqlDelightHistoryDataSource
import com.bakhur.translator.translate.domain.translate.TranslateException
import com.bakhur.translator.translate.domain.translate.TranslateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch

class TranslateViewModel(
    private val translateUseCase: TranslateUseCase,
    private val historyDataSource: SqlDelightHistoryDataSource,
    private val coroutineScope: CoroutineScope?
) {

    /* CoroutineScope(Dispatchers.Main) will be used for iOS */
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(TranslateState())
    val state = combine(
        _state,
        historyDataSource.getHistory(viewModelScope.coroutineContext)
    ) { state, history ->
        if (state.history != history) {
            state.copy(
                history = history.mapNotNull { historyItem ->
                    UiHistoryItem(
                        id = historyItem.id ?: return@mapNotNull null,
                        fromLanguage = UiLanguage.byCode(historyItem.fromLanguageCode),
                        toLanguage = UiLanguage.byCode(historyItem.toLanguageCode),
                        fromText = historyItem.fromText,
                        toText = historyItem.toText
                    )
                }
            )
        } else {
            state
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TranslateState()
    ).toCommonStateFlow()

    private var translateJob: Job? = null

    fun onEvent(event: TranslateEvent) {
        when (event) {
            is TranslateEvent.ChangeTranslationText -> {
                _state.update {
                    it.copy(
                        fromText = event.text
                    )
                }
            }

            is TranslateEvent.ChooseFromLanguage -> {
                _state.update {
                    it.copy(
                        isChoosingFromLanguage = false,
                        fromLanguage = event.language
                    )
                }
            }

            is TranslateEvent.ChooseToLanguage -> {
                val newState = _state.updateAndGet {
                    it.copy(
                        isChoosingToLanguage = false,
                        toLanguage = event.language
                    )
                }
                translate(newState)
            }

            TranslateEvent.CloseTranslation -> {
                _state.update {
                    it.copy(
                        isTranslating = false,
                        fromText = EMPTY,
                        toText = null
                    )
                }
            }

            TranslateEvent.EditTranslation -> {
                if (state.value.toText != null) {
                    _state.update {
                        it.copy(
                            toText = null,
                            isTranslating = false
                        )
                    }
                }
            }

            TranslateEvent.OnErrorSeen -> {
                _state.update {
                    it.copy(
                        error = null
                    )
                }
            }

            TranslateEvent.OpenFromLanguageDropDown -> {
                _state.update {
                    it.copy(
                        isChoosingFromLanguage = true
                    )
                }
            }

            TranslateEvent.OpenToLanguageDropDown -> {
                _state.update {
                    it.copy(
                        isChoosingToLanguage = true
                    )
                }
            }

            is TranslateEvent.SelectHistoryItem -> {
                translateJob?.cancel()

                val historyItem = event.historyItem
                _state.update {
                    it.copy(
                        fromText = historyItem.fromText,
                        toText = historyItem.toText,
                        isTranslating = false,
                        fromLanguage = historyItem.fromLanguage,
                        toLanguage = historyItem.toLanguage
                    )
                }
            }

            TranslateEvent.StopChoosingLanguage -> {
                _state.update {
                    it.copy(
                        isChoosingFromLanguage = false,
                        isChoosingToLanguage = false
                    )
                }
            }

            is TranslateEvent.SubmitVoiceResult -> {
                _state.update {
                    it.copy(
                        fromText = event.result ?: it.fromText,
                        isTranslating = if (event.result != null) false else it.isTranslating,
                        toText = if (event.result != null) null else it.toText
                    )
                }
            }

            TranslateEvent.SwapLanguages -> {
                _state.update {
                    it.copy(
                        fromLanguage = it.toLanguage,
                        toLanguage = it.fromLanguage,
                        fromText = it.toText ?: EMPTY,
                        toText = if (it.toText != null) it.fromText else null
                    )
                }
            }

            TranslateEvent.Translate -> translate(state.value)

            else -> Unit
        }
    }

    private fun translate(state: TranslateState) {
        if (state.isTranslating || state.fromText.isBlank()) return

        translateJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    isTranslating = true
                )
            }

            // TODO: switch to DispatcherIO?
            val result = translateUseCase.execute(
                fromLanguage = state.fromLanguage.language,
                toLanguage = state.toLanguage.language,
                fromText = state.fromText
            )

            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isTranslating = false,
                            toText = result.data
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isTranslating = false,
                            error = (result.throwable as? TranslateException)?.error
                        )
                    }
                }
            }
        }
    }
}