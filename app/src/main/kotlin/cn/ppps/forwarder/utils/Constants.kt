package cn.ppps.forwarder.utils

object Worker {
    const val SEND_MSG_INFO = "send_msg_info"
    const val UPDATE_LOGS = "update_logs"
    const val RULE = "rule"
    const val SENDER_INDEX = "sender_index"
    const val MSG_ID = "msg_id"
}

//服务相关
const val ACTION_START = "START"
const val ACTION_STOP = "STOP"
const val ACTION_RESTART = "RESTART"
const val ACTION_UPDATE_NOTIFICATION = "UPDATE_NOTIFICATION"
const val EXTRA_UPDATE_NOTIFICATION = "EXTRA_UPDATE_NOTIFICATION"

//初始化相关
const val IS_AGREE_PRIVACY_KEY = "is_agree_privacy_key"

//数据库
const val DATABASE_NAME = "sms_forwarder.db"
const val PACKAGE_NAME = "cn.ppps.forwarder"

//通用设置
const val SP_ENABLE_SMS = "enable_sms"

const val SP_ENABLE_PHONE = "enable_phone"
const val SP_ENABLE_CALL_TYPE_1 = "enable_call_type_1"
const val SP_ENABLE_CALL_TYPE_2 = "enable_call_type_2"
const val SP_ENABLE_CALL_TYPE_3 = "enable_call_type_3"
const val SP_ENABLE_CALL_TYPE_4 = "enable_call_type_4"
const val SP_ENABLE_CALL_TYPE_5 = "enable_call_type_5"
const val SP_ENABLE_CALL_TYPE_6 = "enable_call_type_6"

const val SP_ENABLE_APP_NOTIFY = "enable_app_notify"
const val SP_ENABLE_CANCEL_APP_NOTIFY = "enable_cancel_app_notify"
const val SP_CANCEL_EXTRA_APP_NOTIFY = "cancel_extra_app_notify_list"
const val SP_APP_NOTIFY_BLACKLIST = "app_notify_blacklist"
const val SP_ENABLE_NOT_USER_PRESENT = "enable_not_user_present"

const val SP_ENABLE_CLOSE_TO_EARPIECE_TURN_OFF_SCREEN = "enable_close_2_earpiece_turn_off_screen"

const val ENABLE_LOAD_APP_LIST = "enable_load_app_list"
const val ENABLE_LOAD_USER_APP_LIST = "enable_load_user_app_list"
const val ENABLE_LOAD_SYSTEM_APP_LIST = "enable_load_system_app_list"

const val SP_DUPLICATE_MESSAGES_LIMITS = "duplicate_messages_limits"
const val SP_SILENT_PERIOD_START = "silent_period_start"
const val SP_SILENT_PERIOD_END = "silent_period_end"
const val SP_ENABLE_SILENT_PERIOD_LOGS = "enable_silent_period_logs"

const val SP_ENABLE_EXCLUDE_FROM_RECENTS = "enable_exclude_from_recents"
const val SP_ENABLE_PLAY_SILENCE_MUSIC = "enable_play_silence_music"
const val SP_ENABLE_ONE_PIXEL_ACTIVITY = "enable_one_pixel_activity"
const val SP_MUSIC_INTERVAL = "music_interval"

const val SP_REQUEST_RETRY_TIMES = "request_retry_times"
const val SP_REQUEST_DELAY_TIME = "request_delay_time"
const val SP_REQUEST_TIMEOUT = "request_timeout"

const val SP_NOTIFY_CONTENT = "notify_content"
const val SP_EXTRA_DEVICE_MARK = "extra_device_mark"
const val SP_SUBID_SIM1 = "subid_sim1"
const val SP_SUBID_SIM2 = "subid_sim2"
const val SP_EXTRA_SIM1 = "extra_sim1"
const val SP_EXTRA_SIM2 = "extra_sim2"
const val SP_ENABLE_SMS_TEMPLATE = "enable_sms_template"
const val SP_SMS_TEMPLATE = "sms_template"

const val SP_DEBUG_MODE = "enable_debug_mode"

const val SP_ENABLE_CACTUS = "enable_cactus"
const val CACTUS_TIMER = "cactus_timer"
const val CACTUS_LAST_TIMER = "cactus_last_timer"
const val CACTUS_DATE = "cactus_date"
const val CACTUS_END_DATE = "cactus_end_date"

