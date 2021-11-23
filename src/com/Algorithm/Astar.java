package com.Algorithm;

import com.Observable;

import java.util.List;
import java.util.Queue;

public interface Astar {
    /*
    map中 0----路-------灰色
          1----障碍------红色
          2----结果路径-------蓝色
          3----搜索中路径cur------中心绿色
          4----cur的childs------分布在绿色周围黄色
          5----被选择状态------紫色
     */
    Queue<Node> openList = null;
    List<Node> closeList = null;
    MapInfo mapInfo = null;
    boolean isEnd = false;

    void setMapInfo(MapInfo mapInfo);
    MapInfo getMapInfo();
    Queue<Node> getOpenList();
    List<Node> getCloseList();

    void loadMap();
    void start(); //算法入口
    void nextStep(); //下一步搜索更新open，close表
    boolean goEnd(); //直接走完算法，可以到重点 -- true，无法到达--false

    void setRoute(); //改变mapInfo并通知界面
}
