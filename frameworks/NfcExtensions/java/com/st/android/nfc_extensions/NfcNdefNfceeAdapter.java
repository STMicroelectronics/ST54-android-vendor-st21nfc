/* Copyright Statement:
 *
 * This software/firmware and related documentation ("MediaTek Software") are
 * protected under relevant copyright laws. The information contained herein is
 * confidential and proprietary to MediaTek Inc. and/or its licensors. Without
 * the prior written permission of MediaTek inc. and/or its licensors, any
 * reproduction, modification, use or disclosure of MediaTek Software, and
 * information contained herein, in whole or in part, shall be strictly
 * prohibited.
 *
 * MediaTek Inc. (C) 2022. All rights reserved.
 *
 * BY OPENING THIS FILE, RECEIVER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
 * THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
 * RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO RECEIVER
 * ON AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NONINFRINGEMENT. NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH
 * RESPECT TO THE SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY,
 * INCORPORATED IN, OR SUPPLIED WITH THE MEDIATEK SOFTWARE, AND RECEIVER AGREES
 * TO LOOK ONLY TO SUCH THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO.
 * RECEIVER EXPRESSLY ACKNOWLEDGES THAT IT IS RECEIVER'S SOLE RESPONSIBILITY TO
 * OBTAIN FROM ANY THIRD PARTY ALL PROPER LICENSES CONTAINED IN MEDIATEK
 * SOFTWARE. MEDIATEK SHALL ALSO NOT BE RESPONSIBLE FOR ANY MEDIATEK SOFTWARE
 * RELEASES MADE TO RECEIVER'S SPECIFICATION OR TO CONFORM TO A PARTICULAR
 * STANDARD OR OPEN FORUM. RECEIVER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S
 * ENTIRE AND CUMULATIVE LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE
 * RELEASED HEREUNDER WILL BE, AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE
 * MEDIATEK SOFTWARE AT ISSUE, OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE
 * CHARGE PAID BY RECEIVER TO MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
 *
 * The following software/firmware and/or related documentation ("MediaTek
 * Software") have been modified by MediaTek Inc. All revisions are subject to
 * any receiver's applicable license agreements with MediaTek Inc.
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
