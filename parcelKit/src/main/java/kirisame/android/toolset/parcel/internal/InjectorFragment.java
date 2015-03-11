package kirisame.android.toolset.parcel.internal;

/**
 * Created by 雾雨 on 2015/3/9.
 */
public class InjectorFragment extends CodeFragment {

    public InjectorFragment(String fieldName, String classType) {
        super(fieldName, classType);
    }

    public String generateWriteParcelSourceFragment() {
        return String.format("parcel.writeValue(t.%s);", mFieldName);
    }

    public String generateCreatorSourceFragment(String targetClassName) {
        return String.format("parcelObject.%s = (%s) source.readValue(%s.class.getClassLoader());", mFieldName, mFieldType, targetClassName);
    }
}
