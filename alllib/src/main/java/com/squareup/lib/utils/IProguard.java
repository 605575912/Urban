package com.squareup.lib.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface IProguard {
    /**
     * 用于保护指定类的成员不能被混淆，所有JSON数据类
     *
     * @author lhy
     */
    public static interface ProtectMembers {
    }

    /**
     * 保护类名及成员，主要保护含native方法、实现了Android API接口的类
     *
     * @author lhy
     */
    public static interface ProtectClassAndMembers {
    }

    /**
     * 保护类名及构造函数
     *
     * @author lhy
     */
    public static interface ProtectClassAndConstruct {
    }

    /**
     * 用于保护指定类名不能被混淆
     *
     * @author lhy
     */
    public static interface ProtectClass {
    }

    /**
     * 用于保护构造函数不能被混淆
     *
     * @author lhy
     */
    public static interface ProtectConstructs {
    }


}
