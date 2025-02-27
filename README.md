# dingdong-helper
叮咚自动下单 并发调用接口方式 多人实战反馈10秒以内成功 自动将购物车能买的商品全部下单 只需自行编辑购物车和最后支付即可

当前时间2022-04-19根据自身和身边朋友反馈正常下单，但不能保证每一个人都能成功下单，如果此程序完全不可用，我会更新到这个位置

这个项目看上去要接近尾声了，我所在的地方团菜很容易，而且比叮咚便宜的多，叮咚越来越贵了，商品种类也越来越少，背后的阴谋论不去推敲，更新和回复不会很频繁了，如果大家对项目有什么重大改进的话可以直接fork此项目进行修改并留言在issues中推荐给更多有需要的人用，感谢大家的支持

# 重大更新 重大更新 重大更新 当前小程序版本：2.83.0

完全还原签名算法（请使用jdk1.8，高版本不带此功能），保证和真机一模一样的请求参数，之前请求中有签名字段，但是后端无验证，为了避免留下把柄，展现了一把我深厚的js功底，现在请求和小程序一模一样，没有任何参数不同


# 关于封号
issues中今天有不少不能下单的，根据我自身和朋友的反馈并没有出现，推荐大家还是用时间触发5点59和8点29执行2分钟，没抢到也不要再执行了，对于被风控的兄弟说声抱歉。

害怕封号者慎用

避免以下几点
1. 并发程序不要执行超过2分钟（时间触发等待时间不算）
2. 低峰期不要使用并发程序，特别是在购物车无商品或无配送的情况下，如果能下单还好，如果不能下单在低峰期这些并发请求全部会被叮咚服务器处理非常容易被风控，高峰期是限流策略直接把请求给忽略了不处理。
3. 一天不要下超过两单，有时候早上第三单就不让支付了，下午又可以了，也有人反馈第二单就不让支付了，隔天再试一下就可以确认是不是因为多单问题
4. 慎用哨兵模式，执行时间不宜超过3小时，并且尽量加大请求和轮询间隔

尝试方案
1. 过一段时间再试
2. 重新登录
3. 更换ip，家庭宽带重启猫（非自己买的路由器，运营商送的那个）或使用手机热点移动网络
4. 根据issues中反馈，有人修改device_id和open_id就成功了，这两个参数后端不校验
5. 拨打客服电话（按以往其他平台的经验，底气要十足，就说不知道）
6. 换号

# 特别强调 注意事项

1. 此程序只用来帮助在上海急需买菜的程序猿，请勿商用，issues中非技术问题本人不参与也不会阻止大家讨论
2. 叮咚的策略是6点和8点30更新当天配送时间，全天都有可能会上架货品，所以每天最佳抢菜时间是6点（当天第一轮允许下单），如果6点-8点30之间一直能配送那么8点30不会有任何变化，8点30主要是更新配送时间，只有当8点30之前无法配送的时候（当天第二轮允许下单）才需要在这个时间抢配送额度。总结6点抢库存和配送，8点30只抢配送
3. 不要删除Application中的保护线程，此段代码控制程序并发执行时2分钟未下单自动终止，避免对叮咚服务器造成压力，也避免封号
4. 接口如果出现405状态有以3种可能 1.偶发，无需处理 2.不要长时间运行程序，参考上面的第2点  3.一个账号下单数有时会有限制 参考上面的第3点
5. 根据反馈有少部分人的站点是假库存，可能是怕大家闹事，开放购买之前能看到购物车里有，但是根本就不可能买到，第一秒下单瞬间很多东西就没了，我也是，几百块的购物车最后下单几十块，我同时用app人工操作了购物车确实是没货了，不是程序问题。
6. 日期20220412我这个站点是6点开始陆续上东西，6点之前购物车没有东西可以买


## 环境
非Java开发很多 加一点新手教学 老手直接忽略

