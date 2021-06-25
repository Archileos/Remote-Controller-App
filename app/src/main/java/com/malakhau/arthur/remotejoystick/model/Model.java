package com.malakhau.arthur.remotejoystick.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Model {
    BlockingQueue<Runnable> dispatchQueue = new LinkedBlockingQueue<Runnable>();
    PrintWriter out;

    public Model(String ip, int port) {
        connect(ip, port);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        dispatchQueue.take().run();
                    } catch (InterruptedException ignored) { }
                }
            }
        }).start();
    }

    public void connect(String ip, int port) {
        dispatchQueue.add(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket fg = new Socket(ip, port);
                    out = new PrintWriter(fg.getOutputStream(), true);
                } catch (IOException ignored) { }
            }
        });
    }

    public void addToDispatchQueue(String type, float value) {
        if (type.matches("aileron")) {
            dispatchQueue.add(
                    new Runnable() {
                        @Override
                        public void run() {
                            out.print("set /controls/flight/aileron " + value + "\r\n");
                            out.flush();
                        }
                    });
        } else if (type.matches("elevator")) {
            dispatchQueue.add(
                    new Runnable() {
                        @Override
                        public void run() {
                            out.print("set /controls/flight/elevator " + value + "\r\n");
                            out.flush();
                        }
                    });
        } else if (type.matches("rudder")) {
            dispatchQueue.add(
                    new Runnable() {
                        @Override
                        public void run() {
                            out.print("set /controls/flight/rudder " + value + "\r\n");
                            out.flush();
                        }
                    });
        } else if (type.matches("throttle")) {
            dispatchQueue.add(
                    new Runnable() {
                        @Override
                        public void run() {
                            out.print("set /controls/engines/current-engine/throttle " + value + "\r\n");
                            out.flush();
                        }
                    });
        }
    }
}
