package com.seapip.thomas.kai.settings;

import java.util.ArrayList;

public class SettingsPage {
    private ArrayList<SettingsOverlay> settingOverlays;

    public SettingsPage(ArrayList<SettingsOverlay> settingOverlays) {
        this.settingOverlays = settingOverlays;
    }

    public ArrayList<SettingsOverlay> getSettingOverlays() {
        return settingOverlays;
    }
}