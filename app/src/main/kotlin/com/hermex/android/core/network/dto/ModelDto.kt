package com.hermex.android.core.network.dto

import kotlinx.serialization.Serializable

/** Model inventory response.
 *
 * Current Hermes Agent returns `providers` from `/api/model/options`; older hermes-webui builds
 * returned `groups` from `/api/models`. Keeping both fields lets existing self-hosted servers and
 * cached test fixtures continue to decode while the Android client follows the current endpoint.
 */
@Serializable
data class ModelsResponse(
    val groups: List<ModelGroupDto>? = null,
    val defaultModel: String? = null,
    val activeProvider: String? = null,
    val providers: List<ProviderModelDto>? = null,
    val model: String? = null,
    val provider: String? = null,
)

@Serializable
data class ProviderModelDto(
    val slug: String? = null,
    val name: String? = null,
    val models: List<String>? = null,
)

@Serializable
data class ModelGroupDto(
    val providerId: String? = null,
    val name: String? = null,
    val models: List<ModelOptionDto>? = null,
    val extraModels: List<ModelOptionDto>? = null,
)

@Serializable
data class ModelOptionDto(
    val id: String? = null,
    val name: String? = null,
    val label: String? = null,
    val providerId: String? = null,
)

/** `GET /api/models/live` -- an uncached, real-time model list for the active provider, used to
 * overlay onto the (possibly stale) cached catalog from [ModelsResponse]. Best-effort: a failed
 * or empty live fetch just means the cached catalog is shown as-is. */
@Serializable
data class ModelsLiveResponse(
    val provider: String? = null,
    val models: List<ModelOptionDto>? = null,
    val count: Int? = null,
)

@Serializable
data class DefaultModelRequest(
    val model: String,
)

@Serializable
data class DefaultModelResponse(
    val ok: Boolean? = null,
    val model: String? = null,
)
