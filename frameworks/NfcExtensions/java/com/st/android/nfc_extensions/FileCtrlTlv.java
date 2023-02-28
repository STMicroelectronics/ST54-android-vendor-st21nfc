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
