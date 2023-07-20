package edu.hitsz.DAO;

import java.util.List;

public interface DataDao {

    /*
    返回链表存储所有游戏数据
     */
    List<GameData> getAllDatas();

    /*
    在游戏排行榜中添加本次游戏数据
     */
    void doAdd(GameData data);

    /*
    删除所选行数的数据
     */
    void doDelete(int index);

    /*
    将链表中的数组写入文件
     */
    void writeToFile();

    /*
    从文件中读出游戏数据
     */
    void readFromFile();
}
