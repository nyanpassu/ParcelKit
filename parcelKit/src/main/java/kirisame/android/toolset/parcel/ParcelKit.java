package kirisame.android.toolset.parcel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 丢猫 on 2015/3/8.
 */
public class ParcelKit {

    public static final String DELEGATE_SUFFIX = "$$ParcelDelegate";

    public static final String INJECTOR_SUFFIX = "$$ParcelInjector";

    private static final Map<Class<?>, Delegate<Object>> DELEGATE_CACHE = new LinkedHashMap<>();

    private static final Map<Class<?>, Injector<Object>> INJECTOR_CACHE = new LinkedHashMap<>();

    public static Parcelable toParcelable(Object target) {
        Class<?> targetClass = target.getClass();

        Delegate<Object> delegate = findDelegateForClass(targetClass);
        if (delegate != null) {
            delegate.delegate(target);
        }

        return delegate;
    }

    public static Object fromParcelable(Parcelable parcelable) {
        if (parcelable instanceof Delegate) {
            return ((Delegate) parcelable).getClient();
        } else {
            //TODO throw exception or return null?
            return null;
        }
    }

    public static void fromParcelable(Object object, Parcelable parcelable) {
        if (parcelable instanceof Delegate) {
            ((Delegate) parcelable).injectClient(object);
        }
        //TODO throw exception?
    }

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

    private static Delegate<Object> findDelegateForClass(Class<?> cls) {
        Delegate<Object> delegate = DELEGATE_CACHE.get(cls);
        if (delegate != null) {
            return delegate;
        }

        String clsName = cls.getName();

        try {
            Class<?> delegateClass = Class.forName(clsName + DELEGATE_SUFFIX);

            delegate = (Delegate<Object>) delegateClass.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        DELEGATE_CACHE.put(cls, delegate);
        return delegate;
    }

    private static Injector<Object> findInjectorForClass(Class<?> cls) {
        Injector<Object> injector = INJECTOR_CACHE.get(cls);
        if (injector != null) {
            return injector;
        }

        String clsName = cls.getName();

        try {
            Class<?> injectorClass = Class.forName(clsName + INJECTOR_SUFFIX);

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

    public interface Delegate<T> extends Parcelable{
        public void delegate(T t);

        public Object getClient();

        public void injectClient(T t);
    }
}
