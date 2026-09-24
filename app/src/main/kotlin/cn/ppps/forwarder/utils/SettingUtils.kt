package cn.ppps.forwarder.utils

import cn.ppps.forwarder.R
import com.xuexiang.xutil.resource.ResUtils.getString

class SettingUtils private constructor() {
    companion object {

        //是否同意隐私政策
        var isAgreePrivacy: Boolean by SharedPreference(IS_AGREE_PRIVACY_KEY, false)

        //是否转发短信
        var enableSms: Boolean by SharedPreference(SP_ENABLE_SMS, false)

        //是否转发通话
        var enablePhone: Boolean by SharedPreference(SP_ENABLE_PHONE, false)

        //是否转发通话——来电挂机
        var enableCallType1: Boolean by SharedPreference(SP_ENABLE_CALL_TYPE_1, false)

        //是否转发通话——去电挂机
        var enableCallType2: Boolean by SharedPreference(SP_ENABLE_CALL_TYPE_2, false)

        //是否转发通话——未接来电
        var enableCallType3: Boolean by SharedPreference(SP_ENABLE_CALL_TYPE_3, false)

        //是否转发通话——来电提醒
        var enableCallType4: Boolean by SharedPreference(SP_ENABLE_CALL_TYPE_4, false)

        //是否转发通话——来电接通
        var enableCallType5: Boolean by SharedPreference(SP_ENABLE_CALL_TYPE_5, false)

        //是否转发通话——去电拨出
        var enableCallType6: Boolean by SharedPreference(SP_ENABLE_CALL_TYPE_6, false)

        //是否转发应用通知
        var enableAppNotify: Boolean by SharedPreference(SP_ENABLE_APP_NOTIFY, false)

        //是否靠近听筒关屏
        var enableCloseToEarpieceTurnOffScreen: Boolean by SharedPreference(SP_ENABLE_CLOSE_TO_EARPIECE_TURN_OFF_SCREEN, false)

        //是否转发应用通知——自动消除通知
        var enableCancelAppNotify: Boolean by SharedPreference(SP_ENABLE_CANCEL_APP_NOTIFY, false)

        //是否转发应用通知——自动消除额外APP通知
        var cancelExtraAppNotify: String by SharedPreference(SP_CANCEL_EXTRA_APP_NOTIFY, "")

        //是否转发应用通知——关键词黑名单（一行一个，支持正则，命中标题或内容则不转发）
        var appNotifyBlacklist: String by SharedPreference(SP_APP_NOTIFY_BLACKLIST, "")

        //是否转发应用通知——仅锁屏状态
        var enableNotUserPresent: Boolean by SharedPreference(SP_ENABLE_NOT_USER_PRESENT, false)

        //是否加载应用列表
        var enableLoadAppList: Boolean by SharedPreference(ENABLE_LOAD_APP_LIST, false)

        //是否加载应用列表——用户应用
        var enableLoadUserAppList: Boolean by SharedPreference(ENABLE_LOAD_USER_APP_LIST, false)

        //是否加载应用列表——系统应用
        var enableLoadSystemAppList: Boolean by SharedPreference(ENABLE_LOAD_SYSTEM_APP_LIST, false)

        //过滤多久内重复消息
        var duplicateMessagesLimits: Int by SharedPreference(SP_DUPLICATE_MESSAGES_LIMITS, 0)

        //免打扰(禁用转发)时间段——开始
        var silentPeriodStart: Int by SharedPreference(SP_SILENT_PERIOD_START, 0)

        //免打扰(禁用转发)时间段——结束
        var silentPeriodEnd: Int by SharedPreference(SP_SILENT_PERIOD_END, 0)

        //免打扰(禁用转发)时间段——记录日志
        var enableSilentPeriodLogs: Boolean by SharedPreference(SP_ENABLE_SILENT_PERIOD_LOGS, false)

        //是否不在最近任务列表中显示
        var enableExcludeFromRecents: Boolean by SharedPreference(SP_ENABLE_EXCLUDE_FROM_RECENTS, false)

        //是否启用Cactus增强保活措施
        var enableCactus: Boolean by SharedPreference(SP_ENABLE_CACTUS, false)

        //是否播放静音音乐
        var enablePlaySilenceMusic: Boolean by SharedPreference(SP_ENABLE_PLAY_SILENCE_MUSIC, false)

        //是否启用1像素
        var enableOnePixelActivity: Boolean by SharedPreference(SP_ENABLE_ONE_PIXEL_ACTIVITY, false)

        //无声音乐唤醒间隔（秒，越大越省电）
        var musicInterval: Int by SharedPreference(SP_MUSIC_INTERVAL, 10)

        //请求接口失败重试次数
        var requestRetryTimes: Int by SharedPreference(SP_REQUEST_RETRY_TIMES, 0)

        //请求接口失败重试间隔（秒）
        var requestDelayTime: Int by SharedPreference(SP_REQUEST_DELAY_TIME, 1)

        //请求接口失败超时时间（秒）
        var requestTimeout: Int by SharedPreference(SP_REQUEST_TIMEOUT, 10)

        //通知内容
        var notifyContent: String by SharedPreference(SP_NOTIFY_CONTENT, getString(R.string.notification_content))

        //设备名称
        var extraDeviceMark: String by SharedPreference(SP_EXTRA_DEVICE_MARK, "")

        //SIM1主键
        var subidSim1: Int by SharedPreference(SP_SUBID_SIM1, 0)

        //SIM2主键
        var subidSim2: Int by SharedPreference(SP_SUBID_SIM2, 0)

        //SIM1备注
        var extraSim1: String by SharedPreference(SP_EXTRA_SIM1, "")

        //SIM2备注
        var extraSim2: String by SharedPreference(SP_EXTRA_SIM2, "")

        //是否启用自定义模板
        var enableSmsTemplate: Boolean by SharedPreference(SP_ENABLE_SMS_TEMPLATE, false)

        //自定义模板
        var smsTemplate: String by SharedPreference(SP_SMS_TEMPLATE, "")

        //是否调试模式
        var enableDebugMode: Boolean by SharedPreference(SP_DEBUG_MODE, false)
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}
