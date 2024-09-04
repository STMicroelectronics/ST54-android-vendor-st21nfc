/*
 * Copyright (C) 2018 ST Microelectronics S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Provide extensions for the ST implementation of the NFC stack
 */
package com.st.android.nfc_extensions;

import android.util.Log;

public class StNonAidBasedServiceInfo {
    static final String TAG = "APINfc_StNonAidBasedServiceInfo";
    static final boolean DBG = true;

    int mSeId;

    public StNonAidBasedServiceInfo(int host_id) {
        if (DBG) Log.d(TAG, "Constructor - hostId: " + host_id);

        this.mSeId = host_id;
    }

    public int getSeId() {
        if (DBG) Log.d(TAG, "getHostId - seId: " + this.mSeId);
        return this.mSeId;
    }
}
