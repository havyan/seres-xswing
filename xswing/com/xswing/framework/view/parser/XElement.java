/**
 * 
 */
package com.xswing.framework.view.parser;

import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author HWYan
 *
 */
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface XElement {
	
	String[] names();
	
	String parent() default "UNDEFINED";

}
