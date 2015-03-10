package kirisame.android.toolset.parcel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 丢猫 on 2015/3/8.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface ParcelField {
}
