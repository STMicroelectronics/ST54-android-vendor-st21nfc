/*
 *  The original Work has been changed by ST Microelectronics S.A.
 *
 * Copyright MediaTek Inc. (C) 2017
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

import android.content.Context;
import android.nfc.NfcAdapter;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NfcSettingsAdapter {
    static INfcSettingsAdapter sService;

    private static final String TAG = "NfcSettingsAdapter";

    /**
     * The NfcAdapter object for each application context. There is a 1-1 relationship between
     * application context and NfcAdapter object.
     */
    static HashMap<Context, NfcSettingsAdapter> sNfcSettingsAdapters = new HashMap();

    final Context mContext;

    public static final String SERVICE_SETTINGS_NAME = "nfc_settings";

    /* Below values must be aligned with SecureElementSelector code */
    public static final String SE_SIM1 = "SIM1";
    public static final String SE_SIM2 = "SIM2";
    public static final String SE_ESE1 = "eSE";

    public static final String SE_STATE_ACTIVATED = "Active";
    public static final String SE_STATE_AVAILABLE = "Available";
    public static final String SE_STATE_NOT_AVAILABLE = "N/A";

    public NfcSettingsAdapter(Context context) {
        mContext = context;
        sService = getServiceInterface();
    }

    /**
     * Helper to get the default NFC Settings Adapter.
     *
     * @param context the calling application's context
     * @return the default NFC settings adapter, or null if no NFC settings adapter exists
     */
    public static NfcSettingsAdapter getDefaultAdapter(Context context) {
        if (NfcAdapter.getDefaultAdapter(context) == null) {
            Log.d(TAG, "getDefaultAdapter = null");
            return null;
        }

        NfcSettingsAdapter adapter = sNfcSettingsAdapters.get(context);
        if (adapter == null) {
            adapter = new NfcSettingsAdapter(context);
            sNfcSettingsAdapters.put(context, adapter);
        }

        if (sService == null) {
            sService = getServiceInterface();
            Log.d(TAG, "sService = " + sService);
        }

        Log.d(TAG, "adapter = " + adapter);
        return adapter;
    }

    private static INfcSettingsAdapter getServiceInterface() {
        INfcSettingsAdapter result;
        /* get a handle to NFC extensions service */
        IBinder b = ServiceManager.getService(NfcAdapterStExtensions.SERVICE_NAME);
        if (b == null)
            throw new RuntimeException(
                    "Cannot retrieve service :" + NfcAdapterStExtensions.SERVICE_NAME);
        INfcAdapterStExtensions ext = INfcAdapterStExtensions.Stub.asInterface(b);

        try {
            result = ext.getNfcSettingsAdapterInterface();
        } catch (RemoteException e) {
            result = null;
            throw new RuntimeException(
                    "Cannot retrieve NfcSettingsAdapter from service :"
                            + NfcAdapterStExtensions.SERVICE_NAME);
        }
        return result;
    }

    /**
     * Indicates if an UICC is connected to the ST21NFCD chip.
     *
     * @return true if an UICC is connected, false if not.
     */
    public boolean isUiccConnected() {
        boolean result = false;
        try {
            result = sService.isUiccConnected();
        } catch (RemoteException e) {
            // attemptDeadServiceRecovery(e);
            Log.e(TAG, "isUiccConnected() - e = " + e.toString());
        }

        Log.d(TAG, "isUiccConnected() - " + result);

        return result;
    }

    /**
     * Indicates if an eSE is connected to the ST21NFCD chip.
     *
     * @return true if an eSE is connected, false if not.
     */
    public boolean iseSEConnected() {
        boolean result = false;
        try {
            result = sService.iseSEConnected();
        } catch (RemoteException e) {
            // attemptDeadServiceRecovery(e);
            Log.e(TAG, "iseSEConnected() - e = " + e.toString());
        }

        Log.d(TAG, "iseSEConnected() - " + result);

        return result;
    }

    /**
     * Indicates if an SE given it HostID is connected to the ST21NFCD chip.
     *
     * @return true if an SE is connected, false if not.
     */
    public boolean isSEConnected(int HostID) {
        boolean result = false;
        try {
            result = sService.isSEConnected(HostID);
        } catch (RemoteException e) {
            // attemptDeadServiceRecovery(e);
            Log.e(TAG, "isSEConnected() - e = " + e.toString());
        }

        Log.d(TAG, "isSEConnected(" + HostID + ") - " + result);

        return result;
    }

    /**
     * This API activate or deactivate the given Secure Element defined by se_id.
     *
     * <p>
     *
     * @return true if successful
     */
    public boolean EnableSE(String se_id, boolean enable) {
        Log.i(TAG, "EnableSE(" + se_id + ", " + enable + ")");
        boolean status = false;

        try {
            return sService.EnableSE(se_id, enable);
        } catch (RemoteException e) {
            // attemptDeadServiceRecovery(e);
            Log.e(TAG, "EnableSE() - e = " + e.toString());
        }

        return status;
    }

    /* Get the current state of SWP elements:
    Each item is a pair NAME:STATUS.
    NAME and STATUS are constants NfcSettingsAdapter.SE_*
    NAME is one of: SIM1, SIM2, eSE.
    STATUS is one of: Active, Available, N/A.
    */
    public List<String> getSecureElementsStatus() {
        Log.i(TAG, "getSecureElementsStatus()");

        try {
            return sService.getSecureElementsStatus();
        } catch (RemoteException e) {
            // attemptDeadServiceRecovery(e);
            Log.e(TAG, "getSecureElementsStatus() e = " + e.toString());
        }

        return null;
    }

    /* Callback for UI updates */
    public void registerNfcSettingsCallback(INfcSettingsCallback cb) {
        Log.i(TAG, "registerNfcSettingsCallback()");

        try {
            sService.registerNfcSettingsCallback(cb);
        } catch (RemoteException e) {
            // attemptDeadServiceRecovery(e);
            Log.e(TAG, "registerNfcSettingsCallback() e = " + e.toString());
        }
    }

    public void unregisterNfcSettingsCallback(INfcSettingsCallback cb) {
        Log.i(TAG, "unregisterNfcSettingsCallback()");

        try {
            sService.unregisterNfcSettingsCallback(cb);
        } catch (RemoteException e) {
            // attemptDeadServiceRecovery(e);
            Log.e(TAG, "unregisterNfcSettingsCallback() e = " + e.toString());
        }
    }

    public static final String DEFAULT_AID_ROUTE = "default_aid_route";
    public static final String DEFAULT_MIFARE_ROUTE = "default_mifare_route";
    public static final String DEFAULT_ISO_DEP_ROUTE = "default_iso_dep_route";
    public static final String DEFAULT_FELICA_ROUTE = "default_felica_route";
    public static final String DEFAULT_AB_TECH_ROUTE = "default_ab_tech_route";
    public static final String DEFAULT_SC_ROUTE = "default_sc_route";

    public static final String UICC_ROUTE = "UICC";
    public static final String ESE_ROUTE = "eSE";
    public static final String HCE_ROUTE = "HCE";
    public static final String DEFAULT_ROUTE = "Default";

    /**
     * Set listen mode routing table configuration for Default Route. routeLoc is parameter which
     * fetch the text from UI and compare *
     *
     * <p>Requires {@link android.Manifest.permission#NFC} permission.
     *
     * @throws IOException If a failure occurred during Default Route Route set.
     */
    public void DefaultRouteSet(String routeLoc) throws IOException {
        Log.i(TAG, "DefaultRouteSet() - route: " + routeLoc);

        HashMap<String, String> entry = new HashMap<String, String>();
        entry.put(DEFAULT_AID_ROUTE, routeLoc);
        setUserDefaultRoutes(entry);
    }

    /**
     * Set listen mode routing table configuration for Default Route. routeLoc is parameter which
     * fetch the text from UI and compare *
     *
     * <p>Requires {@link android.Manifest.permission#NFC} permission.
     *
     * @throws IOException If a failure occurred during Default Route Route set.
     */
    public void setUserDefaultRoutes(Map<String, String> routeList) throws IOException {

        List<DefaultRouteEntry> defaultRouteList = new ArrayList<DefaultRouteEntry>();

        for (Map.Entry<String, String> entry : routeList.entrySet()) {
            String routeKey = entry.getKey();
            String routeValue = entry.getValue();

            Log.d(TAG, "setUserDefaultRoutes() - " + routeKey + ": " + routeValue);

            if ((DEFAULT_AID_ROUTE.contentEquals(routeKey) == false)
                    && (DEFAULT_MIFARE_ROUTE.contentEquals(routeKey) == false)
                    && (DEFAULT_ISO_DEP_ROUTE.contentEquals(routeKey) == false)
                    && (DEFAULT_FELICA_ROUTE.contentEquals(routeKey) == false)
                    && (DEFAULT_AB_TECH_ROUTE.contentEquals(routeKey) == false)
                    && (DEFAULT_SC_ROUTE.contentEquals(routeKey) == false)) {

                Log.e(TAG, "setUserDefaultRoutes() - " + routeKey + " does not exists");
                throw new IOException(routeKey + " does not exists");
            }

            if ((UICC_ROUTE.contentEquals(routeValue) == false)
                    && (ESE_ROUTE.contentEquals(routeValue) == false)
                    && (HCE_ROUTE.contentEquals(routeValue) == false)
                    && (DEFAULT_ROUTE.contentEquals(routeValue)) == false) {

                Log.e(TAG, "setUserDefaultRoutes() - " + routeValue + " does not exists");
                throw new IOException(routeValue + " does not exists");
            }

            DefaultRouteEntry defaultRouteEntry = new DefaultRouteEntry(routeKey, routeValue);
            defaultRouteList.add(defaultRouteEntry);
        }

        try {
            sService.setDefaultUserRoutes(defaultRouteList);
        } catch (RemoteException e) {
            Log.e(TAG, "setDefaultUserRoutes failed", e);
            throw new IOException("setDefaultUserRoutes failed");
        }
    }

    /**
     * Set listen mode routing table configuration for Default Route. routeLoc is parameter which
     * fetch the text from UI and compare *
     *
     * <p>Requires {@link android.Manifest.permission#NFC} permission.
     *
     * @throws IOException If a failure occurred during Default Route Route set.
     */
    public Map<String, String> getUserDefaultRoutes() throws IOException {
        Map<String, String> userRoutes = new HashMap<String, String>();

        List<DefaultRouteEntry> list = new ArrayList<DefaultRouteEntry>();

        try {
            list = sService.getDefaultUserRoutes();
        } catch (RemoteException e) {
            Log.e(TAG, "getUserDefaultRoutes failed", e);
            throw new IOException("getUserDefaultRoutes failed");
        }

        for (DefaultRouteEntry entry : list) {
            Log.d(
                    TAG,
                    "getUserDefaultRoutes() - "
                            + entry.getRouteName()
                            + ": "
                            + entry.getRouteLoc());
            userRoutes.put(entry.getRouteName(), entry.getRouteLoc());
        }

        return userRoutes;
    }

    /**
     * Set listen mode routing table configuration for Default Route. routeLoc is parameter which
     * fetch the text from UI and compare *
     *
     * <p>Requires {@link android.Manifest.permission#NFC} permission.
     *
     * @throws IOException If a failure occurred during Default Route Route set.
     */
    public Map<String, String> getEffectiveDefaultRoutes() throws IOException {
        Map<String, String> userRoutes = new HashMap<String, String>();

        List<DefaultRouteEntry> list = new ArrayList<DefaultRouteEntry>();

        try {
            list = sService.getEffectiveRoutes();
        } catch (RemoteException e) {
            Log.e(TAG, "getEffectiveDefaultRoutes failed", e);
            throw new IOException("getEffectiveDefaultRoutes failed");
        }

        for (DefaultRouteEntry entry : list) {
            Log.d(
                    TAG,
                    "getEffectiveDefaultRoutes() - "
                            + entry.getRouteName()
                            + ": "
                            + entry.getRouteLoc());
            userRoutes.put(entry.getRouteName(), entry.getRouteLoc());
        }

        return userRoutes;
    }

    /**
     * Retrieve how many bytes are still availabe to add AID entries in the listen mode routing
     * table.
     *
     * <p>Each entry has an overhead of 4 bytes per AID. Entries for the same route as the default
     * route don't consume space. The available space can change if the default route changes.
     *
     * <p>In case of overflow, this method returns 0.
     *
     * <p>In case of problem, returns -1
     *
     * @return
     */
    public int getAvailableSpaceForAid() {
        try {
            if (sService == null) {
                Log.e(TAG, "getAvailableSpaceForAid() - sService = null");
                return -1;
            }
            Log.d(TAG, "sService.getAvailableSpaceForAid()");
            return sService.getAvailableSpaceForAid();
        } catch (RemoteException e) {
            // attemptDeadServiceRecovery(e);
            Log.e(TAG, "getAvailableSpaceForAid() - e = " + e.toString());
            return -1;
        }
    }
}
