package com.hermex.android.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermex.android.auth.AuthRepository
import com.hermex.android.core.network.ApiError
import com.hermex.android.core.network.HermexApi
import com.hermex.android.core.network.dto.DefaultModelRequest
import com.hermex.android.core.network.dto.ModelCatalogOption
import com.hermex.android.core.network.dto.ModelCatalogParser
import com.hermex.android.core.network.dto.mergingLiveModels
import com.hermex.android.core.network.safeApiCall
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Picks the server's remembered default model (`GET`/`POST /api/default-model`) -- this is
 * server-side state, not a local preference: it's what `POST /api/session/new` falls back to
 * when a session is created without an explicit `model` override, matching iOS. */
class DefaultModelViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DefaultModelUiState())
    val uiState: StateFlow<DefaultModelUiState> = _uiState.asStateFlow()

    init { load() }

    fun load() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val api = authRepository.apiForActiveServer()
            if (api == null) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "サインインしていません。") }
                return@launch
            }
            try {
                val response = safeApiCall { api.models() }
                _uiState.update {
                    it.copy(
                        groups = ModelCatalogParser.parseGroups(response),
                        defaultModelId = response.defaultModel ?: response.model,
                    )
                }
            } catch (e: ApiError) {
                _uiState.update { it.copy(errorMessage = e.message ?: "モデルを読み込めませんでした。") }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }


    fun selectModel(option: ModelCatalogOption) {
        if (option.id == _uiState.value.defaultModelId || _uiState.value.savingModelId != null) return
        val api = authRepository.apiForActiveServer()
        if (api == null) {
            _uiState.update { it.copy(errorMessage = "サインインしていません。") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(savingModelId = option.id, errorMessage = null) }
            try {
                val response = safeApiCall { api.setDefaultModel(DefaultModelRequest(option.id)) }
                if (response.ok == true) {
                    _uiState.update { it.copy(savingModelId = null, defaultModelId = response.model ?: option.id) }
                } else {
                    _uiState.update { it.copy(savingModelId = null, errorMessage = "The server did not confirm the change.") }
                }
            } catch (e: ApiError) {
                _uiState.update { it.copy(savingModelId = null, errorMessage = e.message ?: "既定モデルを設定できませんでした。") }
            }
        }
    }
}
