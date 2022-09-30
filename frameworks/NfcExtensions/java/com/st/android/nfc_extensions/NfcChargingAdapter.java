/*
 * Copyright (C) 2020 ST Microelectronics S.A.
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
import android.util.Log;

public final class NfcChargingAdapter {
    private static final String TAG = "NfcChargingAdapter";

    private static INfcChargingAdapter sInterface = null;

    /**
     * Constructor for the {@link NfcChargingAdapter}
     *
     * @param intf a {@link NfcChargingAdapter}, must not be null
     * @return
     */
    public NfcChargingAdapter(INfcChargingAdapter intf) {
        sInterface = intf;
    }

    /** NFC service dead - attempt best effort recovery */
    void attemptDeadServiceRecovery(Exception e) {
        Log.e(TAG, "NFC Charging Adapter ST Extensions dead - recover by close / open, TODO");
    }

    public boolean registerStChargingDataCallback(INfcChargingDataCallback cb) {
        boolean result = false;
        try {
            result = sInterface.registerStChargingDataCallback(cb);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public boolean unregisterStChargingDataCallback() {
        boolean result = false;
        try {
            result = sInterface.unregisterStChargingDataCallback();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }
}