//规则相关
const val STATUS_ON = 1
const val STATUS_OFF = 0
const val FILED_TRANSPOND_ALL = "transpond_all"
const val FILED_PHONE_NUM = "phone_num"
const val FILED_CALL_TYPE = "call_type"
const val FILED_PACKAGE_NAME = "package_name"
const val FILED_UID = "uid"
const val FILED_MSG_CONTENT = "msg_content"
const val FILED_INFORM_CONTENT = "inform_content"
const val FILED_MULTI_MATCH = "multi_match"
const val CHECK_IS = "is"
const val CHECK_CONTAIN = "contain"
const val CHECK_NOT_CONTAIN = "notcontain"
const val CHECK_START_WITH = "startwith"
const val CHECK_END_WITH = "endwith"
const val CHECK_NOT_IS = "notis"
const val CHECK_REGEX = "regex"
const val CHECK_SIM_SLOT_ALL = "ALL"
const val CHECK_SIM_SLOT_1 = "SIM1"
const val CHECK_SIM_SLOT_2 = "SIM2"

//发送通道执行逻辑：ALL=全部执行, UntilFail=失败即终止, UntilSuccess=成功即终止
const val SENDER_LOGIC_ALL = "ALL"
const val SENDER_LOGIC_UNTIL_FAIL = "UntilFail"
const val SENDER_LOGIC_UNTIL_SUCCESS = "UntilSuccess"
const val SENDER_LOGIC_RETRY = "Retry"

//发送通道（精简版：仅保留邮箱、Server酱、微信本地网关）
//注意：type 值沿用原项目编号，避免老用户数据库中的 Sender.type 错位
const val TYPE_EMAIL = 1
const val TYPE_SERVERCHAN = 6
const val TYPE_WECHAT = 16

//微信本地网关：请求方式
const val WECHAT_MODE_JSON = "json"      //标准 JSON  POST
const val WECHAT_MODE_TEXT = "text"      //裸文本  POST --data-binary
const val WECHAT_MODE_GET = "get"        //GET     ?token=xxx&text=xxx

//前台服务
const val FRONT_NOTIFY_ID = 0x1010
const val FRONT_CHANNEL_ID = "cn.ppps.forwarder"
const val FRONT_CHANNEL_NAME = "SmsForwarder Foreground Service"

//吐司监听
const val EVENT_TOAST_SUCCESS = "key_toast_success"
const val EVENT_TOAST_ERROR = "key_toast_error"
const val EVENT_TOAST_INFO = "key_toast_info"
const val EVENT_TOAST_WARNING = "key_toast_warning"

const val KEY_SENDER_ID = "key_sender_id"
const val KEY_SENDER_TYPE = "key_sender_type"
const val KEY_SENDER_CLONE = "key_sender_clone"
const val KEY_SENDER_TEST = "key_sender_test"

const val KEY_RULE_ID = "key_rule_id"
const val KEY_RULE_TYPE = "key_rule_type"
const val KEY_RULE_CLONE = "key_rule_clone"
const val KEY_DEFAULT_SELECTION = "key_default_selection"

//转发规则测试弹窗填写内容缓存（key需拼接ruleType后缀区分）
const val SP_RULE_TEST_SIM_SLOT = "rule_test_sim_slot_"
const val SP_RULE_TEST_FROM = "rule_test_from_"
const val SP_RULE_TEST_TITLE = "rule_test_title_"
const val SP_RULE_TEST_CONTENT = "rule_test_content_"
const val SP_RULE_TEST_CALL_TYPE = "rule_test_call_type_"

const val EVENT_LOAD_APP_LIST = "EVENT_LOAD_APP_LIST"

const val EVENT_KEY_SIM_SLOT = "EVENT_KEY_SIM_SLOT"
const val EVENT_KEY_PHONE_NUMBERS = "EVENT_KEY_PHONE_NUMBERS"

