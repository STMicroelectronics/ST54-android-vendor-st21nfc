 /*
  * Copyright (C) 2017 NXP Semiconductors
  * Copyright (C) 2021 ST Microelectronics S.A.
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *      http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  *
  * Derived from android.nfc.INfcDta of /system/framework
  * for NFC service DTA service access from JAR for SNEP testing only
  */
package com.st.android.nfc_dta;

import android.os.Bundle;

/**
 * {@hide}
 */
interface INfcAdapterDta {

    boolean enableDta();
    boolean disableDta();
    boolean enableServer(String serviceName, int serviceSap, int miu,
            int rwSize,int testCaseId);
    boolean disableServer();
    boolean enableClient(String serviceName, int miu, int rwSize,
            int testCaseId);
    boolean disableClient();
    boolean registerMessageService(String msgServiceName);
}
