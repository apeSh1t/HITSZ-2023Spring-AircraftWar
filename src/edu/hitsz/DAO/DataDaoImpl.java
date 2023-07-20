package edu.hitsz.DAO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataDaoImpl implements DataDao, java.io.Serializable{

    private List<GameData> datas;
    private String fileName;

    public DataDaoImpl(int gameMode){
        fileName = "data" + gameMode + ".dat";
        this.datas = new ArrayList<>();
    }

    @Override
    public List<GameData> getAllDatas() {
        return datas;
    }

    @Override
    public void doAdd(GameData data) {
        int i = 0;
        for(GameData da : datas){
            if (da.getScore() < data.getScore()) {
                break;
            }
            i ++;
        }
        datas.add(i, data);
    }

    @Override
    public void doDelete(int index) {
        datas.remove(index);
    }

    @Override
    public void writeToFile() {
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(datas);
            oos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void readFromFile() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            datas = (List<GameData>) ois.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
