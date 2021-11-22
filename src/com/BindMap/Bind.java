package com.BindMap;

import javax.swing.*;
import java.awt.*;

public class Bind extends JFrame {
    mapPanel mapPanel = new mapPanel();
    ButtonPanel btnPanel = new ButtonPanel(mapPanel);
    public Bind(){
        setTitle("绑图工具");
        add(mapPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.EAST);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

