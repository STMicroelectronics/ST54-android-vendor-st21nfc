/*
 * Copyright (C) 2019 ST Microelectronics S.A.
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

interface INfceeActionNtfCallback
{
    /* Receive one RF_NFCEE_ACTION_NTF message
     *  nfcee: ID of the NFCEE.
     *  data: trigger + supporting data, e.g. [00 len AID] or [01 01 protocol]
     */
    void onNfceeActionNtfReceived(in int nfcee, in byte[] data);
}
