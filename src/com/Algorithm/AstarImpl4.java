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

public class AstarImpl4 implements Astar{
    MapInfo mapInfo = null;
    boolean isEnd = false;
    boolean canFind = true;
    int[] dirx = new int[]{0,0,1,-1,1,1,-1,-1};
    int[] diry = new int[]{1,-1,0,0,1,-1,1,-1};
    Node begNode = null;
    Node endNode = null;
    Queue<Node> openList = null;
    Queue<Node> o1 = null;
    Queue<Node> o2 = null;
    List<Node> closeList = null;
    List<Node> close1 = null;
    List<Node> close2 = null;
    List<Node> routeList = null;
    long usedTime = 0;
    int spend = 0;

    List<Observer> observers = new ArrayList<>();

    public AstarImpl4() {
        openList = new PriorityQueue<>();
        o1 = new PriorityQueue<>();
        o2 = new PriorityQueue<>();
        closeList = new ArrayList<>();
        close1 = new ArrayList<>();
        close2 = new ArrayList<>();
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
        begNode.setDiagonalH(endNode);
        begNode.setF();
        openList.add(begNode);
        o1.add(begNode);
        o2.add(endNode);
    }

    @Override
    public void restart() {
        loadMap();
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

    /**
     * 双向搜索，交替进行，当open表有重合时就结束。
     */
    @Override
    public void nextStep() {
        long startTime = System.currentTimeMillis();

        if(o1.isEmpty() || o2.isEmpty()){
            canFind = false;
            String msg = "找不到路径，用时："+ usedTime + "ms";
            JOptionPane.showMessageDialog(null,msg);
            Notify();
            return ;
        }

        //先从begNode开始
        Node c1 = o1.poll();
        openList.remove(c1);
        if(mapInfo.map[c1.pos.x][c1.pos.y] != 2)
            mapInfo.map[c1.pos.x][c1.pos.y] = 3;
        for (int i = 0; i < 8; i++) {
            Node child = new Node(c1.pos.x+dirx[i],c1.pos.y+diry[i]);
            if(!isCanAddIntoOpen1(child)) continue;
            if(mapInfo.map[child.pos.x][child.pos.y] != 2)
                mapInfo.map[child.pos.x][child.pos.y] = 4;
            if(i < 4) child.G += 10;
            else child.G += 14;
            child.setDiagonalH(endNode);
            child.setF();
            child.parent = c1;
            openList.add(child);
            o1.add(child);
        }

        //endNode
        Node c2 = o2.poll();
        openList.remove(c2);
        if(mapInfo.map[c2.pos.x][c2.pos.y] != 2)
            mapInfo.map[c2.pos.x][c2.pos.y] = 3;
        for (int i = 0; i < 8; i++) {
            Node child = new Node(c2.pos.x+dirx[i],c2.pos.y+diry[i]);
            if(!isCanAddIntoOpen2(child)) continue;
            if(mapInfo.map[child.pos.x][child.pos.y] != 2)
                mapInfo.map[child.pos.x][child.pos.y] = 4;
            if(i < 4) child.G += 10;
            else child.G += 14;
            child.setDiagonalH(begNode);
            child.setF();
            child.parent = c2;
            openList.add(child);
            o2.add(child);
        }
        closeList.add(c1);
        closeList.add(c2);
        close1.add(c1);
        close2.add(c2);

        //close表第一次接触的时候搜索结束
        if(close1.contains(c2)){
            //找到路径,扩展结果
            isEnd = true;
            setRoute(c2);
            for(Node c : close1){
                if(c.equals(c2)){
                    setRoute(c);
                    break;
                }
            }
        }else if(close2.contains(c1)){
            isEnd = true;
            setRoute(c1);
            for(Node c : close2){
                if(c.equals(c1)){
                    setRoute(c);
                    break;
                }
            }
        }
        long endTime = System.currentTimeMillis();
        usedTime += endTime - startTime;
        if(isEnd){
            String msg = "成功找到路径\n用时："+ usedTime + "ms\n长度：" + spend;
            JOptionPane.showMessageDialog(null,msg);
        }
        Notify();
    }

    private boolean isCanAddIntoOpen1(Node child) {
        if(child.pos.x < 0 || child.pos.x >= 70 ||child.pos.y < 0 || child.pos.y >= 40) {
            return false;
        } else if(mapInfo.map[child.pos.x][child.pos.y] == 1){
            return false;
        }
        if(o1.contains(child)) return false;
        if(close1.contains(child)) return false;
        return true;
    }

    private boolean isCanAddIntoOpen2(Node child) {
        if(child.pos.x < 0 || child.pos.x >= 70 ||child.pos.y < 0 || child.pos.y >= 40) {
            return false;
        } else if(mapInfo.map[child.pos.x][child.pos.y] == 1){
            return false;
        }
        if(o2.contains(child)) return false;
        if(close2.contains(child)) return false;
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
    public void setRoute(Node finalNode){
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
        Notify();
    }


    @Override
    public boolean isEnd() {
        return isEnd;
    }

    @Override
    public boolean canFind() {
        return canFind;
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
