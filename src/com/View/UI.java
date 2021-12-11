package com.View;

import com.Algorithm.AiFactory;
import com.Algorithm.Astar;
import com.Algorithm.MapInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UI extends JFrame {
    public static final int WIDTH = 1150;
    public static final int HEIGHT = 1450;

    public MapPanel mapPanel = null;
    public ListPanel listPanel = null;
    public ButtonPanel btnPanel = null;
    public Astar astar = null;
    public MapInfo mapInfo = null;
    private JMenuBar menuBar = null;
    Set<Astar> usedAstar = new HashSet<>();
    AiFactory factory = new AiFactory();


    public UI(){
        setSize(WIDTH,HEIGHT);
        setTitle("A*算法路径规划");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
    }

    public void loadAlgorithm(Astar astar){
        this.astar = astar;
        usedAstar.add(astar);
        this.mapInfo = astar.mapInfo;
        init();
        setVisible(true);
    }

    private void init(){
        this.mapPanel = new MapPanel(astar);
        this.listPanel = new ListPanel(astar);
        this.btnPanel = new ButtonPanel(astar,listPanel,mapPanel);
        this.menuBar = new JMenuBar();
        initMenuBar(menuBar);
        setJMenuBar(menuBar);
        add(mapPanel,BorderLayout.CENTER);
        add(listPanel,BorderLayout.EAST);
        add(btnPanel,BorderLayout.SOUTH);
    }

    public void setAstar(Astar astar) {
        this.astar = astar;
        usedAstar.add(astar);

        mapPanel.setAstar(astar);
        btnPanel.setAstar(astar);
        listPanel.setAstar(astar);
    }

    public void initMenuBar(JMenuBar menuBar){
        this.menuBar = menuBar;

        ActionListener al = new AloSelectListener();
        ActionListener sl = new startListener();
        ActionListener gl = new gridListener();

        JMenu algorithmMenu = new JMenu("算法选择");
        JMenu optionMenu= new JMenu("选项");

        JMenuItem item1 = new JMenuItem("对角距离");
        JMenuItem item2 = new JMenuItem("曼哈顿距离");
        JMenuItem item3 = new JMenuItem("迪杰斯特拉");
        JMenuItem item4 = new JMenuItem("双向对角");
        JMenuItem restart = new JMenuItem("重新开始");
        JMenuItem showGrid = new JMenuItem("显示/隐藏栅格");

        algorithmMenu.add(item1);
        algorithmMenu.add(item2);
        algorithmMenu.add(item3);
        algorithmMenu.add(item4);
        optionMenu.add(restart);
        optionMenu.add(showGrid);

        item1.addActionListener(al);
        item2.addActionListener(al);
        item3.addActionListener(al);
        item4.addActionListener(al);
        restart.addActionListener(sl);
        showGrid.addActionListener(gl);

        this.menuBar.add(algorithmMenu);
        this.menuBar.add(optionMenu);
    }

    class AloSelectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem item = (JMenuItem) e.getSource();
            String type = item.getText();
            setAstar(factory.getAi(type));
            astar.restart();
            btnPanel.restart();
        }
    }

    class startListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for(Astar a : usedAstar){
                a.restart();
            }
            usedAstar.clear();
            setAstar(factory.getAi("对角距离"));
            btnPanel.restart();
            mapPanel.restart();
        }
    }

    class gridListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapPanel.showGrid = !mapPanel.showGrid;
            mapPanel.update();
        }
    }

}


