package com.Algorithm;

import java.awt.*;

public class Node implements Comparable<Node>{

    public Point pos;
    public Node parent;
    public int F;
    public int G;
    public int H;

    public Node(int x, int y){
        this.pos = new Point(x,y);
    }

    public Node(int x, int y, int G, int H) {
        this.pos = new Point(x,y);
        this.G = G;
        this.H = H;
    }

    public void setF(){
        this.F = this.G + this.H;
    }

    /**
     * 对角线距离，可走对角线
     */
    public void setDiagonalH(Node endPos){
        int x, y;
        x = (endPos.pos.x > this.pos.x) ? (endPos.pos.x - this.pos.x) : (this.pos.x - endPos.pos.x);
        y = (endPos.pos.y > this.pos.y) ? (endPos.pos.y - this.pos.y) : (this.pos.y - endPos.pos.y);
        int diagonal = Math.min(x,y);//对角步数
        int straight = x + y;//曼哈顿距离
        this.H  = 14 * diagonal + 10 * (straight - 2 * diagonal);
    }

    /**
     * Manhattan距离，不可走对角线
     */
    public void setManhattanH(Node endPos){
        int x, y;
        x = (endPos.pos.x > this.pos.x) ? (endPos.pos.x - this.pos.x) : (this.pos.x - endPos.pos.x);
        y = (endPos.pos.y > this.pos.y) ? (endPos.pos.y - this.pos.y) : (this.pos.y - endPos.pos.y);
        this.H = (x + y) * 10;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Node){
            Node node = (Node) obj;
            return node.pos.equals(this.pos);
        }else{
            return false;
        }
    }

    @Override
    public int compareTo(Node n) {
        if(n == null) return -1;
        return this.F - n.F;
    }
}

