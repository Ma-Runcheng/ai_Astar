package com.Algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Queue;

public class AstarImpl implements Astar {
    MapInfo mapInfo = null;

    public static void main(String[] args) {
        AstarImpl a = new AstarImpl();
        a.loadMap();
    }

    @Override
    public void loadMap() {
        BufferedReader bufferedReader;
        int[][] map = new int[69][32];
        try{
            File file = new File("mat.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            int i=0;
            while((line = bufferedReader.readLine() )!= null){
                String[] str = line.split(",");
                for(int k = 0; k < str.length; k++){
                    map[i][k] = Integer.parseInt(str[k]);
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapInfo = new MapInfo(map);
    }

    @Override
    public void start() {

    }

    @Override
    public void nextStep() {

    }

    @Override
    public boolean goEnd() {
        return false;
    }

    @Override
    public void setMapInfo(MapInfo mapInfo) {

    }

    @Override
    public MapInfo getMapInfo() {
        return null;
    }

    @Override
    public Queue<Node> getOpenList() {
        return null;
    }

    @Override
    public List<Node> getCloseList() {
        return null;
    }

    @Override
    public void setRoute() {

    }

}
