package kirisame.android.toolset.parcel.internal;

import java.util.List;

/**
 * Created by 雾雨 on 2015/3/9.
 */
public class InjectorGenerator extends CodeGenerator<InjectorFragment> {

    public InjectorGenerator(String packageName, String className, String targetClass) {
        super(packageName, className, targetClass);
    }

    @Override
    public InjectorFragment generateFragment(String fieldName, String classType) {
        return new InjectorFragment(fieldName, classType);
    }

    public String generateJavaSource() {

        List<InjectorFragment> fragmentList = getFragmentList();
        int listSize = fragmentList.size();

        StringBuilder builder = new StringBuilder();
        builder.append("//generate code by parcel annotation processor , DO NOT MODIFY!\n");
        builder.append(String.format("package %s;\n\n", mPackageName));
        builder.append("import android.os.Parcel;\n");
        builder.append("import android.os.Parcelable;\n\n");
        builder.append(String.format("public class %s<T extends %s> implements kirisame.android.toolset.parcel.ParcelKit.Injector<T> {\n\n", mClassName, mTargetClassFullName));
        builder.append("    @Override\n");
        builder.append("    public void writeParcel(T t, Parcel parcel, int flags) {\n");
        for (int i = 0; i < listSize; i++) {
            builder.append("        ").append(fragmentList.get(i).generateWriteParcelSourceFragment()).append("\n");
        }
        builder.append("    }\n\n");
        builder.append("    @Override\n");
        builder.append("    public Parcelable.Creator getCreator() {\n");
        builder.append("        return CREATOR;\n");
        builder.append("    }\n\n");
        builder.append(String.format("    public static Parcelable.Creator<%s> CREATOR = new Parcelable.Creator<%s>() {\n\n", mTargetClassFullName, mTargetClassFullName));
        builder.append("        @Override\n");
        builder.append(String.format("        public %s createFromParcel(Parcel source) {\n", mTargetClassFullName));
        builder.append(String.format("            %s parcelObject = new %s();\n", mTargetClassFullName, mTargetClassFullName));
        for (int i = 0; i < listSize; i++) {
            builder.append("            ").append(fragmentList.get(i).generateCreatorSourceFragment(mTargetClassFullName)).append("\n");
        }
        builder.append("            return parcelObject;\n");
        builder.append("        }\n\n");
        builder.append("        @Override\n");
        builder.append(String.format("        public %s[] newArray(int size) {\n", mTargetClassFullName));
        builder.append(String.format("            return new %s[size];\n", mTargetClassFullName));
        builder.append("        }\n\n");
        builder.append("    };\n}");

        return builder.toString();
    }
}
