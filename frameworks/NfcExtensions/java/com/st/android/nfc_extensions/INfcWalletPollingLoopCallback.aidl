
/******************************************************************************
 *
 * Copyright (C) 2018 ST Microelectronics S.A.
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

interface INfcWalletPollingLoopCallback
{
    /* Receive a String of events separated by durations in the format: Ex;ts[;Ex;ts]*;Ex
     * Events (Ex) is one of:
     *  - 'O' : remote field ON
     *  - 'A'+x : received a REQA or WUPA, with gain 'x' (00-21)
     *  - 'B'+x : received a REQB, with gain 'x' (00-21)
     *  - 'F'+x : received a REQF, with gain 'x' (00-21)
     *  - 'o' : remote field OFF
     * and ts is the duration in us between two events.
     *  This data is obtained in "observer mode" for a period of around 100ms before CE is enabled.
     *  It is truncated at 50 events in case more events are received.
     */
    void onPollingLoopInfoReceived(in String data);
}
