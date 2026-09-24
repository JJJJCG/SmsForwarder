package cn.ppps.forwarder.utils.sender

import android.text.TextUtils
import cn.ppps.forwarder.database.entity.Rule
import cn.ppps.forwarder.entity.MsgInfo
import cn.ppps.forwarder.entity.result.WeChatResult
import cn.ppps.forwarder.entity.setting.WeChatSetting
import cn.ppps.forwarder.utils.Log
import cn.ppps.forwarder.utils.SendUtils
import cn.ppps.forwarder.utils.SettingUtils
import cn.ppps.forwarder.utils.WECHAT_MODE_GET
import cn.ppps.forwarder.utils.WECHAT_MODE_TEXT
import cn.ppps.forwarder.utils.interceptor.LoggingInterceptor
import com.google.gson.Gson
import com.xuexiang.xhttp2.XHttp
import com.xuexiang.xhttp2.callback.SimpleCallBack
import com.xuexiang.xhttp2.exception.ApiException

/**
 * 微信本地网关发送通道
 *
 * 对应网关接口：POST/GET http://{host}:{port}/v1/wechat
 */
class WeChatUtils private constructor() {

    companion object {

        private val TAG: String = WeChatUtils::class.java.simpleName

        //网关接口路径
        private const val API_PATH = "/v1/wechat"

        //裸文本请求的 Content-Type
        private const val MEDIA_TYPE = "text/plain; charset=utf-8"

        fun sendMsg(
            setting: WeChatSetting,
            msgInfo: MsgInfo,
            rule: Rule? = null,
            senderIndex: Int = 0,
            logId: Long = 0L,
            msgId: Long = 0L
        ) {
            val title: String = if (rule != null) {
                msgInfo.getTitleForSend(setting.titleTemplate, rule.regexReplace, rule.title)
            } else {
                msgInfo.getTitleForSend(setting.titleTemplate)
            }
            val content: String = if (rule != null) {
                msgInfo.getContentForSend(rule.smsTemplate, rule.regexReplace, rule.title)
            } else {
                msgInfo.getContentForSend(SettingUtils.smsTemplate)
            }

            //网关只接受一个 text 字段：配置了标题模板则「标题+换行+正文」，否则只推正文
            val text: String = if (TextUtils.isEmpty(setting.titleTemplate.trim())) {
                content
            } else {
                "$title\n$content"
            }

            val requestUrl = buildRequestUrl(setting)
            val token = setting.token.trim()
            Log.i(TAG, "requestUrl:$requestUrl, mode:${setting.mode}, text:$text")

            val builder = if (setting.mode == WECHAT_MODE_GET) XHttp.get(requestUrl) else XHttp.post(requestUrl)
            builder.keepJson(true)

            //GET 方式令牌放 query，POST 方式令牌放 Authorization 头
            if (!TextUtils.isEmpty(token)) {
                if (setting.mode == WECHAT_MODE_GET) {
                    builder.params("token", token)
                } else {
                    builder.headers("Authorization", "Bearer $token")
                }
            }

            when (setting.mode) {
                WECHAT_MODE_GET -> builder.params("text", text)
                WECHAT_MODE_TEXT -> builder.upString(text, MEDIA_TYPE)
                else -> builder.upJson(Gson().toJson(mapOf("text" to text)))
            }

            builder.retryCount(SettingUtils.requestRetryTimes) //超时重试的次数
                .retryDelay(SettingUtils.requestDelayTime * 1000) //超时重试的延迟时间
                .retryIncreaseDelay(SettingUtils.requestDelayTime * 1000) //超时重试叠加延时
                .addInterceptor(LoggingInterceptor(logId)) //增加一个log拦截器, 记录请求日志
                .execute(object : SimpleCallBack<String>() {

                    override fun onError(e: ApiException) {
                        Log.e(TAG, e.detailMessage)
                        val status = 0
                        SendUtils.updateLogs(logId, status, e.displayMessage)
                        SendUtils.senderLogic(status, msgInfo, rule, senderIndex, msgId)
                    }

                    override fun onSuccess(response: String) {
                        Log.i(TAG, response)
                        val resp = try {
                            Gson().fromJson(response, WeChatResult::class.java)
                        } catch (e: Exception) {
                            null
                        }
                        val status = if (resp?.ok == true) 2 else 0
                        val detail = if (resp?.ok == true) {
                            response + "\n字符数:${resp.chars}, 截断:${resp.truncated}, 耗时:${resp.ms}ms"
                        } else {
                            response
                        }
                        SendUtils.updateLogs(logId, status, detail)
                        SendUtils.senderLogic(status, msgInfo, rule, senderIndex, msgId)
                    }

                })
        }

        /**
         * 拼接请求地址，允许用户在「网关地址」里直接写 `http://ip:port`，
         * 端口留空时使用默认 9901。
         */
        private fun buildRequestUrl(setting: WeChatSetting): String {
            val raw = setting.host.trim().ifEmpty { DEFAULT_HOST }
            val scheme = if (raw.startsWith("https://")) "https" else "http"
            var authority = raw.removePrefix("https://").removePrefix("http://").trimEnd('/')
            val port = setting.port.trim().ifEmpty { DEFAULT_PORT }
            //地址里已显式带了端口就不再拼接
            if (authority.substringAfterLast(':').toIntOrNull() == null) {
                authority = "$authority:$port"
            }
            return "$scheme://$authority$API_PATH"
        }

        private const val DEFAULT_HOST = "127.0.0.1"
        private const val DEFAULT_PORT = "9901"

    }
}
