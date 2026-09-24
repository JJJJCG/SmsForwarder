package cn.ppps.forwarder.entity.setting

import java.io.Serializable

/**
 * 微信本地网关
 *
 * 网关 API 约定（由本机其它程序提供）：
 *
 * ```
 * # 标准 JSON
 * curl -X POST http://127.0.0.1:9901/v1/wechat -H "Authorization: Bearer $TOKEN" \
 *      -H 'content-type: application/json' -d '{"text":"洗衣机洗完了"}'
 * # → {"ok":true,"chars":7,"truncated":false,"ms":1830}
 *
 * # 裸文本
 * curl -X POST http://127.0.0.1:9901/v1/wechat -H "Authorization: Bearer $TOKEN" \
 *      --data-binary '服务器磁盘 91% 了'
 *
 * # 浏览器 / 不便 POST 的场合
 * curl "http://127.0.0.1:9901/v1/wechat?token=$TOKEN&text=测试"
 * ```
 */
data class WeChatSetting(
    var host: String = "127.0.0.1", //网关地址（可带 http(s):// 前缀）
    var port: String = "9901", //网关端口
    var token: String = "", //访问令牌，POST 走 Authorization: Bearer，GET 走 token 参数
    var mode: String = "json", //请求方式：json=标准JSON、text=裸文本、get=GET
    var titleTemplate: String = "", //标题模板（留空则只推送正文）
) : Serializable
