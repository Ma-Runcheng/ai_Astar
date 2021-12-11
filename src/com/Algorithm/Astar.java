package com.Algorithm;

import com.Observable;

import java.util.List;
import java.util.Queue;

public interface Astar extends Observable{
    /*
    map中 -1----路-------灰色
          1----障碍------红色
          2----结果路径-------蓝色
          3----搜索中路径cur------中心绿色
          4----cur的children------分布在绿色周围黄色
          5----被选择状态------紫色
          6----起始点和终点颜色------黑色
     */
    MapInfo mapInfo = null;
    void setMapInfo(MapInfo mapInfo);
    MapInfo getMapInfo();
    Queue<Node> getOpenList();
    List<Node> getCloseList();

    void loadMap();
    void start(); //算法入口
    void nextStep(); //下一步搜索更新open，close表
    void goEnd(); //直接走完算法，可以到重点 -- true，无法到达--false

    void setRoute(Node finalNode); //改变mapInfo并通知界面
    boolean isEnd();
    boolean canFind();

    void restart();
}
