package com.BindMap;

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
                Notify(mapPanel,"empty");
            }
        });

        obstacleBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Notify(mapPanel,"obstacle");
            }
        });

        fileBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(check()){
                    File mat = new File("mat.txt");
                    BufferedWriter bw = null;
                    try {
                        mat.createNewFile();
                        bw = new BufferedWriter(new FileWriter(mat));
                        for (int i = 0; i < map[0].length; i++) {
                            for (int j = 0; j < map.length; j++) {
                                bw.write(map[j][i]+"");
                                if(j != map[0].length - 1) bw.write(",");
                            }
                            bw.write("\n");
                        }
                        bw.close();
                        JOptionPane.showMessageDialog(null,"生成成功");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"没选完");
                }
            }
        });
    }

    private boolean check() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if(map[i][j] == 0){
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public void Notify(Observable o,String status) {
        o.update(status);
    }
}
