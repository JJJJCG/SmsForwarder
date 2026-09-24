package cn.ppps.forwarder.workers

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson
import cn.ppps.forwarder.R
import cn.ppps.forwarder.core.Core
import cn.ppps.forwarder.database.entity.Logs
import cn.ppps.forwarder.database.entity.Msg
import cn.ppps.forwarder.database.entity.Rule
import cn.ppps.forwarder.entity.MsgInfo
import cn.ppps.forwarder.utils.DataProvider
import cn.ppps.forwarder.utils.HistoryUtils
import cn.ppps.forwarder.utils.Log
import cn.ppps.forwarder.utils.SendUtils
import cn.ppps.forwarder.utils.SettingUtils
import cn.ppps.forwarder.utils.Worker
import com.xuexiang.xutil.resource.ResUtils
import com.xuexiang.xutil.security.CipherUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Suppress("PrivatePropertyName", "DEPRECATION")
class SendWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    private val TAG: String = SendWorker::class.java.simpleName

    @SuppressLint("SimpleDateFormat")
    override suspend fun doWork(): Result {

        return withContext(Dispatchers.IO) {
            try {
                val msgInfoJson = inputData.getString(Worker.SEND_MSG_INFO)
                if (msgInfoJson.isNullOrBlank()) {
                    return@withContext Result.failure(workDataOf("send" to "msgInfoJson is null"))
                }

                val msgInfo = Gson().fromJson(msgInfoJson, MsgInfo::class.java)
                //【注意】卡槽id：-1=获取失败、0=卡槽1、1=卡槽2，但是 Rule 表里存的是 SIM1/SIM2
                val simSlot = "SIM" + (msgInfo.simSlot + 1)

                // 免打扰(禁用转发)时间段
                var isSilentPeriod = false
                Log.d(TAG, "silentPeriodStart = ${SettingUtils.silentPeriodStart}, silentPeriodEnd = ${SettingUtils.silentPeriodEnd}")
                if (SettingUtils.silentPeriodStart != SettingUtils.silentPeriodEnd) {
                    isSilentPeriod = DataProvider.isCurrentTimeInPeriod(SettingUtils.silentPeriodStart, SettingUtils.silentPeriodEnd)
                    Log.d(TAG, "isSilentPeriod = $isSilentPeriod, enableSilentPeriodLogs = ${SettingUtils.enableSilentPeriodLogs}")
                    if (isSilentPeriod && !SettingUtils.enableSilentPeriodLogs) {
                        Log.e(TAG, "免打扰(禁用转发)时间段")
                        return@withContext Result.failure(workDataOf("send" to "failed"))
                    }
                }

                // 过滤重复消息机制
                val duplicateMessagesLimits = SettingUtils.duplicateMessagesLimits * 1000L
                if (duplicateMessagesLimits > 0L) {
                    val key = CipherUtils.md5(msgInfo.type + msgInfo.from + msgInfo.content)
                    val timestamp: Long = System.currentTimeMillis()
                    var timestampPrev: Long by HistoryUtils(key, timestamp)
                    Log.d(TAG, "duplicateMessagesLimits=$duplicateMessagesLimits, timestamp=$timestamp, timestampPrev=$timestampPrev, msgInfo=$msgInfo")
                    if (timestampPrev != timestamp && timestamp - timestampPrev <= duplicateMessagesLimits) {
                        Log.e(TAG, "过滤重复消息机制")
                        timestampPrev = timestamp
                        return@withContext Result.failure(workDataOf("send" to "failed"))
                    }
                    timestampPrev = timestamp
                }

                val ruleList: List<Rule> = Core.rule.getRuleList(msgInfo.type, 1, simSlot)
                if (ruleList.isEmpty()) {
                    return@withContext Result.failure(workDataOf("send" to "failed"))
                }

                val ruleListMatched: MutableList<Rule> = mutableListOf()
                for (rule in ruleList) {
                    Log.d(TAG, rule.toString())
                    if (rule.checkMsg(msgInfo)) ruleListMatched.add(rule)
                }
                if (ruleListMatched.isEmpty()) {
                    return@withContext Result.failure(workDataOf("send" to "failed"))
                }

                val msg = Msg(0, msgInfo.type, msgInfo.from, msgInfo.content, msgInfo.simSlot, msgInfo.simInfo, msgInfo.subId, msgInfo.callType)
                val msgId = Core.msg.insert(msg)
                for (rule in ruleListMatched) {
                    val sender = rule.senderList[0]
                    if (isSilentPeriod) {
                        val log = Logs(0, msgInfo.type, msgId, rule.id, sender.id, 0, ResUtils.getString(R.string.silent_time_period))
                        Core.logs.insert(log)
                        continue
                    }
                    val log = Logs(0, msgInfo.type, msgId, rule.id, sender.id)
                    val logId = Core.logs.insert(log)
                    SendUtils.sendMsgSender(msgInfo, rule, 0, logId, msgId)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "SendWorker error: ${e.message}")
                return@withContext Result.failure(workDataOf("send" to e.message.toString()))
            }

            return@withContext Result.success()
        }
    }

}