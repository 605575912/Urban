package com.squareup.lib.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

public class ReflectHelper implements IProguard.ProtectClassAndMembers {
    private static final String TAG = "ReflectHelper";

    /*
     * 调用该对象所有可调用的公有方法,包括父类方法
     * 参数：obj 调用者对象
     *       methodname 调用的方法名，与obj合在一起即为 obj.methodname
     *       types      调用方法的参数类型
     *       values	调用方法的参数值
     * 返回     methodname所返回的对象
     */
    public static Object callMethod(Object obj, String methodname, Class<?> types[], Object values[]) {
        // 注：数组类型为:基本类型+[].class,如String[]写成 new Class<?>[]{String[].class}
        if (obj == null) {
            return null;
        }
        Class<?> classz = obj.getClass();
        Method method = null;
        Object retValue = null;
        try {
            method = classz.getMethod(methodname, types);
            retValue = method.invoke(obj, values);
        } catch (NoSuchMethodException ex) {
//	    AspLog.e(TAG, "callMethod "+methodname+" reason=" +  ex +" "+ ex.getMessage());
        } catch (Exception ex) {
        }
        return retValue;
    }

    /*
     * 调用该对象所声明的方法，不包括父类方法，可以调用到该方法的私有方法
     * 参数：obj 调用者对象
     *       methodname 调用的方法名，与obj合在一起即为 obj.methodname
     *       types      调用方法的参数类型，
     *       		注：数组类型为:基本类型+[].class,如String[]写成 new Class<?>[]{String[].class}，
     *       		int 类型是为int.class
     *       values	调用方法的参数值
     * 返回     methodname所返回的对象
     */
    public static Object callDeclaredMethod(Object obj, String methodname, Class<?> types[], Object values[]) {
        if (obj == null) {
            return null;
        }
        Class<?> classz = obj.getClass();
        Method method = null;
        Object retValue = null;
        try {
            method = classz.getDeclaredMethod(methodname, types);
            method.setAccessible(true);// 设置安全检查，设为true使得可以访问私有方法
            retValue = method.invoke(obj, values);
        } catch (NoSuchMethodException ex) {
//	    AspLog.e(TAG, "callDeclaredMethod "+methodname+" reason=" +  ex +" "+ ex.getMessage());
        } catch (Exception ex) {
        }
        return retValue;
    }

    public static Object callDeclaredMethod(Object obj, String declaredclassname,
                                            String methodname, Class<?> types[], Object values[]) {
        Class<?> classz = null;
        Method method = null;
        Object retValue = null;
        try {
            classz = Class.forName(declaredclassname);
            method = classz.getDeclaredMethod(methodname, types);
            method.setAccessible(true);// 设置安全检查，设为true使得可以访问私有方法
            retValue = method.invoke(obj, values);
        } catch (NoSuchMethodException ex) {
//	    AspLog.e(TAG, "callDeclaredMethod2 "+declaredclassname +":"+methodname+" reason=" +  ex +" "+ ex.getMessage());
        } catch (Exception ex) {
        }
        return retValue;
    }

    /*
     * 调用该类的静态方法，包括静态方法
     * 参数：classz 	类类对象
     *       methodname 调用的方法名，与obj合在一起即为 obj.methodname
     *       types      调用方法的参数类型，
     *       		注：数组类型为:基本类型+[].class,如String[]写成 new Class<?>[]{String[].class}，
     *       		int 类型是为int.class
     *       values	调用方法的参数值
     * 返回     methodname所返回的对象
     */
    public static Object callStaticMethod(Class<?> classz, String methodname, Class<?> types[], Object values[]) {
        Method method = null;
        Object retValue = null;
        try {
            method = classz.getDeclaredMethod(methodname, types);
            method.setAccessible(true);// 设置安全检查，设为true使得可以访问私有方法
            retValue = method.invoke(null, values);
        } catch (NoSuchMethodException ex) {
//	    AspLog.e(TAG, "callStaticMethod "+ methodname+","+ex +" "+ ex.getMessage());
        } catch (Exception ex) {
        }
        return retValue;
    }

