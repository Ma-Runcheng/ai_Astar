package com;

import com.Algorithm.Astar;
import com.Algorithm.AstarImpl;
import com.View.UI;

public class test {
    public static void main(String[] args) {
        Astar ai = new AstarImpl();
        UI ui = new UI();

        ai.loadMap();
        ui.loadAlgorithm(ai);

    }
}
