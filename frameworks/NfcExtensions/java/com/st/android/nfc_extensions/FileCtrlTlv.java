/*
 * Copyright (C) 2022 ST Microelectronics S.A.
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

import android.os.SystemProperties;
import android.util.Log;

public class FileCtrlTlv {
    String TAG = "NfcFileCtrlTlv";
    static final boolean DBG = SystemProperties.getBoolean("persist.nfc.debug_enabled", false);

    private int mType;
    private byte[] mFileId;
    private int mMaxSize;
    private boolean mReadAccess;
    private boolean mWriteAccess;

    public FileCtrlTlv(byte[] data, int idx) {
        mType = data[idx];
        mFileId = new byte[] {data[idx + 2], data[idx + 3]};
        mMaxSize = ((data[idx + 4] & 0xFF) << 8) + (data[idx + 5] & 0xFF);
        mReadAccess = (data[idx + 6] == 0x00);
        mWriteAccess = (data[idx + 7] == 0x00);

        Log.d(
                TAG,
                "FileCtrlTlv(constructor) - File Type: "
                        + String.format("0x%02X", mType)
                        + ", FileId: "
                        + bytesToString(mFileId)
                        + ", File Max Size: "
                        + String.format("0x%02X", mMaxSize)
                        + ", R: "
                        + mReadAccess
                        + ", W: "
                        + mWriteAccess);
    }

    private static String bytesToString(byte[] bytes) {
        if (bytes == null) return "";

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x ", b & 0xFF));

        return sb.toString();
    }

    public int getType() {
        return mType;
    }

    public int getMaxSize() {
        return mMaxSize;
    }

    public byte[] getFileId() {
        return mFileId;
    }

    public boolean getReadAccess() {
        return mReadAccess;
    }

    public boolean getWriteAccess() {
        return mWriteAccess;
    }
}
