/*
 * Copyright (C) 2013 ST Microelectronics S.A.
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
 *
 * Provide extensions for the ST implementation of the NFC stack
 */

package com.st.android.nfc_extensions;

import android.nfc.NfcAdapter;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class contains a set of APIs for the purpose of testing the ST21NFCD chip. */
public final class NfcAdapterStExtensions {

    private static final String TAG = "NfcAdapterStExtensions";

    public static final String SERVICE_NAME = "nfc.st_ext";

    /* NCI 2.0 - Begin */
    private static final String frameworkVersion = "Framework version 01.7.0.00";
    /* NCI 2.0 - End */

    private static final String tagVersion = "C.1.17";

    /**
     * {@link Intent} action to be declared by BroadcastReceiver.
     *
     * <p>This {@link Intent} is broadcasted every time a CE operated on a SE causes the ST21NFCB to
     * return some data to the device host on the connectivity gate (using HCI over NCI). The {@link
     * Intent} contains the identity of the SE on which the CE took place ( {@link
     * EXTRA_HOST_ID_FOR_EVT}).
     */
    public static final String ACTION_EVT_TRANSACTION_RX =
            "android.nfc.action.TRANSACTION_DETECTED";
    /**
     * The name of the meta-data element that contains the identity of the SE on which the CE
     * operation took place when the {@link Intent} {@link ACTION_EVT_TRANSACTION_RX} is
     * broadcasted.
     */
    public static final String EXTRA_HOST_ID_FOR_EVT = "android.nfc.extra.HOST_ID";

    /**
     * {@link Intent} action to be declared by BroadcastReceiver.
     *
     * <p>This {@link Intent} is broadcasted every time a CE operated on a SE has used select AID
     * command. The {@link Intent} contains as extra the identity of the SE on which the CE took
     * place ( {@link EXTRA_HOST_ID_FOR_AID_SEL}) and which AID was selected ( {@link
     * EXTRA_AID_SELECTION}).
     */
    public static final String ACTION_AID_SELECTION = "android.nfc.action.AID_SELECTION";
    /**
     * The name of the meta-data element that contains the identity of the SE on which the CE
     * operation took place when the {@link Intent} {@link ACTION_AID_SELECTION} is broadcasted.
     */
    public static final String EXTRA_HOST_ID_FOR_AID_SEL = "android.nfc.extra.HOST_ID_AID_SEL";
    /**
     * The name of the meta-data element that contains the AID value used during CE when the {@link
     * Intent} {@link ACTION_AID_SELECTION} is broadcasted.
     */
    public static final String EXTRA_AID_SELECTION = "android.nfc.extra.AID_SLECTION";

    /**
     * {@link Intent} action to be declared by BroadcastReceiver.
     *
     * <p>This {@link Intent} is broadcasted every time a CE operated on a SE has been taken
     * following a technology routing decision. The {@link Intent} contains as extra the identity of
     * the SE on which the CE took place ( {@link EXTRA_HOST_ID_FOR_TECHPROTO_SEL}) and which was
     * the technology used ({@link EXTRA_TECHPROTO_SEL})
     */
    public static final String ACTION_TECHNOLOGY_ROUTING_DECISION =
            "android.nfc.action.TECHNOLOGY_ROUTING";
    /**
     * {@link Intent} action to be declared by BroadcastReceiver.
     *
     * <p>This {@link Intent} is broadcasted every time a CE operated on a SE has been taken
     * following a protocol routing decision. The {@link Intent} contains as extra the identity of
     * the SE on which the CE took place ( {@link EXTRA_HOST_ID_FOR_TECHPROTO_SEL}) and which was
     * the protocol used ({@link EXTRA_TECHPROTO_SEL})
     */
    public static final String ACTION_PROTOCOL_ROUTING_DECISION =
            "android.nfc.action.PROTCOL_ROUTING";
    /**
     * The name of the meta-data element that contains the identity of the SE on which the CE
     * operation took place when the {@link Intent} {@link ACTION_TECHPROTO_ROUTING_DECISION} is
     * broadcasted.
     */
    public static final String EXTRA_HOST_ID_FOR_TECHPROTO_SEL =
            "android.nfc.extra.HOST_ID_TECHPROTO_SEL";
    /**
     * The name of the meta-data element that contains the decision routing trigger used during CE
     * when the {@link Intent} {@link ACTION_TECHPROTO_ROUTING_DECISION} is broadcasted.
     */
    public static final String EXTRA_TECHPROTO_VALUE = "android.nfc.extra.TECHPROTO_VAL";

