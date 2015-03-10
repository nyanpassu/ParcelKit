package kirisame.android.toolset.parcel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 丢猫 on 2015/3/8.
 */
public class ParcelKit {

    public static final String SUFFIX = "$$ParcelInjector";

    private static final Map<Class<?>, Injector<Object>> INJECTOR_CACHE = new LinkedHashMap<>();

    public static void write(Object target, Parcel dest, int flags) {
        Class<?> targetClass = target.getClass();

        Injector<Object> injector = findInjectorForClass(targetClass);
        if (injector != null) {
            injector.writeParcel(target, dest, flags);
        }
    }

    public static Object getCreator(Class<?> targetClass) {
        Injector<Object> injector = findInjectorForClass(targetClass);
        if (injector != null) {
            return injector.getCreator();
        }
        throw new RuntimeException("unable to create creator for " + targetClass.getName());
    }

    private static Injector<Object> findInjectorForClass(Class<?> cls) {
        Injector<Object> injector = INJECTOR_CACHE.get(cls);
        if (injector != null) {
            return injector;
        }

        String clsName = cls.getName();

        try {
            Class<?> injectorClass = Class.forName(clsName + SUFFIX);

            injector = (Injector<Object>) injectorClass.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        INJECTOR_CACHE.put(cls, injector);
        return injector;
    }

    public interface Injector<T>{
        void writeParcel(T t, Parcel parcel, int flags);
        Parcelable.Creator getCreator();
    }
}
