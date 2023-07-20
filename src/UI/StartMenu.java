package UI;

import edu.hitsz.application.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class StartMenu {
    private JButton easyModeButton;
    private JButton normalModeButton;
    private JButton hardModeButton;
    private JPanel easyModePanel;
    private JPanel normalModePanel;
    private JPanel hardModePanel;
    private JPanel musicControlPanel;
    private JComboBox musicControlComboBox;
    private JLabel musicLabel;
    private JPanel musicLabelPanel;
    private JPanel startMenuMainPanel;
    private JPanel startpanel1;
    private JPanel startpanel2;
    private JPanel startpanel3;
    private JPanel startpanel4;
    private JPanel startpanel5;
    private JPanel startpanel6;
    private JPanel startpanel7;
    private JPanel startpanel8;
    private JPanel startpanel9;
    private JPanel startpanel10;
    private JPanel startpanel11;

    private Game game;

    private int selectedOption;

    public StartMenu() {

        easyModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                game = new EasyGame(0);

                if (selectedOption == 0){
                    game.isMusicOFF = false;
                }
                else{
                    game.isMusicOFF = true;
                }
                game.action();
                SwingControl.cardPanel.add(game);
                SwingControl.cardLayout.last(SwingControl.cardPanel);
            }
        });
        normalModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                game = new NormalGame(1);
                if (selectedOption == 0){
                    game.isMusicOFF = false;
                }
                else{
                    game.isMusicOFF = true;
                }
                game.action();
                SwingControl.cardPanel.add(game);
                SwingControl.cardLayout.last(SwingControl.cardPanel);
            }
        });
        hardModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg5.jpg"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                game = new HardGame(2);
                if (selectedOption == 0){
                    game.isMusicOFF = false;
                }
                else{
                    game.isMusicOFF = true;
                }
                game.action();
                SwingControl.cardPanel.add(game);
                SwingControl.cardLayout.last(SwingControl.cardPanel);
            }
        });
        musicControlComboBox.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            selectedOption = musicControlComboBox.getSelectedIndex();
        }
        });

    }


    public JPanel getStartMenuMainPanel(){
        return startMenuMainPanel;
    }


    public void setGame(Game game){
        this.game = game;
    }

}