    // protected by NfcAdapterStExtensions.class, and final after first construction,
    // except for attemptDeadServiceRecovery() when NFC crashes - we accept a
    // best effort recovery
    private static INfcAdapterStExtensions sInterface = null;
    // contents protected by NfcAdapterExtras.class
    private static final HashMap<NfcAdapter, NfcAdapterStExtensions> sNfcStExtensions =
            new HashMap();

    /**
     * Constructor for the {@link NfcAdapterStExtensions}
     *
     * @return
     */
    public NfcAdapterStExtensions() {
        sInterface = getNfcAdapterStExtensionsInterface();
    }

    /**
     * Constructor for the {@link NfcAdapterStExtensions}
     *
     * @param i interface retrieved separately by the calling app with
     *     getNfcAdapterStExtensionsInterface
     * @return
     */
    public NfcAdapterStExtensions(INfcAdapterStExtensions i) {
        sInterface = i;
    }

    /** NFC service dead - attempt best effort recovery */
    void attemptDeadServiceRecovery(Exception e) {
        Log.e(TAG, "NFC Adapter ST Extensions dead - attempting to recover");

        IBinder b = ServiceManager.getService(SERVICE_NAME);
        if (b == null) throw new RuntimeException("Cannot retrieve service :" + SERVICE_NAME);
        sInterface = INfcAdapterStExtensions.Stub.asInterface(b);
    }

    public static INfcAdapterStExtensions getNfcAdapterStExtensionsInterface() {
        if (sInterface == null) {
            IBinder b = ServiceManager.getService(SERVICE_NAME);
            if (b == null) throw new RuntimeException("Cannot retrieve service :" + SERVICE_NAME);
            sInterface = INfcAdapterStExtensions.Stub.asInterface(b);
        }
        return sInterface;
    }

