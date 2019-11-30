package client;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@interface Remote {
    /*
     * Indicates that an annotated method calls some methods of the ServerAPI field.
     */
}
