package com.BindMapExample;

import javax.swing.*;
import java.awt.*;

public class Bind extends JFrame {
    mapPanel mapPanel = new mapPanel();
    ButtonPanel btnPanel = new ButtonPanel(mapPanel);
    public Bind(){
        setTitle("绑图");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mapPanel,BorderLayout.CENTER);
        add(btnPanel,BorderLayout.EAST);
        setVisible(true);
    }
}

