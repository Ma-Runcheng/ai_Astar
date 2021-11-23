package com.View;

import com.Algorithm.Astar;
import com.Algorithm.MapInfo;

import javax.swing.*;
import java.awt.*;

public class UI extends JFrame {
    public static final int WIDTH = 1150;
    public static final int HEIGHT = 1450;

    public MapPanel mapPanel = null;
    public ListPanel listPanel = null;
    public ButtonPanel btnPanel = null;
    public Astar astar = null;
    public MapInfo mapInfo = null;

    public UI(){
        setSize(WIDTH,HEIGHT);
        setTitle("A*算法路径规划");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
    }

    public void loadAlgorithm(Astar astar){
        this.astar = astar;
        this.mapInfo = astar.mapInfo;
        init();
        setVisible(true);
    }

    private void init(){
        this.mapPanel = new MapPanel(astar);
        this.listPanel = new ListPanel(astar);
        this.btnPanel = new ButtonPanel(astar,listPanel,mapPanel);
        add(mapPanel,BorderLayout.CENTER);
        add(listPanel,BorderLayout.EAST);
        add(btnPanel,BorderLayout.SOUTH);
    }

    public void setAstar(Astar astar) {
        this.astar = astar;
        mapPanel.setAstar(astar);
        btnPanel.setAstar(astar);
        listPanel.setAstar(astar);
    }
}
