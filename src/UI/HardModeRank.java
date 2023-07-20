package UI;

import edu.hitsz.DAO.DataDaoImpl;
import edu.hitsz.DAO.GameData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class HardModeRank {
    private JPanel hardModeRankMainPanel;
    private JPanel bottomPanel;
    private JButton hardModeDeleteButton;
    private JLabel hardModeShowLable;
    private JLabel hardModeRankLable;
    private JScrollPane hardModeRankPanel;
    private JTable hardModeRankTable;
    private DefaultTableModel model;
public HardModeRank(int score) {
    hardModeDeleteButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = hardModeRankTable.getSelectedRow();
            int result = JOptionPane.showConfirmDialog(hardModeDeleteButton,
                    "是否确定中删除？");
            if (JOptionPane.YES_OPTION == result && row != -1) {
                model.removeRow(row);
                DataDaoImpl dataDao = new DataDaoImpl(2);
                dataDao.readFromFile();
                dataDao.doDelete(row);
                dataDao.writeToFile();
                setModel(dataDao);
                hardModeRankTable.setModel(model);
                hardModeRankPanel.setViewportView(hardModeRankTable);
            }
        }
    });

    String username = JOptionPane.showInputDialog(null, "游戏结束，您的分数是" + score + "" + "\n请输入用户名:");
    GameData gameData = new GameData(username, score, new Date());

    DataDaoImpl dataDao = new DataDaoImpl(2);
    dataDao.readFromFile();
    if (username != null && !Objects.equals(username, "")) {
        dataDao.doAdd(gameData);
    }
    dataDao.writeToFile();
    setModel(dataDao);

    //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
    hardModeRankTable.setModel(model);
    hardModeRankPanel.setViewportView(hardModeRankTable);
}

public JPanel getHardModeRankMainPanel(){
    return hardModeRankMainPanel;
}

    private void setModel(DataDaoImpl dataDao){

        String[] columnName = {"名次","玩家名","得分","记录时间"};
        String[][] tableData = new String[dataDao.getAllDatas().size()][4];
        //表格模型

        int i = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
        for (GameData data : dataDao.getAllDatas()) {
            tableData[i][0] = (i+1)+"";
            tableData[i][1] = data.getUsername();
            tableData[i][2] = data.getScore()+"";
            tableData[i][3] = formatter.format(data.getDate());
            i++;
        }

        model = new DefaultTableModel(tableData, columnName){
            @Override
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };
    }

}
