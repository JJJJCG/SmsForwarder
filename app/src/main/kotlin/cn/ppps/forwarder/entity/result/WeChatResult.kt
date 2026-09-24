package cn.ppps.forwarder.entity.result

import com.google.gson.annotations.SerializedName

/**
 * 微信本地网关响应：{"ok":true,"chars":7,"truncated":false,"ms":1830}
 */
data class WeChatResult(
    @SerializedName("ok") var ok: Boolean = false,
    @SerializedName("chars") var chars: Int = 0,
    @SerializedName("truncated") var truncated: Boolean = false,
    @SerializedName("ms") var ms: Long = 0,
)
