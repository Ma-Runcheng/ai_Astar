package com;

import com.Algorithm.AiFactory;
import com.Algorithm.Astar;
import com.View.UI;

public class test {
    public static void main(String[] args) {
        AiFactory factory = new AiFactory();
        Astar ai = factory.getAi("对角距离");
        ai.loadMap("mat1.txt");

        UI ui = new UI(ai);
        ui.mapInfo = ai.getMapInfo();
    }
}
