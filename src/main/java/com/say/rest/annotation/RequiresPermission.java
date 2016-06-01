package com.say.rest.annotation;

import java.lang.annotation.*;

/**
 * If a controller method is annotated with @RequiresPermission,
 * then it will be only further processed if a "permissionCode" is present in the HttpRequest.
 * The value associated to "permissionCode" must match the permission code if the applications properties file.
 *
 * @author philipp
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiresPermission {

}