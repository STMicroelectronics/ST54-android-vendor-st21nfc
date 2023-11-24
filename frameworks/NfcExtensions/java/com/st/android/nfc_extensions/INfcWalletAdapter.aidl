/*
 * Copyright (C) 2019 ST Microelectronics S.A.
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

import com.st.android.nfc_extensions.IIntfActivatedNtfCallback;
import com.st.android.nfc_extensions.INfcWalletLogCallback;
import com.st.android.nfc_extensions.INfcWalletPollingLoopCallback;
import com.st.android.nfc_extensions.INfcWalletRawCallback;
import com.st.android.nfc_extensions.INfceeActionNtfCallback;

/**
 * @hide
 */
interface INfcWalletAdapter
{
    /* Force to keep eSE SWP interface active.
      -- use for operations in applets that require HCI services (connectivity
      gate) when accessed over SPI. This has no effect if APDU gate is opened */
    boolean keepEseSwpActive(boolean enable);

    /* NFC firmware logs support */
    boolean registerStLogCallback(INfcWalletLogCallback cb);
    boolean unregisterStLogCallback();

    /* Observer mode support (do not reply any message in listen mode) */
    boolean setObserverMode(boolean enable);

    /* Interfaces for controlling the behavior of the NFC solution for specific
     * applets requirements, controlled by a wallet generally.
     *
     * These methods are only applied when the eSE is active.
     */
    /* Do not answer to readers in specific technologies */
    boolean setMuteTech(boolean muteA, boolean muteB, boolean muteF);

    /* Change the RF parameter set to use for initial response.
     * Set of parameters are rotated when the parameter is false,
     * and reset to default if the parameter is true.
     * The value is not persistent, i.e. after NFC off / on the default
     * parameter set is used. */
    boolean rotateRfParameters(boolean reset);

    boolean setSEFelicaCardEnabled(boolean status) ;

    /* RF_NFCEE_ACTION_NTF listener */
    boolean registerNfceeActionNtfCallback(INfceeActionNtfCallback cb);
    boolean unregisterNfceeActionNtfCallback();

    /* For a temporary SAK value as if the eSE had set it */
    boolean setForceSAK(boolean enabled, int sak);

    /* Control sequence for card switching
      call this before (true) and after (false) sending card activate /
        deactivate commands to eSE.
      This method has been replaced by seteSEInCardSwitchingExt, it is not
      recommended to use seteSEInCardSwitching anymore.

      This method stops card emulation during the switch to ensure no dynamic UID.
      It also ensures SWP is running for parameters updates if OMAPI is using SPI.
      The proper sequence is :
       - call seteSEInCardSwitchingExt(true, 2);
       - send previous card deactivate command over OMAPI
       - wait for SW 90 00.
       - wait 250ms // -- ST54H only
       - send new card activate command over OMAPI
       - wait for SW 90 00
       - call seteSEInCardSwitchingExt(false, 2);

      The following sequence is also possible, although a bit slower:
       - call seteSEInCardSwitchingExt(true, 1);
       - send previous card deactivate command over OMAPI
       - wait for SW 90 00.
       - call seteSEInCardSwitchingExt(false, 1);
       - ...  // be careful, here CE is enabled, so transactions with dynamic UID may happen
       - call seteSEInCardSwitchingExt(true, 1);
       - send new card activate command over OMAPI
       - wait for SW 90 00
       - call seteSEInCardSwitchingExt(false, 1);

      This method shall also be called when install or delete card (default selectable) APDU sent over SPI,
      in order to ensure the parameters update is propagated to CLF properly:
       - call seteSEInCardSwitchingExt(true, 1);
       - send the install or delete card APDU [8x E4] or [8x E6] (you can do this only for the last APDU if it is a sequence)
       - wait for SW 90 00.
       - call seteSEInCardSwitchingExt(false, 1);

      Finally if CRS SET STATUS is used (opens or closes all the RF gates) the flow is:
       - call seteSEInCardSwitchingExt(true, 3);
       - send the SET STATUS command to the CRS to turn ON or OFF the contactless.
       - wait for SW 90 00.
       - call seteSEInCardSwitchingExt(false, 3);
       */
    boolean seteSEInCardSwitching(boolean inswitching);

    /*****************************
        RAW mode for ISO14443-3
     *****************************/
    /* management of callback for raw mode authorization */
    boolean registerRawRfAuthCallback(INfcWalletRawCallback cb);
    boolean unregisterRawRfAuthCallback();

    /* Open logical channel with eSE, send command to CLF to start monitoring.
      if auth is successful (INfcWalletRawCallback), the auth will remain valid
      for duration. Logical channel will be closed automatically when callback
      is posted. This API can only be called when the foreground application
      started reader mode A only already and when an INfcWalletRawCallback is
      already registered. */
    boolean rawRfAuth(int duration);

    /* Exchange data with the eSE. First command shall be SELECT ISD. */
    byte[] rawSeAuth(in byte[] data);

    /* When RAW mode is authorized, use this APIs to start or stop it */
    boolean rawRfMode(boolean enable);

    /* RAW card access from JNI directly */
    byte[]  rawJniSeq(int i, in byte[] ba);
    /*****************************
      end of RAW mode for ISO14443-3
     *****************************/

    /* Get content of NFC snoop buffer */
    byte[]  getLogBuffer();

    /* RF_INTF_ACTIVATED_NTF listener */
    boolean registerIntfActivatedNtfCallback(IIntfActivatedNtfCallback cb);
    boolean unregisterIntfActivatedNtfCallback();

    /* Allow specify number of CRS updates in seteSEInCardSwitching
      -- see seteSEInCardSwitching comments above */
    boolean seteSEInCardSwitchingExt(boolean inswitching, int nbOp);

    /* Readers polling loop spy support */
    boolean registerPollingLoopCallback(INfcWalletPollingLoopCallback cb);
    boolean unregisterPollingLoopCallback();
}
