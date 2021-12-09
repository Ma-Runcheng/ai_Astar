package com.View;

import com.Algorithm.Astar;
import com.Algorithm.Node;
import com.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Queue;

public class ListPanel extends JPanel{
    Astar astar;
    public JTable table;
    public JScrollPane scrollPane;
    Object[] head = {"OpenList","CloseList"};
    Object[][] rowData =  new Object[1000][1000];
    List<Node> closeList = null;
    Queue<Node> openList = null;

    public ListPanel(Astar astar) {
        this.astar = astar;
        setLayout(new BorderLayout());
        table = new JTable(rowData,head);
        scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    void updateData(){
        openList = astar.openList;
        closeList = astar.closeList;
        int index = 0;
        for(Node node : openList){
            String nodePos = "("+node.pos.x+","+node.pos.y+")"+"  F= "+node.F;
            table.getModel().setValueAt(nodePos,index++,0);
        }
        index = 0;
        for(Node node : closeList){
            String nodePos = "("+node.pos.x+","+node.pos.y+")";
            table.getModel().setValueAt(nodePos,index++,1);
        }

    }

    public void setAstar(Astar astar) {
        this.astar = astar;
    }
}
