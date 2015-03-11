package kirisame.android.toolset.parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 丢猫 on 2015/3/11.
 */
public abstract class CodeGenerator<T extends CodeFragment> {

    String mPackageName;

    String mClassName;

    String mTargetClassFullName;

    List<T> mFragmentList;

    public CodeGenerator(String packageName, String className, String targetClass) {
        mPackageName = packageName;
        mClassName = className;
        mTargetClassFullName = targetClass;
    }

    // TODO we shall check each fragment representing different @ParceField
    public void addCodeFragment(T fragment) {
        final List<T> fragmentList = getFragmentList();
        if (!fragmentList.contains(fragment)) {
            fragmentList.add(fragment);
        }
    }

    public void addCodeFragment(String fieldName, String classType) {
        T fragment = generateFragment(fieldName, classType);
        final List<T> fragmentList = getFragmentList();
        if (!fragmentList.contains(fragment)) {
            fragmentList.add(fragment);
        }
    }

    public List<T> getFragmentList() {
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>();
        }
        return mFragmentList;
    }

    public String getClassFullNames() {
        return mPackageName + "." + mClassName;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public String getClassName() {
        return mClassName;
    }

    public String getTargetClassFullName() {
        return mTargetClassFullName;
    }

    public abstract T generateFragment(String fieldName, String classType);

    public abstract String generateJavaSource() ;
}
