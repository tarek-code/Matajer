package com.unlimited.matajer.common.Moudules.SrttingsModule;

public class SrttingsModule {
    String Settingsname;
    int photoSettings;

    public SrttingsModule(String settingsname, int photoSettings) {
        Settingsname = settingsname;
        this.photoSettings = photoSettings;
    }

    public String getSettingsname() {
        return Settingsname;
    }

    public int getPhotoSettings() {
        return photoSettings;
    }
}
