package UI;

import edu.hitsz.application.Game;
import edu.hitsz.application.Main;

import javax.swing.*;
import java.awt.*;

public class SwingControl {

    static final CardLayout cardLayout = new CardLayout(0, 0);
    static final JPanel cardPanel = new JPanel(cardLayout);


    public void UIStart(){
        System.out.println("Hello Aircraft War");
        //Game game = new Game(0);
        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - Main.WINDOW_WIDTH) / 2, 0,
                Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        StartMenu startMenu = new StartMenu();
        //startMenu.setGame(game);

        frame.add(cardPanel);
        cardPanel.add(startMenu.getStartMenuMainPanel());
        frame.setVisible(true);

    }

    public static void RankShow(int gameMode, int score){
        if(gameMode == 0){
            EasyModeRank easyModeRank = new EasyModeRank(score);
//            easyModeRank.setUserData(score);
            cardPanel.add(easyModeRank.getEasyModeRankMainPanel());
            cardLayout.last(cardPanel);
        } else if (gameMode == 1) {
            NormalModeRank normalModeRank = new NormalModeRank(score);
//            normalModeRank.setUserData(score);
            cardPanel.add(normalModeRank.getNormalModeRankMainPanel());
            cardLayout.last(cardPanel);
        } else if (gameMode == 2) {
            HardModeRank hardModeRank = new HardModeRank(score);
//            hardModeRank.setUserData(score);
            cardPanel.add(hardModeRank.getHardModeRankMainPanel());
            cardLayout.last(cardPanel);
        }

    }
}
