package com.malakhau.arthur.remotejoystick.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.malakhau.arthur.remotejoystick.R;
import com.malakhau.arthur.remotejoystick.view_model.ViewModel;

public class MainActivity extends AppCompatActivity implements Joystick.JoystickTouchListener {

    ViewModel viewModel = new ViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekBarThrottle = findViewById(R.id.seekBarThrottle);
        seekBarThrottle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                viewModel.viewModelSetThrottle(seekBar.getProgress(), seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        SeekBar seekBarRudder = findViewById(R.id.seekBarRudder);
        seekBarRudder.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                viewModel.viewModelSetRudder(seekBar.getProgress(), seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    public void connectClick(View view) {
        EditText ip = findViewById(R.id.Ip);
        EditText port = findViewById(R.id.Port);
        String ips = ip.getText().toString();
        String ports = port.getText().toString();
        if (ips.matches("")) {
            Snackbar missing = Snackbar.make(view, "Ip field is missing", BaseTransientBottomBar.LENGTH_SHORT);
            missing.show();
        } else if (ports.matches("")) {
            Snackbar missing = Snackbar.make(view, "Port field is missing", BaseTransientBottomBar.LENGTH_SHORT);
            missing.show();
        } else {
            Snackbar msg = Snackbar.make(view, "Connecting", BaseTransientBottomBar.LENGTH_SHORT);
            msg.show();
            SeekBar throttle = findViewById(R.id.seekBarThrottle);
            throttle.setProgress(0);
            SeekBar rudder = findViewById(R.id.seekBarRudder);
            rudder.setProgress(50);
            if (viewModel.viewModelConnect(ips, ports)) {
                msg = Snackbar.make(view, "Connected successfully", BaseTransientBottomBar.LENGTH_SHORT);
                msg.show();
            } else {
                msg = Snackbar.make(view, "Connection failed", BaseTransientBottomBar.LENGTH_SHORT);
                msg.show();
            }
        }
    }

    @Override
    public void onChange(float aileron, float elevator) {
        viewModel.viewModelSetAileron(aileron);
        viewModel.viewModelSetElevator(elevator);
    }
}