//设备状态缓存（消息模板用）
const val SP_BATTERY_INFO = "battery_info"
const val SP_BATTERY_STATUS = "battery_status"
const val SP_BATTERY_LEVEL = "battery_level"
const val SP_BATTERY_PCT = "battery_pct"
const val SP_BATTERY_PLUGGED = "battery_plugged"
const val SP_BATTERY_VOLTAGE = "battery_voltage"
const val SP_BATTERY_HEALTH = "battery_health"
const val SP_BATTERY_TEMPERATURE = "battery_temperature"

const val SP_NETWORK_STATE = "network_state"
const val SP_DATA_SIM_SLOT = "data_sim_slot"
const val SP_WIFI_SSID = "wifi_ssid"
const val SP_IPV4 = "ipv4"
const val SP_IPV6 = "ipv6"
const val SP_IP_LIST = "ip_list"

//切换语言需要替换的自定义模板标签列表
val TAG_LIST = arrayOf(
    mapOf("zh_CN" to "{{来源号码}}", "zh_TW" to "{{來源號碼}}", "en" to "{{FROM}}"),
    mapOf("zh_CN" to "{{短信内容}}", "zh_TW" to "{{簡訊內容}}", "en" to "{{SMS}}"),
    mapOf("zh_CN" to "{{APP包名}}", "zh_TW" to "{{APP包名}}", "en" to "{{PACKAGE_NAME}}"),
    mapOf("zh_CN" to "{{APP名称}}", "zh_TW" to "{{APP名稱}}", "en" to "{{APP_NAME}}"),
    mapOf("zh_CN" to "{{通知内容}}", "zh_TW" to "{{通知內容}}", "en" to "{{MSG}}"),
    mapOf("zh_CN" to "{{卡槽信息}}", "zh_TW" to "{{卡槽信息}}", "en" to "{{CARD_SLOT}}"),
    mapOf("zh_CN" to "{{卡槽主键}}", "zh_TW" to "{{卡槽主鍵}}", "en" to "{{CARD_SUBID}}"),
    mapOf("zh_CN" to "{{接收时间}}", "zh_TW" to "{{接收時間}}", "en" to "{{RECEIVE_TIME}}"),
    mapOf("zh_CN" to "{{当前时间}}", "zh_TW" to "{{當前時間}}", "en" to "{{CURRENT_TIME}}"),
    mapOf("zh_CN" to "{{设备名称}}", "zh_TW" to "{{設備名稱}}", "en" to "{{DEVICE_NAME}}"),
    mapOf("zh_CN" to "{{当前应用版本号}}", "zh_TW" to "{{當前應用版本號}}", "en" to "{{APP_VERSION}}"),
    mapOf("zh_CN" to "{{通知标题}}", "zh_TW" to "{{通知標題}}", "en" to "{{TITLE}}"),
    mapOf("zh_CN" to "{{通知Scheme}}", "zh_TW" to "{{通知Scheme}}", "en" to "{{SCHEME}}"),
    mapOf("zh_CN" to "{{通话类型}}", "zh_TW" to "{{通話類型}}", "en" to "{{CALL_TYPE}}"),
    mapOf("zh_CN" to "{{定位信息}}", "zh_TW" to "{{定位信息}}", "en" to "{{LOCATION}}"),
    mapOf("zh_CN" to "{{定位信息_经度}}", "zh_TW" to "{{定位信息_經度}}", "en" to "{{LOCATION_LONGITUDE}}"),
    mapOf("zh_CN" to "{{定位信息_纬度}}", "zh_TW" to "{{定位信息_緯度}}", "en" to "{{LOCATION_LATITUDE}}"),
    mapOf("zh_CN" to "{{定位信息_地址}}", "zh_TW" to "{{定位信息_地址}}", "en" to "{{LOCATION_ADDRESS}}"),
    mapOf("zh_CN" to "{{电池电量}}", "zh_TW" to "{{電池電量}}", "en" to "{{BATTERY_PCT}}"),
    mapOf("zh_CN" to "{{电池状态}}", "zh_TW" to "{{電池狀態}}", "en" to "{{BATTERY_STATUS}}"),
    mapOf("zh_CN" to "{{充电方式}}", "zh_TW" to "{{充電方式}}", "en" to "{{BATTERY_PLUGGED}}"),
    mapOf("zh_CN" to "{{电池信息}}", "zh_TW" to "{{電池信息}}", "en" to "{{BATTERY_INFO}}")
)
