/*
 * Copyright (C) 2021 ST Microelectronics S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
