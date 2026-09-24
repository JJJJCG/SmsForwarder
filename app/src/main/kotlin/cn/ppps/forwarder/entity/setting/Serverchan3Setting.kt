package cn.ppps.forwarder.entity.setting

import java.io.Serializable

data class Serverchan3Setting(
    var sendKey: String = "",
    var tags: String = "",
    var short: String = "",
    var titleTemplate: String = "",
) : Serializable
