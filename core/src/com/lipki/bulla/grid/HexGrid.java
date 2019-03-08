package com.lipki.bulla.grid;

import com.lipki.bulla.grid.aGrid;
import com.lipki.bulla.grid.aNode;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

public class HexGrid extends aGrid {

    private Map<Integer, Map<Integer, HexNode>> indexedGrid = new HashMap<>();
    private TreeMap<HexNode, Object> sortedGrid = new TreeMap<>((hex1, hex2) -> {
        if( hex1.equals(hex2) ) return 0;
        int s1 = hex1.q + hex1.r;
        int s2 = hex2.q + hex2.r;
        return s1 < s2 ? 1 : ( s1 > s2 ? -1 : ( hex1.r < hex2.r ? 1 : -1 ) );
    });

    public void put( SqrNode node ) {
        HexNode hex = (HexNode)node;
        put(hex, null);
    }

    public void put( SqrNode node, Object var ) {
        HexNode hex = (HexNode)node;
        indexedGrid.computeIfAbsent(hex.q, k -> new HashMap<>());
        indexedGrid.get( hex.q ).put( hex.r, hex );
        sortedGrid.put( hex, var );
    }

    public int size() {
        return sortedGrid.size();
    }

    public SqrNode get(SqrNode node ) {
        HexNode hex = (HexNode)node;
        indexedGrid.computeIfAbsent(hex.q, k -> new HashMap<>());
        return indexedGrid.get( hex.q ).get( hex.r );
    }

    public Object getInner( SqrNode node ) {
        HexNode hex = (HexNode)node;
        return sortedGrid.get( hex );
    }

    public void remove( SqrNode node ) {
        HexNode hex = (HexNode)node;
        indexedGrid.computeIfAbsent(hex.q, k -> new HashMap<>());
        indexedGrid.get( hex.q ).remove(hex.r);
        sortedGrid.remove( hex );
        if( indexedGrid.get( hex.q ).size() == 0 )
            indexedGrid.remove(hex.q);
    }

    public void forEach(BiConsumer<SqrNode, Object> callback ) {

        if (callback == null) {
            throw new NullPointerException();
        } else {
            sortedGrid.forEach(callback::accept);
        }
    }

    public void clear() {
        indexedGrid.clear();
        sortedGrid.clear();
    }

}
