package app.animation;

public class Edge {
    public final NodeId from;
    public final NodeId to;
    public final MovementType movementType;
    public final int layerIndex;

    public Edge(NodeId from, NodeId to, MovementType movementType, int layerIndex) {
        this.from = from;
        this.to = to;
        this.movementType = movementType;
        this.layerIndex = layerIndex;
    }
}

