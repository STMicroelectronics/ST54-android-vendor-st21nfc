/*
 * Copyright (C) 2022 ST Microelectronics S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.st.android.nfc_extensions;

import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Log;

public class NfcNdefNfceeAdapter {
    private static final String TAG = "NfcNdefNfceeAdapter";
    static final boolean DBG = SystemProperties.getBoolean("persist.nfc.debug_enabled", false);

    private static INfcNdefNfceeAdapter sInterface = null;

    public NfcNdefNfceeAdapter(INfcNdefNfceeAdapter intf) {
        sInterface = intf;
    }

    void attemptDeadServiceRecovery(Exception e) {
        Log.e(TAG, "NFC NDEF-NFCEE ST Extensions dead - recover by close / open, TODO");
    }

    public boolean writeNdefData(byte[] fileId, byte[] data) {
        boolean result = false;
        try {
            result = sInterface.writeNdefData(fileId, data);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        return result;
    }

    public byte[] readNdefData(byte[] fileId) {
        byte[] result = null;
        try {
            result = sInterface.readNdefData(fileId);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        return result;
    }

    public boolean lockNdefData(byte[] fileId, boolean lock) {
        boolean result = false;
        try {
            result = sInterface.lockNdefData(fileId, lock);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        return result;
    }

    public boolean isLockedNdefData(byte[] fileId) {
        boolean result = false;
        try {
            result = sInterface.isLockedNdefData(fileId);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        return result;
    }

    public boolean clearNdefData(byte[] fileId) {
        boolean result = false;
        try {
            result = sInterface.clearNdefData(fileId);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        return result;
    }

    private static String bytesToString(byte[] bytes) {
        if (bytes == null) return "";

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x ", b & 0xFF));

        return sb.toString();
    }

    public CCFileInfo readT4tCcfile() {
        byte[] result = null;
        CCFileInfo fileInfo = null;
        try {
            result = sInterface.readT4tCcfile();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        if (DBG) {
            Log.d(TAG, "readT4tCcfile() - CC file content: " + bytesToString(result));
        }

        if (result != null) {
            fileInfo = new CCFileInfo(result);
        }

        return fileInfo;
    }
}
