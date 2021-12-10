package com.Algorithm;

import com.Observer;

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
    Node finalNode = null;
    Queue<Node> openList = null;
    List<Node> closeList = null;
    List<Node> routeList = null;


    List<Observer> observers = new ArrayList<>();

    public AstarImpl1() {
        openList = new PriorityQueue<>();
        closeList = new ArrayList<>();
    }

    @Override
    public void loadMap() {
        BufferedReader bufferedReader;
        int[][] map = new int[70][40];
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
        this.mapInfo = new MapInfo(map);
    }

    @Override
    public void start() {
        openList.clear();
        closeList.clear();
        this.begNode = mapInfo.begNode;
        this.endNode = mapInfo.endNode;
        begNode.G = 0;
        begNode.setH(endNode);
        begNode.setF();
        openList.add(begNode);
    }

    @Override
    public void nextStep() {
        if(openList.isEmpty()){
            canFind = false;
            Notify();
            return ;
        }
        Node curNode = openList.poll();
        mapInfo.map[curNode.pos.x][curNode.pos.y] = 3;
        for (int i = 0; i < 8; i++) {
            Node child = new Node(curNode.pos.x+dirx[i],curNode.pos.y+diry[i]);
            if(!isCanAddIntoOpen(child)) continue;
            mapInfo.map[child.pos.x][child.pos.y] = 4;
            if(i < 4) child.G += 10;
            else child.G += 14;
            child.setH(endNode);
            child.setF();
            child.parent = curNode;

            openList.add(child);

            if(child.equals(endNode)){
                isEnd = true;
                finalNode = endNode;
                finalNode.parent = child;
                break;
            }
        }
        closeList.add(curNode);
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

    private boolean isCanAddIntoClose(Node child) {
        if(child.pos.x < 0 || child.pos.x >= 70 ||child.pos.y < 0 || child.pos.y >= 40)
        {
            return false;
        } else if(mapInfo.map[child.pos.x][child.pos.y] == 1){
            return false;
        }else if(closeList.contains(child)){
            return false;
        }
        return true;
    }

    @Override
    public boolean goEnd() {
        while(!openList.isEmpty()){
            nextStep();
            if(isEnd){
                break;
            }
        }
        return isEnd;
    }

    @Override
    public boolean canFind() {
        return canFind;
    }

    @Override
    public void restart() {
        loadMap();
        canFind = true;
        isEnd = false;
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
    public void setRoute() {
        Node cur = finalNode;
        routeList = new ArrayList<>();
        while(cur != null){
            mapInfo.map[cur.pos.x][cur.pos.y] = 2;
            routeList.add(cur);
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
