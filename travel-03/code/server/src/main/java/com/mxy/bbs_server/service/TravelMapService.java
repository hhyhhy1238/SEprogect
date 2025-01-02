package com.mxy.bbs_server.service;

import com.google.common.graph.MutableValueGraph;
import com.mxy.bbs_server.entity.TravelMap;
import com.mxy.bbs_server.entity.TravelPlan;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@NoArgsConstructor
public class TravelMapService {
    private final TravelMap travelMap = TravelMap.getInstance();
    private final MutableValueGraph<String,Integer> graph = travelMap.getTravelMapGraph();

    public List<String> findShortestPath(TravelPlan travelPlan){
        String startLocation = travelPlan.getStartLocation();
        String endLocation = travelPlan.getEndLocation();

        // init Dijkstra
        Map<String,Integer> shortestDistances = new HashMap<>();
        Map<String,String> previousNodes = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>((a, b) -> Integer.compare(shortestDistances.get(a),shortestDistances.get(b)));

        // init distance
        for(String node:graph.nodes()){
            shortestDistances.put(node,Integer.MAX_VALUE);
        }
        shortestDistances.put(startLocation,0);
        queue.offer(startLocation);

        // run Dijkstra algorithm
        while(!queue.isEmpty()){
            String currentNode = queue.poll();
            if(currentNode.equals(endLocation)){
                break;
            }
            for(String neighbor : graph.adjacentNodes(currentNode)){
                Integer distance = shortestDistances.get(currentNode) + graph.edgeValue(currentNode,neighbor).get();
                if(distance < shortestDistances.get(neighbor)){
                    shortestDistances.put(neighbor,distance);
                    previousNodes.put(neighbor,currentNode);
                    queue.offer(neighbor);
                }
            }
        }

        // construct Path
        List<String> shortestPath = new ArrayList<>();
        String node = endLocation;
        while(node != null){
            shortestPath.add(0,node);
            node = previousNodes.get(node);
        }
        return shortestPath;
    }
}
