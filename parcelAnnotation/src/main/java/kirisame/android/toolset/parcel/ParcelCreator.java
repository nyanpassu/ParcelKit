package kirisame.android.toolset.parcel;

/**
 * Created by 丢猫 on 2015/3/9.
 */
//TODO bad naming , rename it later
public class ParcelCreator {

    private String mFieldName;

    private String mFieldType;

    public ParcelCreator(String fieldName, String classType) {
        mFieldName = fieldName;
        mFieldType = classType;
    }

    public String generateWriteParcelSourceFragment(){
        return String.format("parcel.writeValue(t.%s);",mFieldName);
    }

    public String generateCreatorSourceFragment(String targetClassName) {
        return String.format("parcelObject.%s = (%s) source.readValue(%s.class.getClassLoader());",mFieldName,mFieldType,targetClassName);
    }
}
