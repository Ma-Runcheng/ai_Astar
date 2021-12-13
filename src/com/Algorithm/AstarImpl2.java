package com.Algorithm;

import com.Observer;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class AstarImpl2 implements Astar{
    public MapInfo mapInfo = null;
    public Node begNode = null;
    public Node endNode = null;

    public boolean isEnd = false;
    public boolean canFind = true;
    private long usedTime = 0;
    private int spend = 0;
    public String GridName;

    private final int[] dirx = new int[]{0,0,1,-1};
    private final int[] diry = new int[]{1,-1,0,0};

    public Queue<Node> openList;
    public List<Node> closeList;

    private final List<Observer> observers = new ArrayList<>();

    public AstarImpl2() {
        openList = new PriorityQueue<>();
        closeList = new ArrayList<>();
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
        int step;
        long startTime = System.currentTimeMillis();

        if(openList.isEmpty()){
            canFind = false;
            String msg = "找不到路径，用时："+ usedTime + "ms";
            JOptionPane.showMessageDialog(null,msg);
            Notify();
            return ;
        }

        Node curNode = openList.poll();
        if(!isInCloseList(curNode)){
            closeList.add(curNode);
        }

        if(mapInfo.map[curNode.pos.x][curNode.pos.y] != 2)
            mapInfo.map[curNode.pos.x][curNode.pos.y] = 3;

        for (int i = 0; i < 4; i++) {
            Node child = new Node(curNode.pos.x+dirx[i],curNode.pos.y+diry[i]);
            if(!isAccess(child) || isInCloseList(child)) continue;
            step = 10;

            if((child = isInOpenList(child)) != null){
                updateG(curNode,child,step);
            }else {
                child = new Node(curNode.pos.x+dirx[i],curNode.pos.y+diry[i]);
                addChildToOpen(child,curNode,step);
            }

            if(mapInfo.map[child.pos.x][child.pos.y] != 2)
                mapInfo.map[child.pos.x][child.pos.y] = 4;

            if(child.equals(endNode)){
                isEnd = true;
                setRoute(child);
                String msg = "成功找到路径!\n用时："+ usedTime + "ms\n长度："+ spend;
                JOptionPane.showMessageDialog(null,msg);
                break;
            }
        }

        long endTime = System.currentTimeMillis();
        usedTime += endTime - startTime;
        Notify();
    }

    @Override
    public void goEnd() {
        Thread thread = new Thread(() -> {
            while(true) {
                nextStep();
                if (isEnd || !canFind) {
                    break;
                }
                try {
                    Thread.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
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

    private void addChildToOpen(Node child,Node curNode,int step) {
        child.G = curNode.G + step;
        child.setManhattanH(endNode);
        child.setF();
        child.parent = curNode;
        openList.add(child);
    }

    private boolean isInCloseList(Node curNode) {
        for(Node node : closeList){
            if(node.equals(curNode)){
                return true;
            }
        }
        return false;
    }

    private Node isInOpenList(Node child) {
        for(Node node : openList){
            if(node.equals(child)) return node;
        }
        return null;
    }

    private void updateG(Node curNode, Node child, int step) {
        if(curNode.G + step < child.G){
            child.G = curNode.G + step;
            child.setF();
            child.parent = curNode;
        }
    }

    private boolean isAccess(Node child) {
        if(child.pos.x < 0 || child.pos.x >= mapInfo.ROWS ||child.pos.y < 0 || child.pos.y >= mapInfo.COLS) {
            return false;
        } else if(mapInfo.map[child.pos.x][child.pos.y] == 1){
            return false;
        }
        return true;
    }

    @Override
    public boolean canFind() {
        return canFind;
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
}
