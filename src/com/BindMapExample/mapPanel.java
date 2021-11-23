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
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = round(e.getX());
                int y = round(e.getY());
                System.out.println(x+ " " + y);
                mapGrid[x][y] = status;
                repaint();
            }
        });
    }

    private int round(int x){
        return x / 20;
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(mapImg,0,0,mapImg.getWidth(null),mapImg.getHeight(null),null);
        for (int i = 0; i < wNum; i++) {
            for (int j = 0; j < hNum; j++) {
                if(mapGrid[i][j] == 1){
                    g.setColor(Color.RED);
                    g.fillRect(i*20,j*20,5,5);
                }else if(mapGrid[i][j] == -1){
                    g.setColor(Color.GREEN);
                    g.fillRect(i*20,j*20,5,5);
                }else{
                    g.setColor(Color.BLACK);
                    g.drawRect(i*20,j*20,5,5);
                }
            }
        }
    }

    @Override
    public void update(String status) {
        if("empty".equals(status)){
            getGraphics().setColor(Color.GREEN);
            this.status = -1;
        }else if("obstacle".equals(status)){
            getGraphics().setColor(Color.RED);
            this.status = 1;
        }else if("file".equals(status)){

        }
    }
}