1. intellij idea 新手教学：下载路径 https://www.jetbrains.com/idea/download/#section=windows 下载好使用30天试用版即可 祈祷30天内能回归正常生活
2. jdk 8 新手教学：下载路径 https://www.oracle.com/java/technologies/downloads/#java8-windows   搜索Java SE Development Kit 8u321找到合适你系统和cpu的版本 一键安装
3. maven 新手教学：可用idea内置maven 什么都不用操作
4. 打开idea - (file 如果是第一次安装跳过此项直接能看到open) - open - 项目文件夹 - 等待右下角maven构建进度条
5. 新手不要自己新建一个项目在往项目里加文件 直接使用方式4 等待进度条结束即可 如果不小心叉掉了进度条，删掉项目，重新拉，重复步骤4（有其他方法，但新手很难理解就不推荐了）

对java完全不熟悉的来个快速入门

``` java
public class UserConfig { //UserConfig是类名 下面文档中说的执行Application就是类名
  //一个类只能有一个main方法 也就是启动方法 在idea中右键此文件Run就是执行这个类  如果该类中没有main方法则没有Run这个选项 项目中的Api类就没有main方法
  public static void main(String[] args) {
  }
}

```

## 步骤

1. 通过Charles（我截图和教程是Charles，用Charles会更方便对比）等抓包工具抓取微信中叮咚买菜小程序中的接口信息中的用户信息配置到UserConfig.java中
2. 运行UserConfig.java获取默认收货地址城市id、站点id、地址id并填入对应变量中，再执行一次确认配置正确，会有日志输出
3. 将需要买的菜自行通过APP放入购物车
4. 5-8执行模式根据使用需求自选
5. 测试模式（单线程）: 执行ApplicationTest低峰期单次下单
6. 人工执行（多线程并发）：设置Application中的policy变量1并运行，如果当前购车有商品并有配送时间则会在10秒内执行成功
7. 时间触发（多线程并发）：设置Application中的policy变量2或3并运行，当系统时间到达5点59分30秒或8点29分30秒自动执行，如果购买成功将播放一分钟的提示音（请确保电脑外放无静音）
8. 哨兵模式（单线程）：设置Sentinel中最小下单金额并运行，当金额超过设置金额时尝试下单，请注意此模式下不并发，所以在6点和8点30左右的高峰期可能会存在长时间无法正常下单，高峰期买菜使用6或者7策略，如果购买成功将播放一分钟的提示音（请确保电脑外放无静音） 有不少反馈长时间运行还是会可能出现封号等问题，我本人昨天运行几个小时候也出现了，重新登录即可，加长间隔后跑了四五个小时未出现
9. 等待程序结束，如果成功下单请在5分钟内付款，否则订单会取消，用手机打开叮咚买菜app-我的订单-待支付-点击支付
10. 每次抢之前跑一下UserConfig中的main方法确认登录状态是否准确，如果状态不对则重新抓包更新UserConfig数据
11. 如果想用自己的号帮别人下单，只需要手动在APP中设置一下默认地址再运行UserConfig 获取到addressId和stationId进行替换
12. 如果想测试下单，上海是比较难测试的，可以把默认地址选择杭州进行测试
13. [微信公众号推送](http://dd.100vs.com/) ，登录，然后生成token，填写在NoticeUtil中的token上，用来下单成功推送消息

## 程序自动结束的几个条件

1. 购物车无可购买商品（时间触发和哨兵模式会持续执行）
2. 下单成功
3. 用户登录信息失效

## 快捷抓包

小程序已经有PC版了，手机进入小程序右上角3个点->在电脑中打开即可，送上一个参考文章https://blog.csdn.net/z2181745/article/details/123002569 比手机抓包方便太多。

注意事项
1. Charles安装和配置好后再打开或重新打开电脑端叮咚小程序，如果在之前打开可能会抓不到
2. 如果使用电脑端小程序抓包，则不要去碰手机微信里的叮咚小程序，否则session会失效，反过来也一样，其他操作在app上操作不影响，但不能同时在两个端的小程序操作，互斥
3. 确保电脑和手机没有打开vpn（公司的或者梯子）

## 设备分工（同上面的步骤，把设备关系说清楚）

#### 手机&电脑

1. 打开微信小程序中的叮咚买菜，通过电脑抓包软件抓取信息填入代码中，在token不失效的情况下可以一直使用
2. 也可以使用电脑端小程序进行抓包会比手机方便很多

#### 手机

1. 在开放购买前选择商品到购物车
2. 等待下单成功后去待支付订单页面支付

#### 电脑

1. 运行UserConfig获取addressId并填入变量addressId
2. 开放购买前1分钟运行Application，前30秒左右会获取基本信息直到看到提交订单失败的信息则代表基本信息获取完毕等待开放时间一到即可成功

## 思路

虽然我家吃的很多，但是时间长了也受不了这几天每天早上起来抢菜，手都点抽经了都买不到，看着购物车里的菜越来越少心急如焚，作为程序员只能靠自己的双手了，吃完午饭开干，晚上6点成功下单
1. 抓app的包没抓到
2. 抓小程序的包可以，但是小程序无法做登录，拿不到open id，所以只能通过自行抓包解决。另看到请求参数中有一些签名字段，心想麻烦哟
3. 准备研究如何签名，解包微信小程序，初步研究签名相关代码，搞不定就去研究app hook，但那耗费精力太大，留着当后手
4. 先写一个获取地址的请求，发现那几个看着像签名的参数可以不用传，省了一大笔精力，应该一开始就用Charles的breakpoint删除参数再repeat尝试无签名是否可访问，被唬住了，早知道就可以省略步骤3
5. 梳理下单需要的参数和步骤，数据量非常庞大，眼睛都看晕了，需要细心
6. 看到下单成功很开心，这就是乐趣

最后希望疫情早日结束大家伙都能吃上饭

## 更新记录

### 2022.04.11
1. 新增自动勾选购物车
2. 优化请求量过大和持续时间过长被网关拦截提示
3. 执行UserConfig时新增站点信息确认，如站点信息错误将导致购物车在手机上看有货程序执行无货或无法下单

### 2022.04.12
1. 叮咚更新了异常返回数据结构，修改异常日志输出
2. 修复无法使用优惠券的问题
3. 修复明明显示有配送信息但下单时报该时间段不能配送的问题

### 2022.04.13
1. 新增平常时间段哨兵模式（长间隔单线程），设置最小下单金额，成功下单后会播放一分钟的铃声，请将电脑音量打开到合适的音量
2. 新增5点59和8点29两个时间触发程序（并发），如需使用 请设置Application中的policy变量，成功下单后会播放一分钟的铃声，请将电脑音量打开到合适的音量
3. 新增并发时保护程序，默认并发执行2分钟，避免封号和对叮咚服务器造成的压力

### 2022.04.14
1. 提交订单的间隔时间拉长后下单效率明显降低，30秒才成功，改回原配置

### 2022.04.15
1. 完全还原签名算法（请使用jdk1.8，高版本不带此功能），保证和真机一模一样的请求参数，之前请求中有签名字段，但是后端无验证，为了避免留下把柄，展现了一把我深厚的js功底，现在请求和小程序一模一样，没有任何参数不同

## 抓包截图 将你的信息填入

这个图有时候会挂，直接从项目里面看也一样，就是路径image/headers.jpeg 和 body.jpeg  对应到UserConfig中的headers和body方法里的参数
![请求头信息](https://github.com/JannsenYang/dingdong-helper/blob/05cc65034b062d3a7844ec706e7876f8e5a57586/image/headers.jpg)
![请求体信息](https://github.com/JannsenYang/dingdong-helper/blob/0433cc7def733820d734f48dec6e47fc0f2d89c8/image/body.jpg)

## 20220410实战记录

用了的全部秒抢，我自己傻逼了，为了提交github，收货地址id在运行的时候忘记填了，跑了几分钟才后知后觉，随即补上了失败时的返回信息。
![实战记录1](https://github.com/JannsenYang/dingdong-helper/blob/3f1847b6f5c363168de733380d9f3cb02a64b8a6/image/20220410-1.png)
![实战记录2](https://github.com/JannsenYang/dingdong-helper/blob/f6e20d377aa482063732a5be614e3dae3d4c5091/image/20220410-2.png)



### 版权说明

**本项目为 GPL3.0 协议，请所有进行二次开发的开发者遵守 GPL3.0协议，并且不得将代码用于商用。**
