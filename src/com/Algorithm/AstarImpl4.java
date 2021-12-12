package com.Algorithm;

import com.Observer;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class AstarImpl4 implements Astar{
    public MapInfo mapInfo = null;
    public Node begNode = null;
    public Node endNode = null;

    public boolean isEnd = false;
    public boolean canFind = true;
    private long usedTime = 0;
    private int spend = 0;
    public String mapName;

    private final int[] dirx = new int[]{0,0,1,-1,1,1,-1,-1};
    private final int[] diry = new int[]{1,-1,0,0,1,-1,1,-1};

    public Queue<Node> openList;
    public Queue<Node> o1;
    public Queue<Node> o2;
    public List<Node> closeList;
    public List<Node> close1;
    public List<Node> close2;

    private final List<Observer> observers = new ArrayList<>();

    public AstarImpl4() {
        openList = new PriorityQueue<>();
        o1 = new PriorityQueue<>();
        o2 = new PriorityQueue<>();
        closeList = new ArrayList<>();
        close1 = new ArrayList<>();
        close2 = new ArrayList<>();
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
        openList.add(endNode);
        o1.add(begNode);
        o2.add(endNode);
    }

    /**
     * 双向搜索，交替进行，当open表有重合时就结束。
     */
    @Override
    public void nextStep() {
        long startTime = System.currentTimeMillis();
        int step;
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
        if(!isInClose1(c1)){
            close1.add(c1);
            closeList.add(c1);
        }

        if(mapInfo.map[c1.pos.x][c1.pos.y] != 2)
            mapInfo.map[c1.pos.x][c1.pos.y] = 3;

        for (int i = 0; i < 8; i++) {
            Node child = new Node(c1.pos.x+dirx[i],c1.pos.y+diry[i]);
            if(!isAccess(child) || isInClose1(child)) continue;
            if(i < 4) step = 10;
            else step = 14;

            if((child = isInOpen1(child)) != null){
                updateG(c1,child,step);
            }else {
                child = new Node(c1.pos.x+dirx[i],c1.pos.y+diry[i]);
                addChildToOpen1(child,c1,step);
            }

            if(mapInfo.map[child.pos.x][child.pos.y] != 2)
                mapInfo.map[child.pos.x][child.pos.y] = 4;
        }

        //endNode
        Node c2 = o2.poll();
        openList.remove(c2);
        if(!isInClose2(c2)){
            close2.add(c2);
            closeList.add(c2);
        }

        if(mapInfo.map[c2.pos.x][c2.pos.y] != 2)
            mapInfo.map[c2.pos.x][c2.pos.y] = 3;

        for (int i = 0; i < 8; i++) {
            Node child = new Node(c2.pos.x+dirx[i],c2.pos.y+diry[i]);
            if(!isAccess(child) || isInClose2(child)) continue;
            if(i < 4) step = 10;
            else step = 14;

            if((child = isInOpen2(child)) != null){
                updateG(c2,child,step);
            }else {
                child = new Node(c2.pos.x+dirx[i],c2.pos.y+diry[i]);
                addChildToOpen2(child,c2,step);
            }

            if(mapInfo.map[child.pos.x][child.pos.y] != 2)
                mapInfo.map[child.pos.x][child.pos.y] = 4;
        }

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

    @Override
    public void goEnd() {
        Thread thread = new Thread(() -> {
            while(true){
                nextStep();
                if(isEnd || !canFind){
                    break;
                }
                try {
                    Thread.sleep(6);
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

    private void updateG(Node c, Node child, int step) {
        if(c.G + step < child.G){
            child.G = c.G + step;
            child.setF();
            child.parent = c;
        }
    }

    private boolean isInClose1(Node c1) {
        for(Node node : close1){
            if(node.equals(c1)){
                return true;
            }
        }
        return false;
    }

    private boolean isInClose2(Node c2) {
        for(Node node : close2){
            if(node.equals(c2)){
                return true;
            }
        }
        return false;
    }

    private Node isInOpen1(Node child) {
        for(Node node : o1){
            if(node.equals(child)) return node;
        }
        return null;
    }

    private Node isInOpen2(Node child) {
        for(Node node : o2){
            if(node.equals(child)) return node;
        }
        return null;
    }

    private void addChildToOpen1(Node child, Node c1, int step) {
        child.G = c1.G + step;
        child.setDiagonalH(endNode);
        child.setF();
        child.parent = c1;
        o1.add(child);
        openList.add(child);
    }

    private void addChildToOpen2(Node child, Node c2, int step) {
        child.G = c2.G + step;
        child.setDiagonalH(endNode);
        child.setF();
        child.parent = c2;
        o2.add(child);
        openList.add(child);
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
