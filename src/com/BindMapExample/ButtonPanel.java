package com.BindMapExample;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class ButtonPanel extends JPanel{
    JButton roadBtn = new JButton("设置空路");
    JButton obstacleBtn = new JButton("设置障碍物");
    JButton fileBtn = new JButton("导出文件");
    mapPanel mapPanel = null;
    int[][] map = null;

    public ButtonPanel(mapPanel mapPanel) {
        this.mapPanel = mapPanel;
        this.map = mapPanel.mapGrid;
        add(roadBtn);
        add(obstacleBtn);
        add(fileBtn);

        roadBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mapPanel.setStatus(-1);
            }
        });

        obstacleBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mapPanel.setStatus(1);
            }
        });

        fileBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                File file = new File("mat.txt");
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(file));
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    if(check()){
                        for(int i = 0; i < 70; i++){
                            for(int j = 0; j < 40; j++){
                                bw.write(mapPanel.mapGrid[i][j]+",");
                            }
                            bw.write("\n");
                        }
                    }
                    bw.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    private boolean check() {
        for(int i = 0; i < 70; i++){
            for(int j = 0; j < 40; j++){
                if(mapPanel.mapGrid[i][j] == 0){
                    return false;
                }
            }
        }
        return true;
    }
}
