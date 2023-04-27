package com.pidan.common;

/**
 * 基于ThreadLocal 封装工具类。用于保存和获取当前登录用户
 *
 * @author 黄大头
 * @date 2023年04月26日 20:24
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    //作用域 是某一个线程内
    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
