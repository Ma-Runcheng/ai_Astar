package com.Algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class AstarImpl implements Astar {
    MapInfo mapInfo = null;
    boolean isEnd = false;
    int[] dirx = new int[]{0,0,1,-1};
    int[] diry = new int[]{1,-1,0,0};
    Node begNode = null;
    Node endNode = null;
    Node finalNode = null;
    Queue<Node> openList = null;
    List<Node> closeList = null;
    List<Node> routeList = null;

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
        openList = new PriorityQueue<>();
        closeList = new ArrayList<>();
        this.begNode = mapInfo.begNode;
        this.endNode = mapInfo.endNode;
        begNode.G = 0;
        begNode.setH(endNode);
        begNode.setF();
        openList.add(begNode);
    }

    @Override
    public void nextStep() {
        Node curNode = openList.poll();
        closeList.add(curNode);
        mapInfo.map[curNode.pos.x][curNode.pos.y] = 3;
        for (int i = 0; i < 4; i++) {
            Node child = new Node(curNode.pos.x+dirx[i],curNode.pos.y+diry[i]);
            if(!isCanAddIntoOpen(child)) continue;
            mapInfo.map[child.pos.x][child.pos.y] = 4;
            child.G += 10;
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
        openList.poll();
    }

    private boolean isCanAddIntoOpen(Node child) {
        if(mapInfo.map[child.pos.x][child.pos.y] == 1) {
            return false;
        } else if(child.pos.x < 0 || child.pos.x >= 40 ||child.pos.y < 0 || child.pos.y >= 70){
            return false;
        }else if(openList.contains(child)){
            return false;
        }else if(closeList.contains(child)){
            return false;
        }
        return true;
    }

    private boolean isCanAddIntoClose(Node child) {
        if(mapInfo.map[child.pos.x][child.pos.y] == 1)
        {
            return false;
        } else if(child.pos.x < 0 || child.pos.x >= 40 ||child.pos.y < 0 || child.pos.y >= 70){
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
                return true;
            }
        }
        return false;
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

}
