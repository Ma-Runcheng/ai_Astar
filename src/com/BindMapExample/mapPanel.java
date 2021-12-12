package com.BindMapExample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Arrays;

class mapPanel extends JPanel{
    private final ImageIcon icon = new ImageIcon(getClass().getResource("/image/map2.jpg"));
    private final Image mapImg = icon.getImage();
    public int[][] mapGrid = new int[70][70];//1为障碍物,-1为空路,0为未设置
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
                posx = roundX(e.getX());
                posy = roundY(e.getY());
                mapGrid[posy][posx] = status;
                repaint();
            }
        });
    }

    private int roundX(int x){
        return (int)(x/17.55);
    }
    private int roundY(int y) {
        return (int)(y/17.55);
    }


    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(mapImg,0,0,mapImg.getWidth(null),mapImg.getHeight(null),null);
        for (int i = 0; i < 70; i++) {
            for (int j = 0; j < 70; j++) {
                if (mapGrid[i][j] == 0) {
                    g.setColor(Color.black);
                    g.drawRect( (int)(j * 17.55), (int)(i * 17.55),4, 4);
                } else if (mapGrid[i][j] == 1) {
                    g.setColor(Color.red);
                    g.fillRect((int)(j * 17.55), (int)(i * 17.55), 4, 4);
                } else if (mapGrid[i][j] == -1) {
                    g.setColor(Color.green);
                    g.fillRect(  (int)(j * 17.55),(int)(i * 17.55),4, 4);
                }
            }
        }
    }

    void setStatus(int status){
        this.status = status;
    }
}
