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
    private JMenuBar menuBar = null;

    private final AiFactory factory = new AiFactory();
    public Astar astar = null;
    public MapInfo mapInfo = null;

    String ImgName;
    String GridName;

    public UI(Astar astar){
        setSize(WIDTH,HEIGHT);
        setTitle("A*算法路径规划");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        initAlgorithm(astar);
    }

    private void initAlgorithm(Astar astar){
        this.astar = astar;
        initPanel();
        setVisible(true);
    }

    private void initPanel(){
        this.mapPanel = new MapPanel(astar,"map1.jpg");
        this.ImgName = "map1.jpg";
        this.GridName = "mat1.txt";
        this.listPanel = new ListPanel(astar);
        this.btnPanel = new ButtonPanel(astar,listPanel,mapPanel);
        this.menuBar = new JMenuBar();

        initMenuBar(menuBar);
        setJMenuBar(menuBar);

        add(mapPanel,BorderLayout.CENTER);
        add(listPanel,BorderLayout.EAST);
        add(btnPanel,BorderLayout.SOUTH);
    }

    private void initMenuBar(JMenuBar menuBar){
        this.menuBar = menuBar;

        ActionListener al = new AloSelectListener();
        ActionListener sl = new startListener();
        ActionListener gl = new gridListener();
        ActionListener mil = new mapImgListener();
        ActionListener mgl = new mapGridListener();

        JMenu algorithmMenu = new JMenu("算法选择");
        JMenu optionMenu= new JMenu("选项");

        JMenuItem item1 = new JMenuItem("对角距离");
        JMenuItem item2 = new JMenuItem("曼哈顿距离");
        JMenuItem item4 = new JMenuItem("双向对角");
        JMenuItem restart = new JMenuItem("重新开始");
        JMenuItem showGrid = new JMenuItem("显示/隐藏栅格");
        JMenu selectMapImg = new JMenu("选择图片");
        JMenu selectMapGrid = new JMenu("选择栅格地图");
        JMenuItem mapImg1 = new JMenuItem("图片1");
        JMenuItem mapImg2 = new JMenuItem("图片2");
        JMenuItem mapGrid1 = new JMenuItem("地图1");
        JMenuItem mapGrid2 = new JMenuItem("地图2");

        selectMapImg.add(mapImg1);
        selectMapImg.add(mapImg2);
        selectMapGrid.add(mapGrid1);
        selectMapGrid.add(mapGrid2);

        algorithmMenu.add(item1);
        algorithmMenu.add(item2);
        algorithmMenu.add(item4);
        optionMenu.add(restart);
        optionMenu.add(showGrid);
        optionMenu.addSeparator();
        optionMenu.add(selectMapImg);
        optionMenu.add(selectMapGrid);

        item1.addActionListener(al);
        item2.addActionListener(al);
        item4.addActionListener(al);
        restart.addActionListener(sl);
        showGrid.addActionListener(gl);
        mapImg1.addActionListener(mil);
        mapImg2.addActionListener(mil);
        mapGrid1.addActionListener(mgl);
        mapGrid2.addActionListener(mgl);

        this.menuBar.add(algorithmMenu);
        this.menuBar.add(optionMenu);
    }

    public void setAstar(Astar astar) {
        this.astar = astar;
        this.mapInfo = astar.getMapInfo();

        mapPanel.setAstar(astar);
        btnPanel.setAstar(astar);
        listPanel.setAstar(astar);
    }

    class AloSelectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem item = (JMenuItem) e.getSource();
            String type = item.getText();
            mapInfo = astar.getMapInfo();
            Astar astar = factory.getAi(type);
            astar.setMapInfo(mapInfo);
            setAstar(astar);
            btnPanel.restart();
        }
    }

    class startListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Astar astar = factory.getAi("对角距离");
            astar.loadMap(GridName);
            setAstar(astar);

            mapInfo = null;
            btnPanel.restart();
            mapPanel.restart();
            listPanel.update();
        }
    }

    class gridListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mapPanel.showGrid = !mapPanel.showGrid;
            mapPanel.update();
        }
    }

    class mapImgListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem item = (JMenuItem) e.getSource();
            String name = item.getText();
            if("图片1".equals(name)){
                mapPanel.loadMapImage("map1.jpg");
            }else if("图片2".equals(name)){
                mapPanel.loadMapImage("map2.jpg");
            }
            mapPanel.update();
        }
    }

    class mapGridListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem item = (JMenuItem) e.getSource();
            String name = item.getText();
            if("地图1".equals(name)){
                astar.loadMap("mat1.txt");
                GridName = "mat1.txt";
            }else if("地图2".equals(name)){
                astar.loadMap("mat2.txt");
                GridName = "mat2.txt";
            }
            mapPanel.update();
        }
    }

}


