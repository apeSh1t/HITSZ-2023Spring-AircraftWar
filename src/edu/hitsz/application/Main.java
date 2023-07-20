package edu.hitsz.application;

import UI.SwingControl;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    public static void main(String[] args) {
        SwingControl swingController = new SwingControl();
        swingController.UIStart();
    }
}
