package app.animation;

public class Edge {
    public final NodeId from;
    public final NodeId to;
    public final MovementType movementType;

    public Edge(NodeId from, NodeId to, MovementType movementType) {
        this.from = from;
        this.to = to;
        this.movementType = movementType;
    }
}

