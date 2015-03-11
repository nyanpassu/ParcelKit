package kirisame.android.toolset.parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 丢猫 on 2015/3/11.
 */
public class DelegateGenerator extends CodeGenerator<DelegateFragment>{

    public DelegateGenerator(String packageName, String className, String targetClass) {
        super(packageName, className, targetClass);
    }

    @Override
    public DelegateFragment generateFragment(String fieldName, String classType) {
        return new DelegateFragment(fieldName, classType);
    }

    public String generateJavaSource() {

        List<DelegateFragment> fragmentList = getFragmentList();
        int listSize = fragmentList.size();

        StringBuilder builder = new StringBuilder();
        builder.append("//generate code by parcel annotation processor , DO NOT MODIFY!\n");
        builder.append(String.format("package %s;\n\n", mPackageName));
        builder.append("import android.os.Parcel;\n");
        builder.append("import android.os.Parcelable;\n\n");
        builder.append(String.format("public class %s<T extends %s> implements kirisame.android.toolset.parcel.ParcelKit.Delegate<T> {\n\n",mClassName, mTargetClassFullName));
        // generate field declaration code
        for (int i = 0; i < listSize; i++) {
            builder.append("    ").append(fragmentList.get(i).generateFieldDeclarationSourceFragment()).append("\n\n");
        }
        // generate delegate code
        builder.append("    @Override\n");
        builder.append("    public void delegate(T t) {\n");
        for (int i = 0; i < listSize; i++) {
            builder.append("        ").append(fragmentList.get(i).generateDelegateSourceFragment("t")).append("\n");
        }
        builder.append("    }\n\n");
        // generate get client code
        builder.append("    @Override\n");
        builder.append("    public Object getClient() {\n");
        builder.append(String.format("        %s client = new %s();\n",mTargetClassFullName,mTargetClassFullName));
        for (int i = 0; i < listSize; i++) {
            builder.append("        ").append(fragmentList.get(i).generateInjectClientSourceFragment("client")).append("\n");
        }
        builder.append("        return client;\n");
        builder.append("    }\n\n");
        // generate inject client code
        builder.append("    @Override\n");
        builder.append("    public void injectClient(T t) {\n");
        for (int i = 0; i < listSize; i++) {
            builder.append("        ").append(fragmentList.get(i).generateInjectClientSourceFragment("t")).append("\n");
        }
        builder.append("    }\n\n");
        // TODO generate describe content code , later will conjunct code with @ParcelDescribe
        builder.append("    @Override\n");
        builder.append("    public int describeContents() {\n");
        builder.append("        return 0;\n");
        builder.append("    }\n\n");
        // generate write to parcel code
        builder.append("    @Override\n");
        builder.append("    public void writeToParcel(Parcel dest, int flags) {\n");
        for (int i = 0; i < listSize; i++) {
            builder.append("        ").append(fragmentList.get(i).generateWriteParcelSourceFragment("dest")).append("\n");
        }
        builder.append("    }\n\n");
        // generate creator code
        builder.append(String.format("    public static final Parcelable.Creator<%s> CREATOR = new Parcelable.Creator<%s>() {\n\n", mClassName, mClassName));
        // generate create parcel code
        builder.append("        @Override\n");
        builder.append(String.format("        public %s createFromParcel(Parcel source) {\n", mClassName));
        builder.append(String.format("            %s delegate = new %s();\n", mClassName, mClassName));
        for (int i = 0; i < listSize; i++) {
            builder.append("            ").append(fragmentList.get(i).generateCreatorSourceFragment("delegate", "source", mClassName)).append("\n");
        }
        builder.append("            return delegate;\n");
        builder.append("        }\n\n");
        // generate new array code
        builder.append("        @Override\n");
        builder.append(String.format("        public %s[] newArray(int size) {\n", mClassName));
        builder.append(String.format("            return new %s[size];\n", mClassName));
        builder.append("        }\n\n");
        builder.append("    };\n}");

        return builder.toString();
    }
}
