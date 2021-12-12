package com.BindMapExample;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class ButtonPanel extends JPanel{
    JButton roadBtn = new JButton("设置空路");
    JButton obstacleBtn = new JButton("设置障碍物");
    JButton fileBtn = new JButton("导出文件");
    mapPanel mapPanel;
    int[][] map;

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
                File file = new File("mat2.txt");
                BufferedWriter bw;
                try {
                    bw = new BufferedWriter(new FileWriter(file));
                    if(!file.exists()){
                        boolean res = file.createNewFile();
                        if(res) System.out.println("文件生成成功");
                        else System.out.println("文件生成失败");
                    }
                    if(check()){
                        for(int i = 0; i < 70; i++){
                            for(int j = 0; j < 70; j++){
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
            for(int j = 0; j < 70; j++){
                if(mapPanel.mapGrid[i][j] == 0){
                    return false;
                }
            }
        }
        return true;
    }
}
