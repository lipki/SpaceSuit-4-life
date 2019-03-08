package com.lipki.bulla.grid;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

public class TriGrid extends SqrGrid {
    
    public TriGrid (boolean sorted) {
    	super(sorted);
    }

    public TriNode get(SqrNode node ) {
        indexedGrid.computeIfAbsent(node.q, k -> new HashMap<>());
        return (TriNode) indexedGrid.get( node.q ).get( node.r );
    }

    public Object getInner( SqrNode node ) {
        TriNode hex = (TriNode)node;
        return sortedGrid.get( hex );
    }

    public void remove( SqrNode node ) {
        TriNode hex = (TriNode)node;
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
