# Urban
采用kotlin开发，组件化项目，组件之间不直接引用，组件可以独立调试，MVVM模式

isModule=true 开启表明是以module集成开发

1、 alllip 是集合工具类
2、app 是主工程，集成开发时候调起
3、module_image 是图片选择模块
4、module_login 是登录注册相关模块
5、module_common 是业务相关模块




坑：
1、databinding 与kotlin 在定义方法时候@android.databinding.BindingAdapter，用@JvmStatic
2、但是开发过程经常出现 is not static and requires an object to use, retrieved from the DataBindingComponent，最终还是用java 静态方法