    /**
     * Get the firmware version of the ST21NFCD chip
     *
     * @return An object of the type {@link FwVersion} that contains information about the FW
     *     version.
     */
    public FwVersion getFirmwareVersion() {
        byte[] result = null;
        try {
            result = sInterface.getFirmwareVersion();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        FwVersion fwVersion = new FwVersion(result);
        return fwVersion;
    }

    /**
     * Get the HW version of the ST21NFCD chip
     *
     * @return An object of the type {@link HwInfo}.
     *     <p>This object contains information about the ST21NFCB HW version.
     */
    public HwInfo getHWVersion() {
        byte[] result = null;
        try {
            result = sInterface.getHWVersion();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        HwInfo hwInfo = new HwInfo(result);
        return hwInfo;
    }

    /**
     * This API sets the current Tag Detector status in the CLF configuration parameters.
     *
     * <p>
     *
     * @param status True if the Tag Detector shall be enabled and false otherwise.
     */
    public void setTagDetectorStatus(boolean status) {
        Log.i(TAG, "setTagDetectorStatus()");
        int byteNb, bitNb, regAdd;

        // Tag Detector mapping changed with new FW version
        byte[] result = null;
        try {
            result = sInterface.getFirmwareVersion();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        if ((result[0] == 0x01) && (result[1] == 0x00)) {
            // FW 1.0.xxxx
            byteNb = 0;
            bitNb = 0;
            regAdd = 0x11;
        } else {
            byteNb = 0;
            bitNb = 4;
            regAdd = 0x01;
        }

        status = (!status);

        try {
            sInterface.setProprietaryConfigSettings(regAdd, byteNb, bitNb, status);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }

    /**
     * This API retrieves the current Tag detector status from the CLF configuration parameters.
     *
     * <p>
     *
     * @return true if the tag detector is enabled and false otherwise.
     */
    public boolean getTagDetectorStatus() {
        Log.i(TAG, "getTagDetectorStatus()");
        int byteNb, bitNb, regAdd;

        // Tag Detector mapping changed with new FW version
        byte[] result = null;
        try {
            result = sInterface.getFirmwareVersion();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        if ((result[0] == 0x01) && (result[1] == 0x00)) {
            // FW 1.0.xxxx
            byteNb = 0;
            bitNb = 0;
            regAdd = 0x11;
        } else {
            byteNb = 0;
            bitNb = 4;
            regAdd = 0x01;
        }

        boolean status = false;

        try {
            status = sInterface.getProprietaryConfigSettings(regAdd, byteNb, bitNb);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        return (!status);
    }

    /**
     * This API retrieves the list and status of pipes for a given host.
     *
     * <p>
     *
     * @param hostId Identity of the host to investigate
     * @return a structure {@link PipesInfo} containing the list and status of the pipes attached to
     *     the host.
     */
    public PipesInfo getPipesInfo(int hostId) {
        Log.i(TAG, "getPipesInfo() - for host " + hostId);
        int nbPipes = 0;
        byte[] list = new byte[10];
        byte[] info = new byte[5];

        try {
            nbPipes = sInterface.getPipesList(hostId, list);
            Log.i(TAG, "getPipesInfo() - Found " + nbPipes + " for host " + hostId);
            PipesInfo retrievedInfo = new PipesInfo(nbPipes);

            for (int i = 0; i < nbPipes; i++) {
                Log.i(TAG, "getPipesInfo() - retrieving info for pipe " + list[i]);
                sInterface.getPipeInfo(hostId, list[i], info);
                retrievedInfo.setPipeInfo(list[i], info);
            }

            return retrievedInfo;
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        // Log.i(TAG, "getPipesInfo() - Found " + nbPipes + "for host " + hostId);

        return null;
    }

    /**
     * This API retrieves the ATR of the eSE.
     *
     * <p>
     *
     * @return an array of bytes containing the ATR.
     */
    public byte[] getATR() {
        Log.i(TAG, "getATR()");

        try {
            return sInterface.getATR();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        return null;
    }

    public static final String HCI_HOST_UICC1 = "SIM1";
    public static final String HCI_HOST_UICC2 = "SIM2";
    public static final String HCI_HOST_ESE = "ESE";
    public static final String HCI_HOST_EUICCSE = "eUICC-SE";
    public static final String HCI_HOST_DHSE = "DHSE";
    public static final String HCI_HOST_ACTIVE = "ACTIVE";
    public static final String HCI_HOST_INACTIVE = "INACTIVE";
    public static final String HCI_HOST_UNRESPONSIVE = "UNRESPONSIVE";

    public static final int NFA_EE_MAX_EE_SUPPORTED = 5;

    public Map<String, String> getAvailableHciHostList() {
        Map<String, String> result = new HashMap<String, String>();
        byte[] nfceeId = new byte[NFA_EE_MAX_EE_SUPPORTED];
        byte[] conInfo = new byte[NFA_EE_MAX_EE_SUPPORTED];
        int nbHost = 0;
        int i;

        Log.i(TAG, "getAvailableHciHostList()");

        try {
            nbHost = sInterface.getAvailableHciHostList(nfceeId, conInfo);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        for (i = 0; i < nbHost; i++) {
            Log.i(
                    TAG,
                    "getHostList() - nfceeId["
                            + i
                            + "] = "
                            + nfceeId[i]
                            + ", conInfo["
                            + i
                            + "] = "
                            + conInfo[i]);
        }

        String nfcee;
        String status;

        for (i = 0; i < nbHost; i++) {
            nfcee = "";
            status = "";

            switch (nfceeId[i]) {
                case (byte) 0x81:
                    nfcee = HCI_HOST_UICC1;
                    break;

                case (byte) 0x82:
                    nfcee = HCI_HOST_ESE;
                    break;

                case (byte) 0x83:
                case (byte) 0x85:
                    nfcee = HCI_HOST_UICC2;
                    break;

                case (byte) 0x84:
                    nfcee = HCI_HOST_DHSE;
                    break;
                case (byte) 0x86:
                    nfcee = HCI_HOST_EUICCSE;
                    break;
            }

            switch (conInfo[i]) {
                case 0x00: // Active
                    status = HCI_HOST_ACTIVE;
                    break;
                case 0x01: // Inactive
                    status = HCI_HOST_INACTIVE;
                    break;
                case 0x02: // Unresponsive
                    status = HCI_HOST_UNRESPONSIVE;
                    break;
            }

            result.put(nfcee, status);
        }

        return result;
    }

    /**
     * This API gets status of the 2-UICC mode.
     *
     * <p>
     *
     * <p>
     *
     * @return status True if the dual uicc mode is enabled and false otherwise.
     */
    public boolean getDualSimFeature() {
        boolean status = false;

        Log.i(TAG, "getDualSimFeature()");
        try {
            status = sInterface.getBitPropConfig(0x02, 0x00, 3);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        return status;
    }

    /**
     * This API sets the NFCC in 2 UICC mode.
     *
     * <p>
     *
     * <p>
     *
     * @param status True if the dual uicc mode shall be enabled and false otherwise.
     */
    public void setDualSimFeature(boolean status) {
        Log.i(TAG, "setDualSimFeature(" + status + ")");

        try {
            sInterface.setBitPropConfig(0x02, 0x00, 3, status);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }

    /**
     * This API allows to force all the routings to a given NFCEE id
     *
     * <p>
     */
    public void forceRouting(int nfceeId, int PowerState) {
        try {
            sInterface.forceRouting(nfceeId, 0);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }

    /**
     * This API allows to stop forcing all the routings to a given NFCEE id. The routing set by
     * SET_LISTEN_MODE_ROUTING is being applied.
     *
     * <p>
     */
    public void stopforceRouting() {
        try {
            sInterface.stopforceRouting();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }

    public byte getNfceeHwConfig() {
        byte conf = 0;
        boolean status = false;

        Log.i(TAG, "getNfceeHwConfig()");

        try {
            /* bit 3, 4, 6 and 7 are exclusive
             * all to 0 : UICC + eSE (conf = 0)
             * bit 3 : Dual UICC (conf = 1)
             * bit 4 : Dual UICC + eSE (conf = 2)
             * bit 6 : Dual UICC + InSE (conf = 4)
             * bit 7 : UICC + SPI-SE (conf = 3) */
            status = sInterface.getBitPropConfig(0x02, 0x00, 3);
            if (status) conf = 1;
            else {
                status = sInterface.getBitPropConfig(0x02, 0x00, 4);
                if (status) conf = 2;
                else {
                    status = sInterface.getBitPropConfig(0x02, 0x00, 7);
                    if (status) conf = 3;
                    else {
                        status = sInterface.getBitPropConfig(0x02, 0x00, 6);
                        if (status) conf = 4;
                    }
                }
            }
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        return conf;
    }

    public void setNfceeHwConfig(byte conf) {
        Log.i(TAG, "setNfceeHwConfig(" + conf + ")");

        try {
            switch (conf) {
                case 1:
                    sInterface.setBitPropConfig(0x02, 0x00, 3, true);
                    sInterface.setBitPropConfig(0x02, 0x00, 4, false);
                    sInterface.setBitPropConfig(0x02, 0x00, 6, false);
                    sInterface.setBitPropConfig(0x02, 0x00, 7, false);
                    break;
                case 2:
                    sInterface.setBitPropConfig(0x02, 0x00, 3, false);
                    sInterface.setBitPropConfig(0x02, 0x00, 4, true);
                    sInterface.setBitPropConfig(0x02, 0x00, 6, false);
                    sInterface.setBitPropConfig(0x02, 0x00, 7, false);
                    break;
                case 3:
                    sInterface.setBitPropConfig(0x02, 0x00, 3, false);
                    sInterface.setBitPropConfig(0x02, 0x00, 4, false);
                    sInterface.setBitPropConfig(0x02, 0x00, 6, false);
                    sInterface.setBitPropConfig(0x02, 0x00, 7, true);
                    break;
                case 4:
                    sInterface.setBitPropConfig(0x02, 0x00, 3, false);
                    sInterface.setBitPropConfig(0x02, 0x00, 4, false);
                    sInterface.setBitPropConfig(0x02, 0x00, 6, true);
                    sInterface.setBitPropConfig(0x02, 0x00, 7, false);
                    break;
                default:
                    sInterface.setBitPropConfig(0x02, 0x00, 3, false);
                    sInterface.setBitPropConfig(0x02, 0x00, 4, false);
                    sInterface.setBitPropConfig(0x02, 0x00, 6, false);
                    sInterface.setBitPropConfig(0x02, 0x00, 7, false);
                    break;
            }
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }

    /**
     * This API allows to set Nci params
     *
     * <p>
     */
    public void setNciConfig(int paramId, byte[] param) {
        Log.i(TAG, "setNciParam(" + paramId + ")");

        try {
            sInterface.setNciConfig(paramId, param);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }

    /**
     * This API gets the value of a NCI parameter.
     *
     * <p>
     *
     * <p>
     *
     * @param paramId NCI parameter id.
     * @return param NCI parameter value.
     */
    public byte[] getNciConfig(int paramId) {
        Log.i(TAG, "getNciParam(" + paramId + ")");

        try {
            return sInterface.getNciConfig(paramId);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        return null;
    }

    /**
     * This API sets a ST proprietary configuration in the NFCC.
     *
     * <p>
     *
     * <p>
     *
     * @param configSubSetId The identifier of the configuration sub-set.
     * @param paramId The identifier of the specific configuration parameter
     * @param param The value of the specific configuration parameter
     */
    public void sendPropSetConfig(int configSubSetId, int paramId, byte[] param) {
        Log.i(TAG, "sendPropSetConfig(" + configSubSetId + ")");

        try {
            sInterface.sendPropSetConfig(configSubSetId, paramId, param);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }

    /**
     * This API sets several ST proprietary configurations in the NFCC.
     *
     * <p>
     *
     * <p>
     *
     * @param configSubSetIds Array of identifier of the configuration sub-set.
     * @param paramIds Array of identifier of the specific configuration parameter
     * @param params list of byte[] values of the specific configuration parameter
     */
    public void sendPropSetConfig(
            List<Integer> configSubSetIds, List<Integer> paramIds, List<byte[]> params) {
        Log.i(TAG, "sendPropSetConfig(" + configSubSetIds.size() + " values)");

        try {
            int[] subsets = new int[configSubSetIds.size()];
            int[] ids = new int[paramIds.size()];
            List<ByteArray> ba = new ArrayList<ByteArray>();

            /* Convert */
            for (int i = 0; i < configSubSetIds.size(); i++) {
                subsets[i] = configSubSetIds.get(i);
            }
            for (int i = 0; i < paramIds.size(); i++) {
                ids[i] = paramIds.get(i);
            }
            for (int i = 0; i < params.size(); i++) {
                ba.add(new ByteArray(params.get(i)));
            }

            /* Send to service */
            sInterface.sendPropSetConfigs(subsets, ids, ba);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }

    /**
     * This API gets a ST proprietary configuration in the NFCC.
     *
     * <p>
     *
     * <p>
     *
     * @param configSubSetId The identifier of the configuration sub-set.
     * @param paramId The identifier of the specific configuration parameter
     * @param param The value of the specific configuration parameter
     */
    public byte[] sendPropGetConfig(int configSubSetId, int paramId) {
        Log.i(TAG, "sendPropGetConfig(" + configSubSetId + ")");

        try {
            return sInterface.sendPropGetConfig(configSubSetId, paramId);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return null;
    }
    /**
     * This API allows to send Proprietary test commands.
     *
     * <p>
     */
    public byte[] sendPropTestCmd(int subCode, byte[] paramTx) {
        Log.i(TAG, "sendPropTestCmd(" + subCode + ")");

        try {
            return sInterface.sendPropTestCmd(subCode, paramTx);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        return null;
    }

    public byte[] getCustomerData() {
        Log.i(TAG, "getCustomerData()");
        try {
            return sInterface.getCustomerData();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        return null;
    }

    /**
     * This API sets the low power mode in the UICC configuration register.
     *
     * <p>
     *
     * <p>
     *
     * @param status True if the Low power shall be enabled and false otherwise.
     */
    public void setUiccLowPowerStatus(boolean status) {
        Log.i(TAG, "setUiccLowPowerStatus()");
        int byteNb, bitNb, regAdd;

        byteNb = 0;
        bitNb = 3;
        regAdd = 0x0A;

        status = (!status);

        try {
            sInterface.setProprietaryConfigSettings(regAdd, byteNb, bitNb, status);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }

    /**
     * This API retrieves the current low power mode in the UICC configuration register.
     *
     * <p>
     *
     * @return true if the Low power is enabled and false otherwise.
     */
    public boolean getUiccLowPowerStatus() {
        Log.i(TAG, "getUiccLowPowerStatus()");
        int byteNb, bitNb, regAdd;

        byteNb = 0;
        bitNb = 3;
        regAdd = 0x0A;

        boolean status = false;

        try {
            status = sInterface.getProprietaryConfigSettings(regAdd, byteNb, bitNb);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        return (!status);
    }

    public INfcWalletAdapter getNfcWalletAdapterInterface() {
        Log.i(TAG, "getNfcWalletAdapterInterface()");
        try {
            return sInterface.getNfcWalletAdapterInterface();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        return null;
    }

    /**
     * This API sets proprietary HCE parameters or restores the default configuration
     *
     * <p>
     *
     * @param setConfig True if proprietary HCE parameters to be set False if default HCE paramaters
     *     must be restored
     * @param bitFrameSdd LA_BIT_FRAME_SDD NCI parameter
     * @param platformConfig LA_PLATFORM_CONFIG NCI parameter
     * @param selInfo LA_SEL_INFO NCI parameter
     * @param nfcid1 LA_NCFID1 NCI parameter
     * @param rats LI_A_RATS_TB1 NCI parameter
     * @param histBytes LI_A_HIST_BY NCI parameter
     */
    public void programHceParameters(
            boolean setConfig,
            byte bitFrameSdd,
            byte platformConfig,
            byte selInfo,
            byte[] nfcid1,
            byte rats,
            byte[] histBytes) {
        Log.i(TAG, "programHceParameters()");
        try {
            sInterface.programHceParameters(
                    setConfig, bitFrameSdd, platformConfig, selInfo, nfcid1, rats, histBytes);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }

    public INfcSettingsAdapter getNfcSettingsAdapterInterface() {
        Log.i(TAG, "getNfcSettingsAdapterInterface()");
        try {
            return sInterface.getNfcSettingsAdapterInterface();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        return null;
    }

    public INfcChargingAdapter getNfcChargingAdapterInterface() {
        Log.i(TAG, "getNfcChargingAdapterInterface()");
        try {
            return sInterface.getNfcChargingAdapterInterface();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        return null;
    }

    public boolean startNfcCharging(boolean switchon) {
        Log.i(TAG, "startNfcCharging(" + switchon + ")");
        try {
            return sInterface.startNfcCharging(switchon);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return false;
    }

    public void seteSeReaderMode(boolean start) {
        Log.i(TAG, "seteSeReaderMode(" + start + ")");
        try {
            sInterface.seteSeReaderMode(start);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }

    public void registerNfcStackRestartCb(INfcStExtensionsRestartCb cb) {
        Log.i(TAG, "registerNfcStackRestartCb()");
        try {
            sInterface.registerNfcStackRestartCb(cb);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }

    public void unregisterNfcStackRestartCb() {
        Log.i(TAG, "unregisterNfcStackRestartCb()");
        try {
            sInterface.unregisterNfcStackRestartCb();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }

    public INfcNdefNfceeAdapter getNfcNdefNfceeAdapterInterface() {
        Log.i(TAG, "getNfcNdefNfceeAdapterInterface()");
        try {
            return sInterface.getNfcNdefNfceeAdapterInterface();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        return null;
    }
}
