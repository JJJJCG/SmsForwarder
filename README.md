![SmsForwarder](pic/SmsForwarder.png)

# SmsForwarder-短信转发器（精简版）

> ⚠️ 本仓库是上游 `pppscn/SmsForwarder` 的**个人精简分支**，只保留「通知转发」核心能力。
>
> **保留**：短信 / 来电 / APP 通知的监控与按规则转发；保活（Cactus）；规则、通道、日志、应用列表。
> **发送通道只剩 4 个**：邮箱、Server酱·Turbo、Server酱³、微信（本地网关）。
> **已移除**：自动任务、主动控制（客户端/服务端）、内网穿透 FRPC、定位、在线更新、短信指令、Tinker 热修复。
>
> 相比上游：Kotlin 源码 339 → 157 个文件（47,135 → 19,988 行），布局 101 → 36 个，并移除了 21.9 MB 的
> `frpclib.aar`。详见 [`精简评估报告.md`](精简评估报告.md)。

## 微信（本地网关）通道

发送到本机运行的微信网关，地址与令牌在应用内的通道配置页填写（默认 `127.0.0.1:9901`），
支持三种请求方式：

```bash
# 标准 JSON
curl -X POST http://127.0.0.1:9901/v1/wechat -H "Authorization: Bearer $TOKEN" \
     -H 'content-type: application/json' -d '{"text":"洗衣机洗完了"}'
# → {"ok":true,"chars":7,"truncated":false,"ms":1830}

# 裸文本
curl -X POST http://127.0.0.1:9901/v1/wechat -H "Authorization: Bearer $TOKEN" \
     --data-binary '服务器磁盘 91% 了'

# 浏览器 / 不便 POST 的场合
curl "http://127.0.0.1:9901/v1/wechat?token=$TOKEN&text=测试"
```

> 标题模板留空时只推送正文；填写后按「标题\n正文」拼接。

## Server酱³ 通道

与 Server酱·Turbo **不是同一套用户体系，SendKey 不通用**，两者在通道列表里是两个独立通道：

| 通道 | 接口地址 | SendKey 前缀 |
|---|---|---|
| Server酱·Turbo | `https://sctapi.ftqq.com/{SENDKEY}.send` | `SCT` |
| Server酱³ | `https://{uid}.push.ft07.com/send/{SENDKEY}.send` | `sctp` |

在通道配置页填入 SendKey 即可，`uid` 会自动从 SendKey 中提取（规则 `/^sctp(\d+)t/`）；
也可以直接粘贴 SendKey 页面给出的完整 API 地址。另外支持两个 Server酱³ 专有参数：

- **标签**：多个标签用竖线 `|` 分隔，例如 `服务器报警|报告`
- **简短描述**：消息卡片的摘要，推送 Markdown 时建议填写

> 官方文档：<http://doc.ft07.com/zh/serverchan3/server/api>

--------

## 构建与发布

> 只产出 **arm64-v8a** 单包，Release 已签名。

### GitHub Actions（推荐）

工作流 [`.github/workflows/Release.yml`](.github/workflows/Release.yml)：

- **推标签触发**：`git tag v3.5.0-slim && git push origin v3.5.0-slim`
- **手动触发**：Actions → Release → Run workflow，填写 release 标签

两种方式都会：构建 → 签名 → 上传 Artifact → 创建 GitHub Release 并附上 APK。

### 签名密钥（Secrets）

仓库里没有 keystore（`.gitignore` 已忽略 `/keystore`）。CI 从以下 Secrets 还原：

| Secret | 说明 |
|---|---|
| `KEYSTORE_BASE64` | keystore 文件的 base64（`base64 -w0 keystore/smsf-slim.p12`） |
| `KEYSTORE_PASSWORD` | keystore 口令 |
| `KEY_ALIAS` | 密钥别名（当前为 `smsf`） |
| `KEY_PASSWORD` | 密钥口令 |

本地构建把 `keystore/keystore.properties` 放好即可（格式见下），AGP 会自动读取：

```properties
keyAlias=smsf
keyPassword=******
storeFile=../keystore/smsf-slim.p12
storePassword=******
storeType=PKCS12
```

> ⚠️ 该密钥是自签的，与上游官方包签名不同：**装过官方版的手机需要先卸载**。
> 请务必备份 `keystore/` 目录，丢失后无法再对已安装的旧版本做覆盖升级。

### 本地构建

```bash
./gradlew assembleRelease
# 产物：build/app/outputs/apk/release/SmsF_<版本>_<版本号>_arm64-v8a_release.apk
```

--------

[English Version](README_en.md)

