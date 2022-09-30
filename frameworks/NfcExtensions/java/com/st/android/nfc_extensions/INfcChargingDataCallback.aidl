
/******************************************************************************
 *
 * Copyright (C) 2020 ST Microelectronics S.A.
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
 *
 ******************************************************************************/
package com.st.android.nfc_extensions;

interface INfcChargingDataCallback
{
    /* TLV parsing is delegated at the moment.
     * logtype is 0 if TLV does not contain timestamps, 1 if it does.
     */
    void onChargingDataReceived(in int logtype, in int data);
}
