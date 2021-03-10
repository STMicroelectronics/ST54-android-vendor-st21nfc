# Device configuration file to be included from device.mk file, e.g.
#
#   -include vendor/st/nfc/st21nfc/NfcDeviceConfig.mk

## GSMA support
PRODUCT_PROPERTY_OVERRIDES += persist.st_nfc_ignore_modem=1
PRODUCT_PROPERTY_OVERRIDES += persist.st_nfc_defaut_se=SIM1

#APDU gate support for eSE (if not set, use SPI eSE)
PRODUCT_PROPERTY_OVERRIDES += persist.st_use_apdu_gate_ese=1

#Debug traces support
PRODUCT_PROPERTY_OVERRIDES += persist.st_nfc_debug=1

#support for ST extensions
PRODUCT_PROPERTY_OVERRIDES += persist.st_nfc_settings_service=1

################################################
## Configuration for ST NFC packages
PRODUCT_PACKAGES += \
#    android.hardware.nfc@1.2-service.st \
#    android.hardware.secure_element@1.0-service.st \
    libstnfc-nci \
    libstnfc_nci_jni \
    Nfc_st \
    com.st.android.nfc_extensions \
    nfc_nci.st21nfc.default \
#    SecureElement_st \
    Tag \
    libnfc_st_dta_jni \
    libnfc_st_dta \
    libdtaNfaProvider \
	STNFCDta \
	ixitdata.xml

   
PRODUCT_COPY_FILES += \
   frameworks/native/data/etc/android.hardware.nfc.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.nfc.xml:st \
   frameworks/native/data/etc/android.hardware.nfc.hce.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.nfc.hce.xml:st \
   frameworks/native/data/etc/android.hardware.nfc.hcef.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.nfc.hcef.xml:st \
   frameworks/native/data/etc/com.nxp.mifare.xml:$(TARGET_COPY_OUT_SYSTEM_EXT)/etc/permissions/com.nxp.mifare.xml:st \
   frameworks/native/data/etc/android.hardware.nfc.uicc.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.nfc.uicc.xml:st \
   frameworks/native/data/etc/android.hardware.nfc.ese.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.nfc.ese.xml:st \
   vendor/st/nfc/st21nfc/frameworks/NfcExtensions/com.st.android.nfc_extensions.xml:$(TARGET_COPY_OUT_SYSTEM_EXT)/etc/permissions/com.st.android.nfc_extensions.xml:st \
   vendor/st/nfc/st21nfc/conf/nfcee_access.xml:$(TARGET_COPY_OUT_SYSTEM_EXT)/etc/nfcee_access.xml:st

PRODUCT_COPY_FILES += \
   vendor/st/nfc/st21nfc/conf/libnfc-hal-st.conf:$(TARGET_COPY_OUT_VENDOR)/etc/libnfc-hal-st.conf:st

PRODUCT_COPY_FILES += \
   vendor/st/nfc/st21nfc/conf/libnfc-nci.conf:$(TARGET_COPY_OUT_VENDOR)/etc/libnfc-nci.conf:st

# Init.rc files
PRODUCT_COPY_FILES += \
   vendor/st/nfc/st21nfc/conf/init.vendor.st21nfc.rc:$(TARGET_COPY_OUT_SYSTEM_EXT)/etc/init/hw/init.stnfc.rc:st

# RF configuration (project dependent)
#PRODUCT_COPY_FILES += \
#    vendor/st/nfc/st21nfc/conf/st21nfc_conf.txt:$(TARGET_COPY_OUT_VENDOR)/etc/st21nfc_conf.txt:st
