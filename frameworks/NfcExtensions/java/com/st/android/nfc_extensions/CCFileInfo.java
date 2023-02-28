package com.st.android.nfc_extensions;

import android.os.SystemProperties;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CCFileInfo {
    String TAG = "NfcCCFileInfo";
    static final boolean DBG = SystemProperties.getBoolean("persist.nfc.debug_enabled", false);

    private byte mMapping;
    private int mMle;
    private int mMlc;
    private int mNbFileCtrlTlv;

    private List<FileCtrlTlv> mFileCtrlTlv = new ArrayList<>();

    public CCFileInfo(byte[] data) {
        if (data == null) {
            Log.e(TAG, "No data provided for parsing");
            return;
        }

        if (data.length < (byte) 0x0F) {
            Log.e(TAG, "CC File length too short: " + data.length);
            return;
        }

        int ccLength = data.length - 2;
        mMapping = data[0];
        mMle = ((data[1] & 0xFF) << 8) + (data[2] & 0xFF);
        mMlc = ((data[3] & 0xFF) << 8) + (data[4] & 0xFF);
        mNbFileCtrlTlv = 0;

        Log.d(
                TAG,
                "CCFileInfo(constructor) - Mapping: "
                        + String.format("0x%02X", mMapping)
                        + ", Mle: "
                        + String.format("0x%02X", mMle)
                        + ", Mlc: "
                        + String.format("0x%02X", mMlc));

        int i = 5;
        while (i < ccLength) {
            FileCtrlTlv fileInfo = new FileCtrlTlv(data, i);
            mFileCtrlTlv.add(fileInfo);
            mNbFileCtrlTlv++;
            i += 8;
        }
    }

    public byte getMapping() {
        return mMapping;
    }

    public int getMle() {
        return mMle;
    }

    public int getMlc() {
        return mMlc;
    }

    public int getNbFileCtrlTlv() {
        return mNbFileCtrlTlv;
    }

    public FileCtrlTlv getFileCtrlTlv(byte[] fileId) {
        for (int i = 0; i < mFileCtrlTlv.size(); i++) {
            if (Arrays.equals(fileId, mFileCtrlTlv.get(i).getFileId())) {
                return mFileCtrlTlv.get(i);
            }
        }
        return null;
    }

    public List<byte[]> getFileIds() {
        List<byte[]> list = new ArrayList<>();

        for (int i = 0; i < mFileCtrlTlv.size(); i++) {
            list.add(mFileCtrlTlv.get(i).getFileId());
        }

        return list;
    }
}
