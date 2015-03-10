package kirisame.android.parcelannotation;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import kirisame.android.toolset.parcel.ParcelField;
import kirisame.android.toolset.parcel.ParcelKit;

/**
 * Created by 丢猫 on 2015/3/10.
 */
public class ParcelObject implements Parcelable {

    String mWillNotParcel;

    @ParcelField
    int mCode;

    @ParcelField
    String mMessage;

    @ParcelField
    Bitmap mBitmap;

    public static final Creator<ParcelObject> CREATOR
            = (Creator<ParcelObject>) ParcelKit.getCreator(ParcelObject.class);

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelKit.write(this, dest, flags);
    }
}