    /*
     * 调用该类的静态方法，包括静态方法
     * 参数：className 	类名
     *       methodname 调用的方法名，与obj合在一起即为 obj.methodname
     *       types      调用方法的参数类型，
     *       		注：数组类型为:基本类型+[].class,如String[]写成 new Class<?>[]{String[].class}，
     *       		int 类型是为int.class
     *       values	调用方法的参数值
     * 返回     methodname所返回的对象
     */
    public static Object callStaticMethod(String className, String methodname, Class<?> types[], Object values[]) {
        Class<?> classz;
        try {
            classz = Class.forName(className);
            return callStaticMethod(classz, methodname, types, values);
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    public static <T> T callMethod(Object receiver, Method method, Object... args) {
        try {
            return (T) method.invoke(receiver, args);
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        } catch (Exception e) {
        }
        return null;
    }

    public static final Method getDeclaredMethod(Class<?> clazz, String method, Class<?>... parameterTypes) {
        Method met = null;
        try {
            met = clazz.getDeclaredMethod(method, parameterTypes);
            if (!met.isAccessible()) {
                met.setAccessible(true);
            }
        } catch (SecurityException e) {
        } catch (NoSuchMethodException e) {
        } catch (Exception e) {
        }
        return met;
    }

    /*
     * 获得对象的公有数据成员,包括父类
     * 参数： obj 	对象
     *       fieldname  公有成员变量名
     * 返回     成员对象
     */
    public static Object getFieldValue(Object obj, String fieldname) {
        if (obj == null) {
            return null;
        }
        Class<?> classz = obj.getClass();
        Field field = null;
        Object retValue = null;
        try {
            field = classz.getField(fieldname);
            retValue = field.get(obj);
        } catch (NoSuchFieldException ex) {
        } catch (Exception ex) {
        }
        return retValue;
    }

    /*
     * 获得对象所在类的数据成员，包括私有成员
     * 参数： obj 	对象
     *       fieldname  成员变量名
     * 返回     成员对象
     */
    public static Object getDeclaredFieldValue(Object obj, String fieldname) {
        if (obj == null) {
            return null;
        }
        Class<?> classz = obj.getClass();
        Field field = null;
        Object retValue = null;
        try {
            field = classz.getDeclaredField(fieldname);
            field.setAccessible(true);
            retValue = field.get(obj);
        } catch (NoSuchFieldException ex) {
        } catch (Exception ex) {
        }
        return retValue;
    }

    public static Object getDeclaredFieldValue(Object obj,
                                               String declaredclassname, String fieldname) {
        Class<?> classz = null;
        Field field = null;
        Object retValue = null;
        try {
            classz = Class.forName(declaredclassname);
            field = classz.getDeclaredField(fieldname);
            field.setAccessible(true);
            retValue = field.get(obj);
        } catch (NoSuchFieldException ex) {
        } catch (ClassNotFoundException e) {
        } catch (Exception ex) {
        }
        return retValue;
    }

    /*
     * 获得类的静态数据成员，包括私有成员
     * 参数： classz 	类类对象
     *       fieldname  成员变量名
     * 返回     成员对象
     */

    public static Object getStaticFieldValue(Class<?> classz, String fieldname) {
        Field field = null;
        Object retValue = null;
        try {
            field = classz.getDeclaredField(fieldname);
            field.setAccessible(true);
            retValue = field.get(null);
        } catch (NoSuchFieldException ex) {
        } catch (Exception ex) {
        }
        return retValue;
    }

    /*
     * 获得类的静态数据成员，包括私有成员
     * 参数： classname 	类名称
     *       fieldname  成员变量名
     * 返回     成员对象
     */
    public static Object getStaticFieldValue(String className, String fieldname) {
        try {
            Class<?> classz = Class.forName(className);
            return getStaticFieldValue(classz, fieldname);
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    public static Field getField(Class<?> clazz, String name) {
        try {
            Field f = clazz.getField(name);
            if (!f.isAccessible()) {
                f.setAccessible(true);
            }
            return f;
        } catch (NoSuchFieldException e) {
        } catch (Exception e) {
        }
        return null;
    }

    public static Field getDeclaredField(Class<?> clazz, String name) {
        try {
            Field f = clazz.getDeclaredField(name);
            if (!f.isAccessible()) {
                f.setAccessible(true);
            }
            return f;
        } catch (NoSuchFieldException e) {
        } catch (Exception e) {
        }
        return null;
    }

    public static <T> T getFieldValue(Object obj, Field field) {
        try {
            return (T) field.get(obj);
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        } catch (Exception e) {
        }
        return null;
    }

    public static boolean getFieldValue(Object obj, Field field, boolean defaultVal) {
        try {
            return field.getBoolean(obj);
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        } catch (Exception e) {
        }
        return defaultVal;
    }

    public static int getFieldValue(Object obj, Field field, int defaultVal) {
        try {
            return field.getInt(obj);
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        } catch (Exception e) {
        }
        return defaultVal;
    }

    public static boolean setFieldValue(Object obj, Field field, Object value) {
        try {
            field.set(obj, value);
            return true;
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
        return false;
    }

    /*
     * 赋值对象的公有数据成员
     * 参数： classname 	类名称
     *       fieldname  成员变量名
     *       value	赋值
     * 返回     成员对象
     */
    public static void setFieldValue(Object obj, String fieldname, Object value) {

        Class<?> classz = null;// obj.getClass();
        try {
            classz = obj.getClass();
            Field field = classz.getField(fieldname);
            field.set(obj, value);
        } catch (NoSuchFieldException ex) {
        } catch (Exception ex) {
        }
    }

    /*
     * 赋值对象的数据成员,包括私有
     * 参数： classname 	类名称
     *       fieldname  成员变量名
     *       value	赋值
     * 返回     成员对象
     */
    public static void setDeclaredFieldValue(Object obj, String fieldname, Object value) {
        Class<?> classz = null;// obj.getClass();
        try {
            classz = obj.getClass();
            Field field = classz.getDeclaredField(fieldname);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException ex) {
        } catch (Exception ex) {
        }
    }

    public static void setDeclaredFieldValue(Object obj, String declaredclassname
            , String fieldname, Object value) {
        try {
            Class<?> classz;
            classz = Class.forName(declaredclassname);
            Field field = classz.getDeclaredField(fieldname);
            field.setAccessible(true);
            field.set(obj, value);


        } catch (ClassNotFoundException e) {
        } catch (NoSuchFieldException ex) {
        } catch (Exception ex) {
        }
    }

    public static Vector<String> getDeclaredFieldNames(Object obj) {
        Vector<String> fieldnames = new Vector<String>();
        Class<?> classz = obj.getClass();
        Field fields[] = classz.getFields();
        for (Field field : fields)
            fieldnames.add(field.getName());
        return fieldnames;
//	Field field = null;
//	Object retValue = null;
//	try {
//	    field = classz.getDeclaredField(fieldname);
//	    field.setAccessible(true);
//	    retValue = field.get(obj);
//	} catch (NoSuchFieldException ex) {
//	    AspLog.e(TAG, "getDeclaredFieldValue "+ ex+" "+ ex.getMessage());
//	} catch (Exception ex) {
//	    AspLog.e(TAG, "getDeclaredFieldValue "+ ex+" "+ ex.getMessage());
//	}
    }

    public static void setStaticFieldValue(Class<?> classz, String fieldname, Object value) {
        try {
            Field field = classz.getDeclaredField(fieldname);
            field.setAccessible(true);
            field.set(null, value);
        } catch (NoSuchFieldException ex) {
        } catch (Exception ex) {
        }
    }
//    /***
//     * 设置父类中私有变量中私有变量（2层）:lhy注此方法不知是谁定义的，无此必要.
//     * @param classz
//     * @param obj
//     * @param fieldname1
//     * @param fieldname2
//     * @param value
//     */
//    public static void setDeclaredFieldValue(Class<?> classz,Object obj,String fieldname1,
//	    String fieldname2,  Object value) {
////	Class<?> classz = f.getType();
//	
//	try {
//	    Field f = classz.getDeclaredField(fieldname1);
//	    f.setAccessible(true);
//	    Object o = f.get(obj);
//	    f = f.getType().getDeclaredField(fieldname2);
////	    ReflectHelper.setDeclaredFieldValue(this, f, value);
////	   
//	    f.setAccessible(true);
////	    Drawable drawable = getResources().getDrawable(R.drawable.hotsaleshop_back3);//(Drawable) f.get(o);
//	    f.set(o, value);
//	} catch (NoSuchFieldException ex) {
//	    AspLog.e(TAG, "setDeclaredFieldValue "+ ex+" "+ ex.getMessage());
//	} catch (Exception ex) {
//	    AspLog.e(TAG, "setDeclaredFieldValue "+ ex+" "+ ex.getMessage());
//	}
//    }

    /**
     * 给静态成员变量赋值
     *
     * @param className 类名
     * @param fieldname 静态变量名
     * @param value     赋值
     */
    public static void setStaticFieldValue(String className, String fieldname, Object value) {
        try {
            Class<?> classz = Class.forName(className);
            setStaticFieldValue(classz, fieldname, value);
        } catch (ClassNotFoundException ex) {
        }
    }

    /**
     * 生成指定类的实例
     *
     * @param classz    类
     * @param argsClass 构造参数类型
     * @param objects   构造参数值
     * @return 实例
     */
    public static Object newInstance(Class<?> classz, Class<?> argsClass[], Object objects[]) {
        Object retObj = null;
        try {
            Constructor<?> constructor = classz.getDeclaredConstructor(argsClass);// 定位构造方法
            constructor.setAccessible(true);// 设置安全检查，能够访问私有构造函数
            retObj = constructor.newInstance(objects);
        } catch (NoSuchMethodException ex) {
//	    AspLog.e(TAG, "newInstance "+ ex+" "+ ex.getMessage());
        } catch (Exception ex) {
        }
        return retObj;
    }

    /**
     * 生成类的实例
     *
     * @param classz 类的类类型
     * @return 实例
     */
    public static Object newInstance(Class<?> classz) {
        try {
            Constructor<?> constructor = classz.getDeclaredConstructor();
            constructor.setAccessible(true); //2014.6.24 支持构造私构造函数的类
            return constructor.newInstance();
//	    return classz.newInstance();
        } catch (IllegalAccessException e) {
        } catch (InstantiationException e) {
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 生成类有实例数组
     */
    public static Object newInstance(Class<?> classz, int length) {
        return Array.newInstance(classz, length);
    }

    public static Object newInstance(String className, int length) {
        Class<?> classz;
        try {
            classz = Class.forName(className);
            return newInstance(classz, length);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * 生成类实例
     *
     * @param className 类名
     * @param argsClass 构造参数类型
     * @param objects   构造参数
     * @return 类实例
     */
    public static Object newInstance(String className, Class<?> argsClass[], Object objects[]) {
        try {
            Class<?> classz = Class.forName(className);
            return newInstance(classz, argsClass, objects);
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    /**
     * 构造类的实例
     *
     * @param className 类名
     * @return 类实例
     */
    public static Object newInstance(String className) {
        try {
            Class<?> classz = Class.forName(className);
            return newInstance(classz);
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    /**
     * 判断类是否支持指定的方法
     *
     * @param className  类名
     * @param methodName 方法名
     * @param argClass   方法的参数类型
     * @return
     */
    public static boolean methodSupported(String className, String methodName, Class<?> argClass[]) {
        try {
            Class<?> classz = Class.forName(className);
            try {
                if (methodName.equals("<init>")) {
                    Constructor<?> constructor = classz.getDeclaredConstructor(argClass);// 定位构造方法
                    return constructor != null;
                } else {
                    classz.getDeclaredMethod(methodName, argClass);
                }
            } catch (SecurityException e) {
//		AspLog.e(TAG, "methodSupported "+ e+" "+ e.getMessage());
                return false;
            } catch (NoSuchMethodException e) {
                try {
                    classz.getMethod(methodName, argClass);
                    return true;
                } catch (Exception e1) {
                }
//		AspLog.e(TAG, "methodSupported "+ e+" "+ e.getMessage());
                return false;
            }
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * 判断obj是否支持指定的方法
     *
     * @param obj
     * @param methodName
     * @param argClass
     * @return
     */
    public static boolean methodSupported(Object obj, String methodName, Class<?> argClass[]) {
        Class<?> classz = obj.getClass();
        return methodSupported(classz.getName(), methodName, argClass);
    }

    /**
     * 检查指定的类是否存在
     *
     * @param className
     * @return
     */
    public static boolean classSupported(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * 判断一个类是否从指定的类派生的
     *
     * @param className：需要检查的类
     * @param baseclazz：基类
     * @return
     */
    public static boolean classDerivedFrom(String className, Class<?> baseclazz) {
        try {
            Class<?> clazz = Class.forName(className);
            boolean flag = false;
            do {
                if (clazz.equals(baseclazz))
                    return true;
                clazz = clazz.getSuperclass();
                if (clazz == null)
                    return false;
            } while (!(flag = clazz.equals(baseclazz)));

            return flag;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * 判断一个类是否从指定的类派生的
     *
     * @param className：需要检查的类
     * @return
     */
    public static boolean classDerivedFrom(String className, String strbaseclazz) {
        try {
            Class<?> baseclazz = Class.forName(strbaseclazz);
            return classDerivedFrom(className, baseclazz);
        } catch (Exception e) {
        }
        return false;
    }
}
