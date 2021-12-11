package com.View;

import com.Algorithm.Astar;
import com.Algorithm.MapInfo;
import com.Algorithm.Node;
import com.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class MapPanel extends JPanel implements Observer {
    /*
    map中 -1----路-------灰色
          1----障碍------红色
          2----结果路径-------蓝色
          3----搜索中路径cur------中心绿色
          4----cur的children------分布在绿色周围黄色
          5----被选择状态------紫色
          6----起始点和终点的颜色------黑色
     */
    ImageIcon icon = null;
    Image mapImg = null;
    String mapName;
    Astar astar;
    MapInfo mapInfo;
    int count=0;
    int posx,posy;  //此时选择的点的map数组坐标
    double scaleX,scaleY;
    boolean showGrid = true;

    public MapPanel(Astar astar,String mapName) {
        this.astar = astar;
        this.mapInfo = astar.getMapInfo();
        astar.register(this);
        this.mapName = mapName;
        loadMapImage(mapName);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //读取mapInfo坐标，实现标点，设置起始点，结束点
                super.mouseClicked(e);
                posx = roundX(e.getX());//map[y][x]
                posy = roundY(e.getY());
                System.out.println(posx + " " + posy);

                if(mapInfo.map[posy][posx] == 5){
                    mapInfo.map[posy][posx] = -1;
                    count--;
                }else if(mapInfo.map[posy][posx] != 1){
                    mapInfo.map[posy][posx] = 5;
                    count++;
                }
                repaint();

            }
        });
    }

    private int roundX(int x){
        return (int)(x / scaleX);
    }
    private int roundY(int y) {
       return (int)(y/ scaleY);
    }

    public void loadMapImage(String mapName){
        this.mapName = mapName;
        this.icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/image/"+mapName)));
        this.mapImg = icon.getImage();
        update();
    }

    public void restart(){
        this.mapInfo = astar.getMapInfo();
        update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(mapImg,0,0,mapImg.getWidth(null),mapImg.getHeight(null),null);
        //判断画点
        int[][] map = mapInfo.map;
        for (int i = 0; i < mapInfo.ROWS; i++) {
            for (int j = 0; j < mapInfo.COLS; j++) {
                if(map[i][j] == 1 && showGrid){
                    g.setColor(Color.red);
                    g.fillRect((int)(j*scaleX),(int)(i*scaleY),10,10);
                }
                if(map[i][j]==-1 && showGrid){
                    g.setColor(Color.gray);
                    g.fillRect((int)(j*scaleX),(int)(i*scaleY),10,10);
                }
                if(map[i][j]==2){
                    g.setColor(Color.blue);
                    g.fillRect((int)(j*scaleX),(int)(i*scaleY),10,10);
                }
                if(map[i][j]==3){
                    g.setColor(Color.green);
                    g.fillRect((int)(j*scaleX),(int)(i*scaleY),10,10);
                }
                if(map[i][j]==4){
                    g.setColor(Color.yellow);
                    g.fillRect((int)(j*scaleX),(int)(i*scaleY),10,10);
                }
                if(map[i][j]==5){
                    g.setColor(Color.magenta);
                    g.fillRect((int)(j*scaleX),(int)(i*scaleY),10,10);
                }
                if(map[i][j]==6){
                    g.setColor(Color.black);
                    g.fillRect((int)(j*scaleX),(int)(i*scaleY),10,10);
                }
            }
        }
    }

    public void setAstar(Astar astar) {
        this.astar = astar;
        this.mapInfo = astar.getMapInfo();
        astar.register(this);
    }

    public boolean setBegPos(){
        //给ai传起始点
        mapInfo.begNode = new Node(posy,posx);
        if(count!=1){
            JOptionPane.showMessageDialog(null, "不能找到起始点", "Title",JOptionPane.ERROR_MESSAGE);
            repaint();
            return false;
        }
        mapInfo.map[posy][posx]=6;
        astar.setMapInfo(mapInfo);
        repaint();
        count--;
        return true;
    }

    public boolean setEndPos(){
        mapInfo.endNode = new Node(posy,posx);
        if(count!=1){
            JOptionPane.showMessageDialog(null, "不能找到终点", "Title",JOptionPane.ERROR_MESSAGE);
            repaint();
            return false;
        }
        mapInfo.map[posy][posx]=6;
        astar.setMapInfo(mapInfo);
        repaint();
        count--;
        return true;
    }

    @Override
    public void update() {
        this.mapInfo = astar.getMapInfo();
        scaleX = 1.0 * mapImg.getWidth(null) / mapInfo.COLS;
        scaleY = 1.0 * mapImg.getHeight(null) / mapInfo.ROWS;
        repaint();
    }
}
