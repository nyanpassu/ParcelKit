package kirisame.android.toolset.parcel.internal;

/**
 * Created by 雾雨 on 2015/3/11.
 */
public class DelegateFragment extends CodeFragment {

    public DelegateFragment(String fieldName, String classType) {
        super(fieldName, classType);
    }

    public String generateFieldDeclarationSourceFragment() {
        return String.format("%s %s;",mFieldType,mFieldName);
    }

    public String generateDelegateSourceFragment(String clientName) {
        return String.format("%s = %s.%s;", mFieldName, clientName, mFieldName);
    }

    public String generateInjectClientSourceFragment(String clientName) {
        return String.format("%s.%s = %s;", clientName, mFieldName, mFieldName);
    }

    public String generateWriteParcelSourceFragment(String destName) {
        return String.format("%s.writeValue(%s);", destName, mFieldName);
    }

    public String generateCreatorSourceFragment(String targetName, String sourceName, String targetClassName) {
        return String.format("%s.%s = (%s) %s.readValue(%s.class.getClassLoader());", targetName, mFieldName, mFieldType, sourceName, targetClassName);
    }
}
