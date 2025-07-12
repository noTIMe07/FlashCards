package app.animation;

import java.util.LinkedList;

public class Graph {
    private Edge[][] matrix;

    public Graph(){
        matrix = new Edge[NodeId.values().length][NodeId.values().length];

        addEdge(NodeId.WINDOWBOARD_LEFT, NodeId.WINDOWBOARD_RIGHT, MovementType.WALK, 3,false);
        addEdge(NodeId.WINDOWBOARD_LEFT, NodeId.TABLE_LEFT, MovementType.JUMP_DOWN, 3,true);
        addEdge(NodeId.TABLE_LEFT, NodeId.WINDOWBOARD_LEFT, MovementType.JUMP_UP, 3, true);
        addEdge(NodeId.TABLE_LEFT, NodeId.TABLE_MIDDLE, MovementType.WALK, 3,false);
        addEdge(NodeId.TABLE_MIDDLE, NodeId.TABLE_RIGHT, MovementType.WALK, 1, false);
        addEdge(NodeId.TABLE_LEFT, NodeId.TABLE_RIGHT, MovementType.WALK, 1, false);
        addEdge(NodeId.TABLE_MIDDLE, NodeId.CHAIR, MovementType.JUMP_DOWN, 3, true);
        addEdge(NodeId.CHAIR_SIT, NodeId.TABLE_MIDDLE, MovementType.JUMP_UP, 3, true);
        addEdge(NodeId.CHAIR, NodeId.CHAIR_SIT, MovementType.SIT, 3, false);
//        addEdge(NodeId.CHAIR_SIT, NodeId.FLOOR_BELOWTABLE, MovementType.JUMP_DOWN, true);
//        addEdge(NodeId.FLOOR_BELOWTABLE, NodeId.CHAIR, MovementType.JUMP_UP, true);
//        addEdge(NodeId.FLOOR_BELOWTABLE, NodeId.FLOOR_LEFT, MovementType.WALK, false);
//        addEdge(NodeId.TABLE_RIGHT, NodeId.SHELF_LEFT, MovementType.JUMP_DOWN, true);
//        addEdge(NodeId.SHELF_LEFT, NodeId.TABLE_RIGHT, MovementType.JUMP_UP, true);
//        addEdge(NodeId.SHELF_LEFT, NodeId.SHELF_RIGHT, MovementType.WALK, false);
    }

    private void addEdge(NodeId node1, NodeId node2, MovementType movementType, int layerIndex, boolean directed){
        matrix[node1.ordinal()][node2.ordinal()] = new Edge(node1, node2, movementType, layerIndex);
        if(!directed) matrix[node2.ordinal()][node1.ordinal()] = new Edge(node2, node1, movementType, layerIndex);
    }

    public LinkedList<Edge> findPath(NodeId from, NodeId to){
        LinkedList<NodeId> queue = new LinkedList<>();
        NodeId[] predecessor = new NodeId[NodeId.values().length];
        boolean[] visited = new boolean[NodeId.values().length];

        queue.addLast(from);
        visited[from.ordinal()] = true;
        predecessor[from.ordinal()] = null;

        while(!queue.isEmpty()){
            NodeId current = queue.removeFirst();

            if (current == to) break;

            for(int i = 0; i < NodeId.values().length; i++){
                if(matrix[current.ordinal()][i]!=null && !visited[i]){
                    NodeId neighbor = NodeId.values()[i];
                    queue.addLast(neighbor);
                    visited[i] = true;
                    predecessor[i] = current;
                }
            }
        }

        // Reconstruct path
        LinkedList<Edge> path = new LinkedList<>();
        for (NodeId current = to; predecessor[current.ordinal()] != null; current = predecessor[current.ordinal()]) {
            NodeId prev = predecessor[current.ordinal()];
            path.addFirst(matrix[prev.ordinal()][current.ordinal()]);
        }

        return path;
    }
}
