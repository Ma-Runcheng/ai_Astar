package com.View;

import com.Algorithm.Astar;
import com.Algorithm.MapInfo;
import com.Algorithm.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class MapPanel extends JPanel {
    /*
    map中 -1----路-------灰色
          1----障碍------红色
          2----结果路径-------蓝色
          3----搜索中路径cur------中心绿色
          4----cur的childs------分布在绿色周围黄色
          5----被选择状态------紫色
     */
    ImageIcon icon = null;
    Image mapImg = null;
    Astar astar;
    MapInfo mapInfo;

    int posx,posy;  //此时选择的点的map数组坐标
    public MapPanel(Astar astar) {
        this.astar = astar;
        this.mapInfo = astar.getMapInfo();
        loadMap();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //读取mapInfo坐标，实现标点，设置起始点，结束点
                posx = round2(e.getX());//map[y][x]
                posy = round(e.getY());
                System.out.println(posx+" "+posy);
            }
        });
    }

    private int round2(int y){

        int m = y % 16;
        int n=y/16;
        if(m<8 ) {
            return y = n;
        }
        else
            return y=n+1;
    }
    private int round(int x) {
        int m = x % 19;
        int n = x / 19;
        if(m < 9.5){
            return x =  n;
        } else return x =  n + 1;
    }

    public void loadMap(){
        this.icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/image/map.jpg")));
        this.mapImg = icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(mapImg,0,0,mapImg.getWidth(null),mapImg.getHeight(null),null);
        //判断画点
        int[][] map = mapInfo.map;
        for (int i = 0; i < mapInfo.ROWS; i++) {
            for (int j = 0; j < mapInfo.COLS; j++) {
                if(map[i][j] == 1){
                    g.setColor(Color.red);
                }
                if(map[i][j]==-1){
                    g.setColor(Color.gray);
                }
                if(map[i][j]==2){
                    g.setColor(Color.blue);
                }
                if(map[i][j]==3){
                    g.setColor(Color.green);
                }
                if(map[i][j]==4){
                    g.setColor(Color.yellow);
                }
                if(map[i][j]==5){
                    g.setColor(Color.magenta);
                }
                g.fillRect(j*19,i*16,4,4);
            }
        }
    }

    public void setAstar(Astar astar) {
        this.astar = astar;
    }

    public void setBegPos(){
        //给ai传起始点
        mapInfo.begNode = new Node(posx,posy);
        astar.setMapInfo(mapInfo);
    }

    public void setEndPos(){
        mapInfo.endNode = new Node(posx,posy);
        astar.setMapInfo(mapInfo);
    }
}
