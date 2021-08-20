# Device configuration file to be included from device.mk for the vendor image, e.g.
#
#   -include vendor/st/nfc/st21nfc/NfcDeviceConfigVendor.mk

######################################################################
##########################  VENDOR image  ############################
######################################################################

# Merge the manifest as needed for NFC support.
#DEVICE_MANIFEST_FILE += vendor/st/nfc/st21nfc/conf/manifest_nfc.xml

# Copy the correct parameters of the NFC controller depending on the hardware layout and CLF version.
#PRODUCT_COPY_FILES += \
#      $(NFC_RF_CONFIG_PATH)/st21nfc_conf.txt:$(TARGET_COPY_OUT_VENDOR)/etc/st21nfc_conf.txt:st

################################################
## Configuration for ST NFC packages
PRODUCT_PACKAGES += \
    android.hardware.nfc@1.2-service-st \
    nfc_nci.st21nfc.st \

PRODUCT_COPY_FILES += \
   frameworks/native/data/etc/android.hardware.nfc.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.nfc.xml:st \
   frameworks/native/data/etc/android.hardware.nfc.hce.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.nfc.hce.xml:st \
   frameworks/native/data/etc/android.hardware.nfc.hcef.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.nfc.hcef.xml:st \
   frameworks/native/data/etc/android.hardware.nfc.uicc.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.nfc.uicc.xml:st \

# if eSE:    frameworks/native/data/etc/android.hardware.nfc.ese.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.nfc.ese.xml:st \
# if eSE in OMAPI:    frameworks/native/data/etc/android.hardware.se.omapi.ese.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.se.omapi.ese.xml:st \

# Stack configuration files (common for ST stack and AOSP stack)
ifneq ($(strip $(TARGET_BUILD_VARIANT)),user)
   PRODUCT_COPY_FILES += \
   vendor/st/nfc/system/nfc/conf/libnfc-nci.conf:$(TARGET_COPY_OUT_VENDOR)/etc/libnfc-nci.conf:st \
   vendor/st/nfc/hardware/st/nfc/conf/libnfc-hal-st.conf:$(TARGET_COPY_OUT_VENDOR)/etc/libnfc-hal-st.conf:st

else
  # Configuration files for user build, remove some logs for GSMA certif
   PRODUCT_COPY_FILES += \
   vendor/st/nfc/system/nfc/conf/libnfc-nci.conf.user:$(TARGET_COPY_OUT_VENDOR)/etc/libnfc-nci.conf:st \
   vendor/st/nfc/hardware/st/nfc/conf/libnfc-hal-st.conf.user:$(TARGET_COPY_OUT_VENDOR)/etc/libnfc-hal-st.conf:st

endif

################################################
## Factory tests support
PRODUCT_PACKAGES += \

################################################
## If you need NFC firmware to be flashed before either NFC stack or factory
# tool starts, you can use this. This avoids in particular a long startup of
# the factory tool the 1st time it runs (FW flashing)
PRODUCT_PACKAGES += \

# Init.rc files
PRODUCT_COPY_FILES += \
   vendor/st/nfc/st21nfc/conf/init.vendor.st21nfc.rc:$(TARGET_COPY_OUT_VENDOR)/etc/init/hw/init.stnfc.rc:st



