package com.lipki.bulla.scene2d.tilemap;

import com.lipki.bulla.grid.aNode;

public class LayerEvent {

    public static final int SET_TILE = 10001;
    public static final int SET_TILE_INNER = 10002;
    public static final int REMOVE_TILE = 10003;

    public final Layer target;
    public final int id;
    public final SqrNode node;


    public LayerEvent( Layer target, int id, SqrNode node) {
        this.target = target;
        this.id = id;
        this.node = node;
    }
}
