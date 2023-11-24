/*
 *  The original Work has been changed by ST Microelectronics S.A.
 *
 * Copyright MediaTek Inc. (C) 2016
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

import com.st.android.nfc_extensions.ServiceEntry;
import com.st.android.nfc_extensions.INfcSettingsCallback;
import com.st.android.nfc_extensions.DefaultRouteEntry;

import java.util.Map;
import java.util.List;


/**
 * @hide
 */
interface INfcSettingsAdapter
{
    /* Interfaces for controlling the behavior of the NFC stack */

    /* Enable / disable tag R/W, P2P, HCE modes */
    int getModeFlag(int mode);
    void setModeFlag(int mode, int flag);

    /* Low-level control of the SWP interfaces */
    boolean isUiccConnected();
    boolean iseSEConnected();
    boolean isSEConnected(int HostID);

    /* High-level functions to manage secure elements. */

    /* Get the current state of SWP elements:
     Each item is a pair NAME:STATUS.
     NAME and STATUS are constants NfcSettingsAdapter.SE_*
     NAME is one of: SIM1, SIM2, eSE.
     STATUS is one of: Active, Available, N/A.
    */
    List<String> getSecureElementsStatus();

    /* Set the element requested by user. It may disable another SIM card if needed.
     The selection is stored and will be reapplied after reboot.
    */
    boolean EnableSE(String se_id, boolean enable);

    /* Get notifications from NFC service when Nfc Settings need update */
    void registerNfcSettingsCallback(INfcSettingsCallback cb);
    void unregisterNfcSettingsCallback(INfcSettingsCallback cb);


    /* For GSMA TS26 requirements on AID routing table management */

    boolean isRoutingTableOverflow();
    boolean isShowOverflowMenu();

    List<ServiceEntry> getServiceEntryList(int userHandle);
    boolean testServiceEntryList(in List<ServiceEntry> proposal);
    void commitServiceEntryList(in List<ServiceEntry> proposal);

    List<ServiceEntry> getNonAidBasedServiceEntryList(int userHandle);
    void commitNonAidBasedServiceEntryList(in List<ServiceEntry> proposal);

    void setDefaultUserRoutes(in List<DefaultRouteEntry> userRoutes);
    List<DefaultRouteEntry> getDefaultUserRoutes();
    List<DefaultRouteEntry> getEffectiveRoutes();

    int getAvailableSpaceForAid();
}
