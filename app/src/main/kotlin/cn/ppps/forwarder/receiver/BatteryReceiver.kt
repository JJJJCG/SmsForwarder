package cn.ppps.forwarder.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import cn.ppps.forwarder.utils.BatteryUtils
import cn.ppps.forwarder.utils.task.TaskUtils

/**
 * 电池状态监听：只负责刷新设备状态缓存，供消息模板（{{电池电量}} 等）读取
 */
class BatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (context == null || intent?.action != Intent.ACTION_BATTERY_CHANGED) return

        TaskUtils.batteryInfo = BatteryUtils.getBatteryInfo(intent).toString()
        TaskUtils.batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)

        val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100)
        TaskUtils.batteryPct = TaskUtils.batteryLevel.toFloat() / scale.toFloat() * 100

        TaskUtils.batteryPlugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
        TaskUtils.batteryStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        TaskUtils.batteryVoltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0)

        //EXTRA_TEMPERATURE 单位为 0.1℃，换算为 ℃
        TaskUtils.batteryTemperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10

        TaskUtils.batteryHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN)
    }

}
