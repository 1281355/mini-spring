package com.rain.zbs.web.mvc;

import java.lang.annotation.*;

/**
 * @author me
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {
    String value();
}
