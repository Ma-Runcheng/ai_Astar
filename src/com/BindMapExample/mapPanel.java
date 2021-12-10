package com.BindMapExample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class mapPanel extends JPanel{
    private ImageIcon icon = new ImageIcon(getClass().getResource("/image/map.jpg"));
    private Image mapImg = icon.getImage();
    public int[][] mapGrid = new int[70][40];//1为障碍物,-1为空路,0为未设置
    public int status = -1;
    int posx,posy;
    boolean isPressed = false;
    public mapPanel() {
        setSize(mapImg.getWidth(null),mapImg.getHeight(null));
        for(int i = 0; i < 70; i++) Arrays.fill(mapGrid[i],-1);
        this.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                posx = roundx(e.getX());
                posy = roundy(e.getY());
                mapGrid[posy][posx] = status;
                repaint();
            }
        });
    }

    private int roundx(int x){
        return x/16;
    }
    private int roundy(int y) {
        return (int)(y/19.8);
    }


    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(mapImg,0,0,mapImg.getWidth(null),mapImg.getHeight(null),null);
        for (int i = 0; i < 70; i++) {
            for (int j = 0; j < 40; j++) {
                if (mapGrid[i][j] == 0) {
                    g.setColor(Color.black);
                    g.drawRect( j * 16, (int) (i * 19.8),8, 8);
                } else if (mapGrid[i][j] == 1) {
                    g.setColor(Color.red);
                    g.fillRect(j * 16, (int) (i * 19.8), 8, 8);
                } else if (mapGrid[i][j] == -1) {
                    g.setColor(Color.green);
                    g.fillRect(  j * 16,(int) (i * 19.8),8, 8);
                }
            }
        }
    }

    void setStatus(int status){
        this.status = status;
    }
}
