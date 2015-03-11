package kirisame.android.toolset.parcel.internal;

/**
* Created by 雾雨 on 2015/3/11.
*/
public class CodeFragment {


    String mFieldName;

    String mFieldType;

    public CodeFragment(String fieldName, String classType) {
        mFieldName = fieldName;
        mFieldType = classType;
    }

    public String getFieldName() {
        return mFieldName;
    }

    public String getFieldType() {
        return mFieldType;
    }
}
