package com.View;

import com.Algorithm.AiFactory;
import com.Algorithm.Astar;
import com.Algorithm.MapInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI extends JFrame {
    public static final int WIDTH = 1150;
    public static final int HEIGHT = 1450;

    public MapPanel mapPanel = null;
    public ListPanel listPanel = null;
    public ButtonPanel btnPanel = null;
    public Astar astar = null;
    public MapInfo mapInfo = null;
    private JMenuBar menuBar = null;

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
        this.menuBar = new JMenuBar();
        initMenuBar(menuBar);
        setJMenuBar(menuBar);
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

    public void initMenuBar(JMenuBar menuBar){
        this.menuBar = menuBar;
        ActionListener al = new AloSelectListener();
        ActionListener sl = new startListener();
        JMenu algorithmMenu = new JMenu("算法选择");
        JMenu optionMenu= new JMenu("选项");
        JMenuItem item1 = new JMenuItem("对角距离");
        JMenuItem item2 = new JMenuItem("曼哈顿距离");
        JMenuItem item3 = new JMenuItem("迪杰斯特拉");
        JMenuItem restart = new JMenuItem("重新开始");
        algorithmMenu.add(item1);
        algorithmMenu.add(item2);
        algorithmMenu.add(item3);
        optionMenu.add(restart);
        item1.addActionListener(al);
        item2.addActionListener(al);
        item3.addActionListener(al);
        restart.addActionListener(sl);
        this.menuBar.add(algorithmMenu);
        this.menuBar.add(optionMenu);
    }

    class AloSelectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem item = (JMenuItem) e.getSource();
            AiFactory factory = new AiFactory();
            String type = item.getText();
            setAstar(factory.getAi(type));
        }
    }

    class startListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            astar.restart();
            btnPanel.restart();
            mapPanel.restart();
        }
    }

}


