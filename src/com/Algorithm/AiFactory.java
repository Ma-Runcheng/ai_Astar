package com.Algorithm;

public class AiFactory {
    public AiFactory() {
    }

    public Astar getAi(String type){
        if("对角距离".equals(type)){
            return new AstarImpl1();
        }else if ("曼哈顿距离".equals(type)){
            return new AstarImpl2();
        }else if("双向对角".equals(type)){
            return new AstarImpl3();
        }
        return null;
    }
}
