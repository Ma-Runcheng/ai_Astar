package com.BindMapExample;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ButtonPanel extends JPanel implements Observer{
    JButton roadBtn = new JButton("设置空路");
    JButton obstacleBtn = new JButton("设置障碍物");
    JButton fileBtn = new JButton("导出文件");
    mapPanel mapPanel;
    int[][] map = null;
    public ButtonPanel(mapPanel mapPanel) {
        this.mapPanel = mapPanel;
        this.map = mapPanel.mapGrid;
        add(roadBtn);
        add(obstacleBtn);
        add(fileBtn);

        roadBtn.addMouseListener(new MouseAdapter() {

        });

        obstacleBtn.addMouseListener(new MouseAdapter() {

        });

        fileBtn.addMouseListener(new MouseAdapter() {

        });
    }


    @Override
    public void Notify(Observable o,String status) {

    }
}
