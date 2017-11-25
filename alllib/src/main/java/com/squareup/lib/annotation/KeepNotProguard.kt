package com.squareup.lib.annotation

/**
 * Created by liangzhenxiong on 2017/10/15.
 */

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * 这些注解不能被混淆
 *
 * @author lzx
 */
@Retention(RetentionPolicy.CLASS)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FIELD)
annotation class KeepNotProguard
