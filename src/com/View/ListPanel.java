package com.View;

import com.Algorithm.Astar;
import com.Algorithm.Node;
import com.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class ListPanel extends JPanel implements Observer{
    public Astar astar;
    public JTable table;
    public JScrollPane scrollPane;
    int lastOpenSize = 0;
    int lastCloseSize = 0;

    public ListPanel(Astar astar) {
        this.astar = astar;
        astar.register(this);
        setLayout(new BorderLayout());
        Object[] head = {"OpenList", "CloseList"};
        Object[][] rowData = new Object[2000][2000];
        table = new JTable(rowData, head);
        scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    public void setAstar(Astar astar) {
        this.astar = astar;
        astar.register(this);
    }

    @Override
    public void update() {
        Queue<Node> openList = astar.getOpenList();
        List<Node> closeList = astar.getCloseList();
        int index = 0;
        Object[] open = openList.toArray();
        Arrays.sort(open);

        for(Object Node : open){
            Node node = (Node) Node;
            String nodePos = "("+node.pos.x+","+node.pos.y+")"+"  F= "+node.F;
            table.getModel().setValueAt(nodePos,index++,0);
        }
        index--;
        while(index < lastOpenSize && lastOpenSize >= 0){
            table.getModel().setValueAt("",lastOpenSize--,0);
        }
        lastOpenSize = index;
        index = 0;
        for(Node node : closeList){
            String nodePos = "("+node.pos.x+","+node.pos.y+")";
            table.getModel().setValueAt(nodePos,index++,1);
        }
        index--;
        while(index < lastCloseSize && lastCloseSize >= 0){
            table.getModel().setValueAt("",lastCloseSize--,1);
        }
        lastCloseSize = index;
    }
}
