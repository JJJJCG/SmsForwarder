package cn.ppps.forwarder.utils.sender

import android.text.TextUtils
import cn.ppps.forwarder.database.entity.Rule
import cn.ppps.forwarder.entity.MsgInfo
import cn.ppps.forwarder.entity.result.Serverchan3Result
import cn.ppps.forwarder.entity.setting.Serverchan3Setting
import cn.ppps.forwarder.utils.Log
import cn.ppps.forwarder.utils.SendUtils
import cn.ppps.forwarder.utils.SettingUtils
import cn.ppps.forwarder.utils.interceptor.LoggingInterceptor
import com.google.gson.Gson
import com.xuexiang.xhttp2.XHttp
import com.xuexiang.xhttp2.callback.SimpleCallBack
import com.xuexiang.xhttp2.exception.ApiException

/**
 * Server酱³ —— https://sc3.ft07.com/
 *
 * 与 Server酱·Turbo 不是同一套用户体系，SendKey 不通用：
 *   Server酱·Turbo：https://sctapi.ftqq.com/{SENDKEY}.send      （SendKey 以 SCT 开头）
 *   Server酱³      ：https://{uid}.push.ft07.com/send/{SENDKEY}.send（SendKey 以 sctp 开头）
 *
 * uid 可从 SendKey 中提取，规则：sctp{uid}t...（正则 /^sctp(\d+)t/）
 * 官方文档：http://doc.ft07.com/zh/serverchan3/server/api
 */
class Serverchan3Utils {
    companion object {

        private val TAG: String = Serverchan3Utils::class.java.simpleName

        /**
         * 由用户填写的内容推导出真正的请求地址
         *  - 填写 SendKey（sctp 开头）→ 按 uid 拼出接口地址
         *  - 直接粘贴 SendKey 页面给出的完整 API 地址 → 原样使用
         *  - 其它 → 返回 null，由调用方提示格式错误
         */
        fun buildRequestUrl(rawSendKey: String): String? {
            val key = rawSendKey.trim()
            if (TextUtils.isEmpty(key)) return null

            //允许直接粘贴 SendKey 页面给出的完整 API 地址
            if (key.startsWith("http://", true) || key.startsWith("https://", true)) {
                return if (key.contains(".push.ft07.com/send/")) key else null
            }

            //从 sctp{uid}t... 中提取 uid
            val uid = Regex("^sctp(\\d+)t", RegexOption.IGNORE_CASE)
                .find(key)?.groupValues?.get(1)
            if (TextUtils.isEmpty(uid)) return null

            return "https://$uid.push.ft07.com/send/$key.send"
        }

        fun sendMsg(
            setting: Serverchan3Setting,
            msgInfo: MsgInfo,
            rule: Rule? = null,
            senderIndex: Int = 0,
            logId: Long = 0L,
            msgId: Long = 0L
        ) {
            val requestUrl = buildRequestUrl(setting.sendKey)
            if (requestUrl.isNullOrEmpty()) {
                val detail = "SendKey 格式不正确：Server酱³ 的 SendKey 应以 sctp 开头"
                Log.e(TAG, detail)
                SendUtils.updateLogs(logId, 0, detail)
                SendUtils.senderLogic(0, msgInfo, rule, senderIndex, msgId)
                return
            }

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

            Log.i(TAG, "requestUrl:$requestUrl")

            val request = XHttp.post(requestUrl)
                .params("title", title)
                .params("desp", content)

            //Server酱³ 专有参数：tags（多个用竖线分隔）、short（消息卡片摘要）
            if (!TextUtils.isEmpty(setting.tags)) request.params("tags", setting.tags)
            if (!TextUtils.isEmpty(setting.short)) request.params("short", setting.short)

            request.keepJson(true)
                .retryCount(SettingUtils.requestRetryTimes) //超时重试的次数
                .retryDelay(SettingUtils.requestDelayTime * 1000) //超时重试的延迟时间
                .retryIncreaseDelay(SettingUtils.requestDelayTime * 1000) //超时重试叠加延时
                .timeStamp(true) //url自动追加时间戳，避免缓存
                .addInterceptor(LoggingInterceptor(logId)) //增加一个log拦截器, 记录请求日志
                .execute(object : SimpleCallBack<String>() {

                    override fun onError(e: ApiException) {
                        Log.e(TAG, e.detailMessage)
                        SendUtils.updateLogs(logId, 0, e.displayMessage)
                        SendUtils.senderLogic(0, msgInfo, rule, senderIndex, msgId)
                    }

                    override fun onSuccess(response: String) {
                        Log.i(TAG, response)
                        val resp = try {
                            Gson().fromJson(response, Serverchan3Result::class.java)
                        } catch (e: Exception) {
                            null
                        }
                        //Server酱³ 成功时返回 code=0
                        val status = if (resp?.code == 0L) 2 else 0
                        SendUtils.updateLogs(logId, status, response)
                        SendUtils.senderLogic(status, msgInfo, rule, senderIndex, msgId)
                    }

                })
        }

    }
}
