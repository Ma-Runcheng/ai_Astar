package com.Algorithm;

public class MapInfo {
    public int[][] map;
    public int ROWS;
    public int COLS;
    public Node begNode;
    public Node endNode;

    public MapInfo(int[][] map) {
        this.map = map;
        this.ROWS = map.length;
        this.COLS = map[0].length;
    }

    public void setBegNode(Node begNode) {
        this.begNode = begNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }
}