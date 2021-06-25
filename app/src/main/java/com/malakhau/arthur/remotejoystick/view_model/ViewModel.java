package com.malakhau.arthur.remotejoystick.view_model;

import com.malakhau.arthur.remotejoystick.model.Model;

public class ViewModel {
    Model model;

    public boolean viewModelConnect(String ip, String port) {
        try {
            this.model = new Model(ip, Integer.parseInt(port));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void viewModelSetThrottle(int progress, int max) {
        if (model == null)
            return;
        float percent = (float) progress / (float) max;
        model.addToDispatchQueue("throttle", percent);
    }

    public void viewModelSetRudder(float progress, int max) {
        if (model == null)
            return;
        float newMax = (float) max / 2;
        float newProgress = progress - newMax;
        float percent = newProgress / newMax;
        model.addToDispatchQueue("rudder", percent);
    }

    public void viewModelSetAileron(float aileron) {
        if (model == null)
            return;
        model.addToDispatchQueue("aileron", aileron);
    }

    public void viewModelSetElevator(float elevator) {
        if (model == null)
            return;
        model.addToDispatchQueue("elevator", elevator);
    }
}
