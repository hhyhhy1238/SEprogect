package com.mxy.bbs_server.entity;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import lombok.Data;
import lombok.Getter;


public class TravelMap {
    private static TravelMap travelMap = null;
    @Getter
    private MutableValueGraph<String,Integer> travelMapGraph;
    private TravelMap(){
        travelMapGraph = ValueGraphBuilder.undirected().build();

        travelMapGraph.addNode("八达岭长城");
        travelMapGraph.addNode("圆明园");
        travelMapGraph.addNode("颐和园");
        travelMapGraph.addNode("香山");
        travelMapGraph.addNode("奥林匹克森林公园");
        travelMapGraph.addNode("鸟巢");
        travelMapGraph.addNode("地坛");
        travelMapGraph.addNode("798艺术区");
        travelMapGraph.addNode("西单");
        travelMapGraph.addNode("南锣鼓巷");
        travelMapGraph.addNode("故宫");
        travelMapGraph.addNode("孔庙");
        travelMapGraph.addNode("雍和宫");
        travelMapGraph.addNode("国家博物馆");
        travelMapGraph.addNode("世贸");
        travelMapGraph.addNode("三里屯");
        travelMapGraph.addNode("王府井");

        travelMapGraph.putEdgeValue("八达岭长城","香山",92);
        travelMapGraph.putEdgeValue("八达岭长城","颐和园",84);
        travelMapGraph.putEdgeValue("八达岭长城","圆明园",87);
        travelMapGraph.putEdgeValue("八达岭长城","奥林匹克森林公园",78);
        travelMapGraph.putEdgeValue("颐和园","圆明园",22);
        travelMapGraph.putEdgeValue("颐和园","奥林匹克森林公园",36);
        travelMapGraph.putEdgeValue("颐和园","香山",14);
        travelMapGraph.putEdgeValue("颐和园","鸟巢",21);
        travelMapGraph.putEdgeValue("颐和园","故宫",33);
        travelMapGraph.putEdgeValue("颐和园","南锣鼓巷",32);
        travelMapGraph.putEdgeValue("奥林匹克森林公园","圆明园",32);
        travelMapGraph.putEdgeValue("奥林匹克森林公园","鸟巢",8);
        travelMapGraph.putEdgeValue("奥林匹克森林公园","地坛",40);
        travelMapGraph.putEdgeValue("奥林匹克森林公园","798艺术区",47);
        travelMapGraph.putEdgeValue("鸟巢","地坛",21);
        travelMapGraph.putEdgeValue("鸟巢","故宫",40);
        travelMapGraph.putEdgeValue("鸟巢","孔庙",25);
        travelMapGraph.putEdgeValue("故宫","南锣鼓巷",18);
        travelMapGraph.putEdgeValue("故宫","孔庙",27);
        travelMapGraph.putEdgeValue("故宫","世贸",23);
        travelMapGraph.putEdgeValue("故宫","国家博物馆",18);
        travelMapGraph.putEdgeValue("南锣鼓巷","香山",42);
        travelMapGraph.putEdgeValue("南锣鼓巷","西单",23);
        travelMapGraph.putEdgeValue("南锣鼓巷","国家博物馆",30);
        travelMapGraph.putEdgeValue("西单","香山",38);
        travelMapGraph.putEdgeValue("西单","国家博物馆",16);
        travelMapGraph.putEdgeValue("世贸","孔庙",23);
        travelMapGraph.putEdgeValue("世贸","国家博物馆",34);
        travelMapGraph.putEdgeValue("世贸","王府井",27);
        travelMapGraph.putEdgeValue("世贸","三里屯",17);
        travelMapGraph.putEdgeValue("王府井","国家博物馆",19);
        travelMapGraph.putEdgeValue("王府井","三里屯",35);
        travelMapGraph.putEdgeValue("雍和宫","三里屯",20);
        travelMapGraph.putEdgeValue("雍和宫","孔庙",1);
        travelMapGraph.putEdgeValue("雍和宫","地坛",5);
        travelMapGraph.putEdgeValue("雍和宫","798艺术区",17);
        travelMapGraph.putEdgeValue("地坛","孔庙",8);
        travelMapGraph.putEdgeValue("地坛","798艺术区",17);
    }

    public static TravelMap getInstance(){
        if(travelMap == null){
            travelMap = new TravelMap();
        }
        return travelMap;
    }

}
