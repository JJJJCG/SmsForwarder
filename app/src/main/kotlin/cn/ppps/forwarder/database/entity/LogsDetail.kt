package cn.ppps.forwarder.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import cn.ppps.forwarder.R
import cn.ppps.forwarder.utils.TYPE_EMAIL
import cn.ppps.forwarder.utils.TYPE_SERVERCHAN
import cn.ppps.forwarder.utils.TYPE_WECHAT
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@DatabaseView("SELECT LOGS.id,LOGS.type,LOGS.msg_id,LOGS.rule_id,LOGS.sender_id,LOGS.forward_status,LOGS.forward_response,LOGS.TIME,Rule.filed AS rule_filed,Rule.`check` AS rule_check,Rule.value AS rule_value,Rule.sim_slot AS rule_sim_slot,Sender.type AS sender_type,Sender.NAME AS sender_name FROM LOGS  LEFT JOIN Rule ON LOGS.rule_id = Rule.id LEFT JOIN Sender ON LOGS.sender_id = Sender.id")
data class LogsDetail(
    @ColumnInfo(name = "id") var id: Long,
    @ColumnInfo(name = "type", defaultValue = "sms") var type: String,
    @ColumnInfo(name = "msg_id", defaultValue = "0") var msgId: Long = 0,
    @ColumnInfo(name = "rule_id", defaultValue = "0") var ruleId: Long = 0,
    @ColumnInfo(name = "sender_id", defaultValue = "0") var senderId: Long = 0,
    @ColumnInfo(name = "forward_status", defaultValue = "1") var forwardStatus: Int = 1,
    @ColumnInfo(name = "forward_response", defaultValue = "") var forwardResponse: String = "",
    @ColumnInfo(name = "time") var time: Date = Date(),
    @ColumnInfo(name = "rule_filed", defaultValue = "") var ruleFiled: String,
    @ColumnInfo(name = "rule_check", defaultValue = "") var ruleCheck: String,
    @ColumnInfo(name = "rule_value", defaultValue = "") var ruleValue: String,
    @ColumnInfo(name = "rule_sim_slot", defaultValue = "") var ruleSimSlot: String,
    @ColumnInfo(name = "sender_type", defaultValue = "1") var senderType: Int = 1,
    @ColumnInfo(name = "sender_name", defaultValue = "") var senderName: String,
) : Parcelable {

    val statusImageId: Int
        get() {
            if (forwardStatus == 1) {
                return R.drawable.ic_round_warning
            } else if (forwardStatus == 2) {
                return R.drawable.ic_round_check
            }
            return R.drawable.ic_round_cancel
        }

    val senderImageId: Int
        get() = when (senderType) {
            TYPE_EMAIL -> R.drawable.icon_email
            TYPE_SERVERCHAN -> R.drawable.icon_serverchan
            TYPE_WECHAT -> R.drawable.icon_wechat
            else -> R.drawable.ic_forwarder
        }
}
