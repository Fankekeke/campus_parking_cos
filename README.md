### 基于SpringBoot + Vue的校园停车场计费系统.

#### 安装环境

JAVA 环境 

Node.js环境 [https://nodejs.org/en/] 选择14.17

Yarn 打开cmd， 输入npm install -g yarn !!!必须安装完毕nodejs

Mysql 数据库 [https://blog.csdn.net/qq_40303031/article/details/88935262] 一定要把账户和密码记住

redis

Idea 编译器 [https://blog.csdn.net/weixin_44505194/article/details/104452880]

WebStorm OR VScode 编译器 [https://www.jianshu.com/p/d63b5bae9dff]

#### 采用技术及功能

后端：SpringBoot、MybatisPlus、MySQL、Redis、
前端：Vue、Apex、Antd、Axios

平台前端：vue(框架) + vuex(全局缓存) + rue-router(路由) + axios(请求插件) + apex(图表)  + antd-ui(ui组件)

平台后台：springboot(框架) + redis(缓存中间件) + shiro(权限中间件) + mybatisplus(orm) + restful风格接口 + mysql(数据库)

开发环境：windows10 or windows7 ， vscode or webstorm ， idea + lambok


#### 前台启动方式
安装所需文件 yarn install 
运行 yarn run dev

#### 默认后台账户密码
[管理员]
admin
1234qwer

[员工]
STF-fank
1234qwer

[用户]
fank
1234qwer

###### 管理员：
系统公告、会员管理、用户消息、订单管理、车位预约、价格规则、车位管理、车位状态、访客管理、车辆管理、数据统计、月份统计、车位实时状态、会员订单、员工管理、教师管理、学生管理、违规记录。

###### 用户/学生/教师：
账户注册登录、密码修改、个人信息、我的车辆、车位预约、我的订单、我的消息、在线缴费、我的会员记录，会员购买、路线导航。

###### 员工：
账户注册登录、密码修改、个人信息 、订单管理 、车位预约 、车位状态。

#### 项目截图
暂无

|  |  |
|---------------------|---------------------|
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/8e11c32e-7908-4c53-9fff-d1cfe9bbf22c.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/3245bf23-d85a-49a8-a91e-1375a5914415.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/7ced6c6e-1d1e-4217-8129-ceaed3780884.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1374c969-7541-42f2-8ab7-258b769bfbe3.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/5ba2228b-68d5-41ad-85ee-0e74805401cd.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/875d85e9-b73f-496b-8dc8-861bc5c0a090.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/fee69e26-db0a-478b-8e8d-6ed319b07571.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/554fe99d-831e-4622-98ca-003e691e7f1d.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/e1504a68-0091-47d9-8883-bb258db29c74.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/234d5534-3367-4ed6-a24e-dba534961e88.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/d036cea2-d2b6-4fdc-8709-a3b1c9606391.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/98faacd2-58ac-4a91-8035-9d6a05839ed5.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/cc3ebaf6-e68f-4220-b4cd-7d89c8f7ef16.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/48f41391-1186-4303-be2a-37a16e7cf8ce.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/c005dd2a-5c05-4bda-a889-008259384129.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/028a0fc7-bf35-443a-9c3c-c9b610657c9d.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/bf72c789-3cc4-4e6f-839f-c1f6b02ddd19.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/27c2201e-68e3-493a-b04f-429f81299e88.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/410267c8-2831-4246-973c-92e20ce4e78a.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/9feb0a4b-1005-484a-8709-8a89765c2b9c.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/9314f815-da63-4f5c-98bd-d4aeb75a2647.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/work/936e9baf53eb9a217af4f89c616dc19.png) |

#### 演示视频

暂无

#### 获取方式

Email: fan1ke2ke@gmail.com

WeChat: `Storm_Berserker`

`附带部署与讲解服务，因为要恰饭资源非免费，伸手党勿扰，谢谢理解😭`

> 1.项目纯原创，不做二手贩子 2.一次购买终身有效 3.项目讲解持续到答辩结束 4.非常负责的答辩指导 5.黑奴价格

> 项目部署调试不好包退！功能逻辑没讲明白包退！

#### 其它资源

[2025年-答辩顺利通过-客户评价🍜](https://berserker287.github.io/2025/06/18/2025%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2024年-答辩顺利通过-客户评价👻](https://berserker287.github.io/2024/06/06/2024%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2023年-答辩顺利通过-客户评价🐢](https://berserker287.github.io/2023/06/14/2023%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2022年-答辩通过率100%-客户评价🐣](https://berserker287.github.io/2022/05/25/%E9%A1%B9%E7%9B%AE%E4%BA%A4%E6%98%93%E8%AE%B0%E5%BD%95/)

[毕业答辩导师提问的高频问题](https://berserker287.github.io/2023/06/13/%E6%AF%95%E4%B8%9A%E7%AD%94%E8%BE%A9%E5%AF%BC%E5%B8%88%E6%8F%90%E9%97%AE%E7%9A%84%E9%AB%98%E9%A2%91%E9%97%AE%E9%A2%98/)

[50个高频答辩问题-技术篇](https://berserker287.github.io/2023/06/13/50%E4%B8%AA%E9%AB%98%E9%A2%91%E7%AD%94%E8%BE%A9%E9%97%AE%E9%A2%98-%E6%8A%80%E6%9C%AF%E7%AF%87/)

[计算机毕设答辩时都会问到哪些问题？](https://www.zhihu.com/question/31020988)

[计算机专业毕业答辩小tips](https://zhuanlan.zhihu.com/p/145911029)

#### 接JAVAWEB毕设，纯原创，价格公道，诚信第一

`网站建设、小程序、H5、APP、各种系统 选题+开题报告+任务书+程序定制+安装调试+项目讲解+论文+答辩PPT`

More info: [悲伤的橘子树](https://berserker287.github.io/)
