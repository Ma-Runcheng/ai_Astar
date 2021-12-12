package com.Algorithm;

import com.Observer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class AstarImpl2 implements Astar{
    String mapName;
    MapInfo mapInfo = null;

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
    public void loadMap(String mapName) {
        this.mapName = mapName;
        BufferedReader bufferedReader;
        try{
            File file = new File(mapName);
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            int rows = 0;
            List<String[]> str = new ArrayList<>();
            while((line = bufferedReader.readLine() )!= null){
                String[] temp = line.split(",");
                rows++;
                str.add(temp);
            }
            int cols = str.get(0).length;

            int[][] map = new int[rows][cols];
            for(int i = 0; i < rows; i++){
                for(int j = 0; j < cols; j++){
                    String[] temp = str.get(i);
                    map[i][j] = Integer.parseInt(temp[j]);
                }
            }
            this.mapInfo = new MapInfo(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void nextStep() {

    }

    @Override
    public void goEnd() {

    }

    @Override
    public void setRoute(Node finalNode) {

    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public boolean canFind() {
        return false;
    }


    @Override
    public void Notify() {

    }

    @Override
    public void register(Observer o) {

    }
}
