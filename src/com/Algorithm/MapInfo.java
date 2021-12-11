package com.Algorithm;

public class MapInfo {
    public int[][] map;
    public int ROWS;
    public int COLS;
    public Node begNode = null;
    public Node endNode = null;
    public String ImgName;
    public String GridName;

    public MapInfo(int[][] map) {
        this.map = map;
        this.ROWS = map.length;
        this.COLS = map[0].length;
    }

    public MapInfo(int rows, int cols){
        this.ROWS = rows;
        this.COLS = cols;
        this.map = new int[rows][cols];
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public void setROWS(int ROWS) {
        this.ROWS = ROWS;
    }

    public void setCOLS(int COLS) {
        this.COLS = COLS;
    }

    public void setBegNode(Node begNode) {
        this.begNode = begNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }
}