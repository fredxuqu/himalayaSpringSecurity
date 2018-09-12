package com.himalaya.auth.annotation;

import java.lang.annotation.*;

/**
 * 用于标注某个类，表示其线程不安全，不可以在多线程场景下使用
 *
 * @see ThreadSafe
 *
 * @author VK.Gao
 * @date 2017/03/03
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface NotThreadSafe {
}
