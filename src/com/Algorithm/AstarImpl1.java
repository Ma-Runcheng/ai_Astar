package com.Algorithm;

import com.Observer;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class AstarImpl1 implements Astar {
    MapInfo mapInfo = null;
    boolean isEnd = false;
    boolean canFind = true;
    int[] dirx = new int[]{0,0,1,-1,1,1,-1,-1};
    int[] diry = new int[]{1,-1,0,0,1,-1,1,-1};
    Node begNode = null;
    Node endNode = null;
    Queue<Node> openList;
    List<Node> closeList;
    long usedTime = 0;
    int spend = 0;
    String GridName;

    List<Observer> observers = new ArrayList<>();

    public AstarImpl1() {
        openList = new PriorityQueue<>();
        closeList = new ArrayList<>();
    }

    @Override
    public void loadMap(String GridName) {
        this.GridName = GridName;
        BufferedReader bufferedReader;
        try{
            File file = new File(GridName);
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
        openList.clear();
        closeList.clear();
        this.begNode = mapInfo.begNode;
        this.endNode = mapInfo.endNode;
        begNode.G = 0;
        begNode.setDiagonalH(endNode);
        begNode.setF();
        openList.add(begNode);
    }

    @Override
    public void nextStep() {
        long startTime = System.currentTimeMillis();
        if(openList.isEmpty()){
            canFind = false;
            String msg = "找不到路径，用时："+ usedTime + "ms";
            JOptionPane.showMessageDialog(null,msg);
            Notify();
            return ;
        }
        Node curNode = openList.poll();
        if(mapInfo.map[curNode.pos.x][curNode.pos.y] != 2)
            mapInfo.map[curNode.pos.x][curNode.pos.y] = 3;
        for (int i = 0; i < 8; i++) {
            Node child = new Node(curNode.pos.x+dirx[i],curNode.pos.y+diry[i]);
            if(!isCanAddIntoOpen(child)) continue;
            if(mapInfo.map[child.pos.x][child.pos.y] != 2)
                mapInfo.map[child.pos.x][child.pos.y] = 4;
            if(i < 4) child.G += 10;
            else child.G += 14;
            child.setDiagonalH(endNode);
            child.setF();
            child.parent = curNode;

            openList.add(child);

            if(child.equals(endNode)){
                isEnd = true;
                setRoute(child);
                String msg = "成功找到路径!\n用时："+ usedTime + "ms\n长度："+ spend;
                JOptionPane.showMessageDialog(null,msg);
                break;
            }
        }
        closeList.add(curNode);
        long endTime = System.currentTimeMillis();
        usedTime += endTime - startTime;
        Notify();
    }

    private boolean isCanAddIntoOpen(Node child) {
        if(child.pos.x < 0 || child.pos.x >= 70 ||child.pos.y < 0 || child.pos.y >= 40) {
            return false;
        } else if(mapInfo.map[child.pos.x][child.pos.y] == 1){
            return false;
        }

        if(openList.contains(child)){
            return false;
        }
        if(closeList.contains(child)){
            return false;
        }
        return true;
    }

    @Override
    public void goEnd() {
        Thread thread = new Thread(() -> {
            while(true){
                nextStep();
                if(isEnd || !canFind){
                    break;
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public boolean canFind() {
        return canFind;
    }

    @Override
    public void restart() {
        loadMap(GridName);
        canFind = true;
        isEnd = false;
        spend = 0;
        usedTime = 0;
        this.begNode = null;
        this.endNode = null;
        mapInfo.begNode = null;
        mapInfo.endNode = null;
        openList.clear();
        closeList.clear();
        Notify();
    }

    @Override
    public void setMapInfo(MapInfo mapInfo) {
        this.mapInfo = mapInfo;
    }

    @Override
    public MapInfo getMapInfo() {
        return mapInfo;
    }

    @Override
    public Queue<Node> getOpenList() {
        return openList;
    }

    @Override
    public List<Node> getCloseList() {
        return closeList;
    }

    @Override
    public void setRoute(Node finalNode) {
        Node cur = finalNode;
        while(cur != null){
            mapInfo.map[cur.pos.x][cur.pos.y] = 2;
            if(cur.parent != null){
                if(Math.abs(cur.parent.pos.x-cur.pos.x)+Math.abs(cur.parent.pos.y-cur.pos.y) == 2){
                    spend += 14;
                }else{
                    spend += 10;
                }
            }
            cur = cur.parent;
        }
    }

    @Override
    public boolean isEnd() {
        return isEnd;
    }


    @Override
    public void Notify() {
        for(Observer o : observers){
            o.update();
        }
    }

    @Override
    public void register(Observer o) {
        if(!observers.contains(o)){
            observers.add(o);
        }
    }
}
