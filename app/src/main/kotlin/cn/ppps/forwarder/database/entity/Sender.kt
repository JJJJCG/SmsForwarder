package cn.ppps.forwarder.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cn.ppps.forwarder.R
import cn.ppps.forwarder.utils.STATUS_OFF
import cn.ppps.forwarder.utils.TYPE_EMAIL
import cn.ppps.forwarder.utils.TYPE_SERVERCHAN
import cn.ppps.forwarder.utils.TYPE_SERVERCHAN3
import cn.ppps.forwarder.utils.TYPE_WECHAT
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "Sender")
data class Sender(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long,
    @ColumnInfo(name = "type", defaultValue = "1") var type: Int = 1,
    @ColumnInfo(name = "name", defaultValue = "") var name: String,
    @ColumnInfo(name = "json_setting", defaultValue = "") var jsonSetting: String,
    @ColumnInfo(name = "status", defaultValue = "1") var status: Int = 1,
    @ColumnInfo(name = "time") var time: Date = Date(),
) : Parcelable {

    val imageId: Int
        get() = when (type) {
            TYPE_EMAIL -> R.drawable.icon_email
            TYPE_SERVERCHAN -> R.drawable.icon_serverchan
            TYPE_SERVERCHAN3 -> R.drawable.icon_serverchan3
            TYPE_WECHAT -> R.drawable.icon_wechat
            else -> R.drawable.ic_forwarder
        }

    val statusImageId: Int
        get() = when (status) {
            STATUS_OFF -> R.drawable.ic_stop
            else -> R.drawable.ic_start
        }

}