[![GitHub release](https://img.shields.io/github/release/pppscn/SmsForwarder.svg)](https://github.com/pppscn/SmsForwarder/releases) [![GitHub stars](https://img.shields.io/github/stars/pppscn/SmsForwarder)](https://github.com/pppscn/SmsForwarder/stargazers) [![GitHub forks](https://img.shields.io/github/forks/pppscn/SmsForwarder)](https://github.com/pppscn/SmsForwarder/network/members) [![GitHub issues](https://img.shields.io/github/issues/pppscn/SmsForwarder)](https://github.com/pppscn/SmsForwarder/issues) [![GitHub license](https://img.shields.io/github/license/pppscn/SmsForwarder)](https://github.com/pppscn/SmsForwarder/blob/main/LICENSE)

--------

短信转发器——不仅只转发短信，备用机必备神器！

监控Android手机短信、来电、APP通知，并根据指定规则转发到其他手机：邮箱、Server酱·Turbo、Server酱³、微信（本地网关）。

> 注意：从`2022-06-06`开始，原`Java版`的代码归档到`v2.x`分支，不再更新！

> `v3.x` 适配 Android 4.4 ~ 13.0

> `加入SmsF预览体验计划`（在线更新每周构建版，率先体验新版&修复BUG）

**升级操作提示：**

- `加入SmsF预览体验计划`后在线更新（`关于软件`页面开启，`v3.3.0_240305+`适用）
- 手动下载：https://github.com/pppscn/SmsForwarder/actions/workflows/Weekly_Build.yml

--------

## 特别声明:

* 本仓库发布的`SmsForwarder`项目中涉及的任何代码/APK，仅用于测试和学习研究，禁止用于商业用途，不能保证其合法性，准确性，完整性和有效性，请根据情况自行判断。

* 任何用户直接或间接使用或传播`SmsForwarder`的任何代码或APK，无论该等使用是否符合其所在国家或地区，或该等使用或传播发生的国家或地区的法律，`pppscn`和/或代码仓库的任何其他贡献者均不对该等行为产生的任何后果（包括但不限于隐私泄露）负责。

* 如果任何单位或个人认为该项目的代码/APK可能涉嫌侵犯其权利，则应及时通知并提供身份证明，所有权证明，我们将在收到认证文件后删除相关代码/APK。

* 隐私声明： **SmsForwarder 不会收集任何您的隐私数据！！！** APP启动时发送版本信息发送到友盟统计；手动检查新版本时发送版本号用于检查新版本；除此之外，没有任何数据！！！

* 防诈提醒： `SmsForwarder`完全免费开源，请您在 [打赏](https://gitee.com/pp/SmsForwarder/wikis/pages?sort_id=4912193&doc_id=1821427) 前务必确认是否出于自愿？本项目不参与任何刷单返利担保！**请您远离刷单返利陷阱，谨防网络诈骗！**

--------

## 工作流程：

![工作流程](pic/working_principle.png "working_principle.png")

--------

## 界面预览：

![界面预览](pic/screenshots.jpg "screenshots.jpg")

更多截图参见 https://github.com/pppscn/SmsForwarder/wiki

--------

## 下载地址

> ⚠ 首发地址：https://github.com/pppscn/SmsForwarder/releases

> ⚠ 国内镜像：https://gitee.com/pp/SmsForwarder/releases

> ⚠ 网盘下载：https://wws.lanzoui.com/b025yl86h 访问密码：`pppscn`

--------

## 使用文档【新用户必看！】

> ⚠ GitHub Wiki：https://github.com/pppscn/SmsForwarder/wiki

> ⚠ Gitee Wiki：https://gitee.com/pp/SmsForwarder/wikis/pages

![使用流程与问题排查流程](pic/Troubleshooting_Process.png "Troubleshooting_Process.png")

--------

## 反馈与建议：

+ 提交issues 或 pr
+ 加入交流群（群内都是机油互帮互助，禁止发任何与SmsForwarder使用无关的内容）

|                      TG Group                       |
|:---------------------------------------------------:|
|         ![TG Group](pic/tg.png "TG Group")          |
| [+QBZgnL_fxYM0NjE9](https://t.me/+QBZgnL_fxYM0NjE9) |

## 感谢

> [感谢所有赞助本项目的热心网友 --> 打赏名单](https://gitee.com/pp/SmsForwarder/wikis/pages?sort_id=4912193&doc_id=1821427)

> 本项目得到以下项目的支持与帮助，在此表示衷心的感谢！

+ https://github.com/xiaoyuanhost/TranspondSms (项目原型)
+ https://github.com/xuexiangjys/XUI （UI框架）
+ https://github.com/xuexiangjys/XUpdate （在线升级）
+ https://github.com/getActivity/XXPermissions (权限请求框架)
+ https://github.com/mainfunx/frpc_android (内网穿透)
+ https://github.com/gyf-dev/Cactus (保活措施)
+ https://github.com/yanzhenjie/AndServer (HttpServer)
+ https://github.com/jenly1314/Location (Location)
+ https://gitee.com/xuankaicat/kmnkt (socket通信)
+ [<img src="https://resources.jetbrains.com/storage/products/company/brand/logos/jetbrains.svg" alt="GitHub license" style="width：159px; height: 32px" width="159" height="32" />](https://jb.gg/OpenSourceSupport)  (License Certificate for JetBrains All Products Pack)

--------

## 如果您觉得本工具对您有帮助，不妨在右上角点亮一颗小星星，以示鼓励！

<p align="center">
  <a href="https://github.com/pppscn/SmsForwarder/tree/star-history">
    <img alt="Star History Chart" src="https://raw.githubusercontent.com/pppscn/SmsForwarder/refs/heads/star-history/star-history.svg" />
  </a>
</p>

--------

## LICENSE

BSD
