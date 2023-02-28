/*
 *  Copyright (C) 2022 ST Microelectronics S.A.
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


/**
  * {@hide}
   */
interface INfcNdefNfceeAdapter {
  /* Write the given data stored in the given
     data buffer to the specified File Id.
     Returns false if an error occured,
     true if the write operation was OK */
  boolean writeNdefData(in byte[] fileId, in byte[] data);

  /* Read the data stored in
     the specified File Id.
     Returns the read data is OK,
     a null pointer if KO */
  byte[] readNdefData(in byte[] fileId);

  /* Make the data stored the specified File Id
     writable or not.
     Returns true if OK,
     false if KO */
  boolean lockNdefData(in byte[] fileId, boolean lock);

  /* Check if the data stored the specified File Id
     is writable or not.
     Returns true if OK,
     false if KO */
  boolean isLockedNdefData(in byte[] fileId);

  /* Clear the content of the specified File Id
     Returns true if OK,
     false if KO */
  boolean clearNdefData(in byte[] fileId);

  /* Read the content of the CC
     file for the NDEF-NFCEE.
     Returns the content of the CC file is OK,
     a null pointer if KO
     NOTE: This API should be called first
     or all calls to the other APIs will retrun with status KO */
  byte[] readT4tCcfile();
}