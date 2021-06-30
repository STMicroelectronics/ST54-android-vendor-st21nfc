# Device configuration file to be included from device.mk file, e.g.
#
#   -include vendor/st/nfc/st21nfc/NfcDeviceConfig.mk

######################################################################
##########################  SYSTEM image  ############################
######################################################################

#PRODUCT_PROPERTY_OVERRIDES += persist.st_nfc_ignore_modem=1
#PRODUCT_PROPERTY_OVERRIDES += persist.st_nfc_defaut_se=SIM1

# If using ST's dual-implementation SecureElement service, the following
# property controls the channel to use with eSE. Default is SPI.
# PRODUCT_PROPERTY_OVERRIDES += persist.st_use_apdu_gate_ese=1

################################################
## Configuration for ST NFC packages
PRODUCT_PACKAGES += \
    libstnfc-nci \
    libstnfc_nci_jni \
    Nfc_st \
    com.st.android.nfc_extensions \
    com.st.android.nfc_extensions.xml \
    Tag

PRODUCT_COPY_FILES += \
   frameworks/native/data/etc/com.nxp.mifare.xml:$(TARGET_COPY_OUT_SYSTEM_EXT)/etc/permissions/com.nxp.mifare.xml:st \

# To keep P2P support :
# PRODUCT_COPY_FILES += vendor/st/nfc/st21nfc/conf/android.sofware.nfc.beam.xml:$(TARGET_COPY_OUT_SYSTEM)/etc/permissions/android.sofware.nfc.beam.xml:st

# To support APDU Gate:
PRODUCT_PACKAGES += \
    com.android.nfc_extras \
    com.android.nfc_extras.xml

PRODUCT_COPY_FILES += \
   vendor/st/nfc/st21nfc/conf/nfcee_access.xml:$(TARGET_COPY_OUT_SYSTEM)/etc/nfcee_access.xml:st

# Init.rc files
PRODUCT_COPY_FILES += \
   vendor/st/nfc/st21nfc/conf/init.system.st21nfc.rc:$(TARGET_COPY_OUT_SYSTEM)/etc/init/hw/init.stnfc.rc:st

################################################
## NFC Forum Digital support
PRODUCT_PACKAGES += \
   libnfc_st_dta_jni \
   libdtaNfaProvider \
   libnfc_st_dta \
   STNFCDta \
   ixitdata.xml

################################################
## Factory tests support
PRODUCT_PACKAGES += \

################################################
## If you are using PRODUCT_ENFORCE_ARTIFACT_PATH_REQUIREMENTS:
## our mk file causes the following to go into system.
## It means single AOSP system image cannot be used at the moment due to these files.
## ref: https://source.android.com/devices/bootloader/partitions/product-interfaces?hl=el
PRODUCT_ARTIFACT_PATH_REQUIREMENT_ALLOWED_LIST += \
   system/etc/init/hw/init.stnfc.rc \
   system/etc/ixitdata.xml \
   system/etc/nfcee_access.xml \
   system/lib/android.hardware.nfc@1.0.so \
   system/lib/android.hardware.nfc@1.1.so \
   system/lib/android.hardware.nfc@1.2.so \
   system/lib64/android.hardware.nfc@1.0.so \
   system/lib64/android.hardware.nfc@1.1.so \
   system/lib64/android.hardware.nfc@1.2.so \
