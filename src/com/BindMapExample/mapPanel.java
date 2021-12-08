package com.BindMapExample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class mapPanel extends JPanel implements Observable{
    private ImageIcon icon = new ImageIcon(getClass().getResource("/image/map.jpg"));
    private Image mapImg = icon.getImage();
    private int wNum = mapImg.getWidth(null) / 20;
    private int hNum = mapImg.getHeight(null) / 20;
    public int[][] mapGrid = new int[wNum][hNum];//1为障碍物,-1为空路,0为未设置
    int status = -1;
    public mapPanel() {
        this.addMouseListener(new MouseAdapter() {

        });
    }




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }

    @Override
    public void update(String status) {

    }
}
