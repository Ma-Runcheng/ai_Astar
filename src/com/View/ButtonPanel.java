package com.View;

import com.Algorithm.Astar;
import com.Observable;
import com.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.Timer;


public class ButtonPanel extends JPanel implements Observable{
    Astar astar;
    ListPanel listPanel;
    MapPanel mapPanel;
    List<Observer> observers = new ArrayList<>();

    JButton begin = new JButton("确定起始位置");
    JButton end = new JButton("确定终点位置");
    JButton start = new JButton("开始算法");
    JButton nextStep = new JButton("下一步");
    JButton endStep = new JButton("显示全部");

    public ButtonPanel(Astar astar, ListPanel listPanel, MapPanel mapPanel) {
        this.astar = astar;
        this.listPanel = listPanel;
        this.mapPanel = mapPanel;

        setLayout(new GridLayout());

        //设置是否可用
        begin.setEnabled(true);
        end.setEnabled(true);
        start.setEnabled(false);
        nextStep.setEnabled(false);
        endStep.setEnabled(false);

        //添加事件,通知mapPanel重绘
        begin.addMouseListener(new beginListener());
        end.addMouseListener(new endListener());
        start.addMouseListener(new startListener());
        nextStep.addMouseListener(new nextStepListener());
        endStep.addMouseListener(new endStepListener());

        add(begin);
        add(end);
        add(start);
        add(nextStep);
        add(endStep);
    }

    public void setAstar(Astar astar){
        this.astar = astar;
    }

    public void restart(){
        begin.setEnabled(true);
        end.setEnabled(true);
        start.setEnabled(false);
        nextStep.setEnabled(false);
        endStep.setEnabled(false);
    }

    @Override
    public void Notify() {
        for(Observer o : observers){
            o.update();
        }
    }

    @Override
    public void register(Observer o) {
        if(!observers.contains(o)){
            observers.add(o);
        }
    }


    private class beginListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            //设置起始点，调mapPanel
            super.mouseClicked(e);
            if(mapPanel.setBegPos())
                begin.setEnabled(false);
            if(!begin.isEnabled()&&!end.isEnabled())
                start.setEnabled(true);
        }
    }

    private class endListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            //设置终点
            super.mouseClicked(e);
            if(mapPanel.setEndPos())
               end.setEnabled(false);
            if(!begin.isEnabled()&&!end.isEnabled())
                start.setEnabled(true);
        }
    }

    private class startListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            //开始算法
            super.mouseClicked(e);
            astar.start();
            nextStep.setEnabled(true);
            endStep.setEnabled(true);
            listPanel.update();
        }
    }

    private class nextStepListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            //下一步，通知mapPanel重绘,判断是否到重点，到重点则展现完成路径
            //调nextStep，更新List
            super.mouseClicked(e);
            if(!astar.canFind()){
                JOptionPane.showMessageDialog(null,"找不到路径");
            }else if(astar.isEnd()){
                astar.goEnd();
                astar.setRoute();
                nextStep.setEnabled(false);
                endStep.setEnabled(false);
            }else{
                astar.nextStep();
            }
            Notify();
        }
    }

    private class endStepListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            //直接展示完整路径
            super.mouseClicked(e);
            if(astar.goEnd()){
                astar.setRoute();
            }else{
                JOptionPane.showMessageDialog(null,"找不到路径");
            }
            nextStep.setEnabled(false);
            endStep.setEnabled(false);
            Notify();
        }
    }
}
