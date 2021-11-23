package com.View;

import com.Algorithm.Astar;
import com.Algorithm.MapInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapPanel extends JPanel {
    /*
    map中 0----路-------灰色
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

    int posx,poxy;  //此时选择的点的map数组坐标
    public MapPanel(Astar astar) {
        this.astar = astar;
        this.mapInfo = astar.getMapInfo();
        loadMap();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //读取mapInfo坐标，实现标点，设置起始点，结束点

            }
        });
    }

    public void loadMap(){
        this.icon = new ImageIcon(getClass().getResource("/image/map.jpg"));
        this.mapImg = icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(mapImg,0,0,mapImg.getWidth(null),mapImg.getHeight(null),null);
    }

    public void setAstar(Astar astar) {
        this.astar = astar;
    }

    public void setBegPos(){

    }

    public void setEndPos(){

    }
}
