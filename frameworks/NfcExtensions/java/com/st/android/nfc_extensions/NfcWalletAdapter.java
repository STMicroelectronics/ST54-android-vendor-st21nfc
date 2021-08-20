/*
 * Copyright (C) 2019 ST Microelectronics S.A.
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

public final class NfcWalletAdapter {
    private static final String TAG = "NfcWalletAdapter";

    private static INfcWalletAdapter sInterface = null;

    /**
     * Constructor for the {@link NfcWalletAdapter}
     *
     * @param intf a {@link INfcWalletAdapter}, must not be null
     * @return
     */
    public NfcWalletAdapter(INfcWalletAdapter intf) {
        sInterface = intf;
    }

    /** NFC service dead - attempt best effort recovery */
    void attemptDeadServiceRecovery(Exception e) {
        Log.e(TAG, "NFC Wallet Adapter ST Extensions dead - try recover");
        try {
            sInterface =
                    NfcAdapterStExtensions.getNfcAdapterStExtensionsInterface()
                            .getNfcWalletAdapterInterface();
        } catch (RemoteException f) {
            // give up
            Log.e(TAG, "Failed to recover");
        }
    }

    public boolean keepEseSwpActive(boolean enable) {
        boolean result = false;
        try {
            result = sInterface.keepEseSwpActive(enable);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public boolean setMuteTech(boolean muteA, boolean muteB, boolean muteF) {
        boolean result = false;
        try {
            result = sInterface.setMuteTech(muteA, muteB, muteF);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public boolean setObserverMode(boolean enable) {
        boolean result = false;
        try {
            result = sInterface.setObserverMode(enable);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public boolean registerStLogCallback(INfcWalletLogCallback cb) {
        boolean result = false;
        try {
            result = sInterface.registerStLogCallback(cb);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public boolean unregisterStLogCallback() {
        boolean result = false;
        try {
            result = sInterface.unregisterStLogCallback();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public boolean rotateRfParameters(boolean reset) {
        boolean result = false;
        try {
            result = sInterface.rotateRfParameters(reset);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public boolean setSEFelicaCardEnabled(boolean status) {
        boolean result = false;
        try {
            result = sInterface.setSEFelicaCardEnabled(status);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public boolean registerNfceeActionNtfCallback(INfceeActionNtfCallback cb) {
        boolean result = false;
        try {
            result = sInterface.registerNfceeActionNtfCallback(cb);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public boolean unregisterNfceeActionNtfCallback() {
        boolean result = false;
        try {
            result = sInterface.unregisterNfceeActionNtfCallback();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public boolean registerIntfActivatedNtfCallback(IIntfActivatedNtfCallback cb) {
        boolean result = false;
        try {
            result = sInterface.registerIntfActivatedNtfCallback(cb);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public boolean unregisterIntfActivatedNtfCallback() {
        boolean result = false;
        try {
            result = sInterface.unregisterIntfActivatedNtfCallback();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public boolean setForceSAK(boolean enabled, int sak) {
        boolean result = false;
        try {
            result = sInterface.setForceSAK(enabled, sak);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public boolean seteSEInCardSwitching(boolean inswitching) {
        boolean result = false;
        try {
            result = sInterface.seteSEInCardSwitching(inswitching);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public boolean seteSEInCardSwitchingExt(boolean inswitching, int nbOp) {
        boolean result = false;
        try {
            result = sInterface.seteSEInCardSwitchingExt(inswitching, nbOp);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    /** *************************** RAW mode for ISO14443-3 *************************** */
    public boolean registerRawRfAuthCallback(INfcWalletRawCallback cb) {
        boolean result = false;
        try {
            result = sInterface.registerRawRfAuthCallback(cb);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public boolean unregisterRawRfAuthCallback() {
        boolean result = false;
        try {
            result = sInterface.unregisterRawRfAuthCallback();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public boolean rawRfAuth(int duration) {
        boolean result = false;
        try {
            result = sInterface.rawRfAuth(duration);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public byte[] rawSeAuth(byte[] data) {
        byte[] result = null;
        try {
            result = sInterface.rawSeAuth(data);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public boolean rawRfMode(boolean enable) {
        boolean result = false;
        try {
            result = sInterface.rawRfMode(enable);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    public byte[] rawJniSeq(int i, byte[] ba) {
        byte[] result = null;
        try {
            result = sInterface.rawJniSeq(i, ba);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
        return result;
    }

    /**
     * This API return the content of the NFC snoop buffer
     *
     * <p>
     *
     * @return content of NFC snoop buffer.
     */
    public byte[] getLogBuffer() {
        try {
            return sInterface.getLogBuffer();
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }

        return null;
    }
}
