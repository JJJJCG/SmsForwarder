package cn.ppps.forwarder.utils.task

import android.os.BatteryManager
import cn.ppps.forwarder.utils.SP_BATTERY_HEALTH
import cn.ppps.forwarder.utils.SP_BATTERY_INFO
import cn.ppps.forwarder.utils.SP_BATTERY_LEVEL
import cn.ppps.forwarder.utils.SP_BATTERY_PCT
import cn.ppps.forwarder.utils.SP_BATTERY_PLUGGED
import cn.ppps.forwarder.utils.SP_BATTERY_STATUS
import cn.ppps.forwarder.utils.SP_BATTERY_TEMPERATURE
import cn.ppps.forwarder.utils.SP_BATTERY_VOLTAGE
import cn.ppps.forwarder.utils.SP_DATA_SIM_SLOT
import cn.ppps.forwarder.utils.SP_IPV4
import cn.ppps.forwarder.utils.SP_IPV6
import cn.ppps.forwarder.utils.SP_IP_LIST
import cn.ppps.forwarder.utils.SP_NETWORK_STATE
import cn.ppps.forwarder.utils.SP_WIFI_SSID
import cn.ppps.forwarder.utils.SharedPreference

/**
 * 设备状态缓存
 *
 * 原项目里的「自动任务」模块已移除，这里只保留消息模板（{{电池电量}}、{{IPV4}} 等）
 * 需要读取的设备状态，由 [cn.ppps.forwarder.receiver.BatteryReceiver] 与
 * [cn.ppps.forwarder.receiver.NetworkChangeReceiver] 在广播到来时写入。
 */
class TaskUtils private constructor() {

    companion object {

        //电池信息
        var batteryInfo: String by SharedPreference(SP_BATTERY_INFO, "")

        //当前电量
        var batteryLevel: Int by SharedPreference(SP_BATTERY_LEVEL, 0)

        //当前电量百分比（level/scale）
        var batteryPct: Float by SharedPreference(SP_BATTERY_PCT, 0.00F)

        //电池状态
        var batteryStatus: Int by SharedPreference(SP_BATTERY_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN)

        //充电方式
        var batteryPlugged: Int by SharedPreference(SP_BATTERY_PLUGGED, BatteryManager.BATTERY_PLUGGED_AC)

        //电池电压（mV）
        var batteryVoltage: Int by SharedPreference(SP_BATTERY_VOLTAGE, 0)

        //电池健康度
        var batteryHealth: Int by SharedPreference(SP_BATTERY_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN)

        //电池温度（℃）
        var batteryTemperature: Int by SharedPreference(SP_BATTERY_TEMPERATURE, 0)

        //网络状态：0-没有网络，1-移动网络，2-WiFi，3-以太网, 4-未知
        var networkState: Int by SharedPreference(SP_NETWORK_STATE, 0)

        //数据卡槽：0-未知，1-卡1，2-卡2
        var dataSimSlot: Int by SharedPreference(SP_DATA_SIM_SLOT, 0)

        //WiFi名称
        var wifiSsid: String by SharedPreference(SP_WIFI_SSID, "")

        //IPv4地址
        var ipv4: String by SharedPreference(SP_IPV4, "")

        //IPv6地址
        var ipv6: String by SharedPreference(SP_IPV6, "")

        //IP地址列表
        var ipList: String by SharedPreference(SP_IP_LIST, "")

    }
}
