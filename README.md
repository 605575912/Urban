# Urban
采用kotlin开发，组件化项目，组件之间不直接引用，组件可以独立调试，MVVM模式

1、applib 是公共类库，通常一个跟业务无关的代码集合
2、module_common 是业务相关的集合代码，提供例如：存储登录数据、加载界面、webveiw
3、module_login 目前集成 注册、登录相关的代码，可以单独运行，登录后数据保存在module_common 的业务模块中
