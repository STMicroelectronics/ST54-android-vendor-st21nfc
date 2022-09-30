package com.st.android.nfc_extensions;

import android.os.Parcel;
import android.os.Parcelable;

public class ByteArray implements Parcelable {
    public final byte[] mBytearray;

    public ByteArray(byte[] bytearray) {
        this.mBytearray = bytearray;
    }

    public byte[] getByteArray() {
        return this.mBytearray;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mBytearray.length);
        dest.writeByteArray(mBytearray);
    }

    public static final Parcelable.Creator<ByteArray> CREATOR =
            new Parcelable.Creator<ByteArray>() {
                @Override
                public ByteArray createFromParcel(Parcel source) {
                    byte[] ba = new byte[source.readInt()];
                    source.readByteArray(ba);

                    return new ByteArray(ba);
                }

                @Override
                public ByteArray[] newArray(int size) {
                    return new ByteArray[size];
                }
            };
}
