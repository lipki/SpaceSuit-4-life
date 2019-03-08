package com.lipki.bulla.grid;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

public class SqrGrid {
	
	public boolean sorted = true;

    protected Map<Integer, Map<Integer, SqrNode>> indexedGrid = new HashMap<>();
    private Map<SqrNode, Object> unsortedGrid = new HashMap<>();
    private TreeMap<SqrNode, Object> sortedGrid = new TreeMap<>((node1, node2) -> {
        if( node1.equals(node2) ) return 0;
        int s1 = node1.q + node1.r;
        int s2 = node2.q + node2.r;
        return s1 < s2 ? 1 : ( s1 > s2 ? -1 : ( node1.r < node2.r ? 1 : -1 ) );
    });
    
    public SqrGrid (boolean sorted) {
    	this.sorted = sorted;
    }

    public void put( SqrNode node ) {
        put(node, null);
    }

    public void put( SqrNode node, Object var ) {
        indexedGrid.computeIfAbsent(node.q, k -> new HashMap<>());
        indexedGrid.get( node.q ).put( node.r, node );
        if( sorted ) sortedGrid.put( node, var );
        else       unsortedGrid.put( node, var );
    }

    public int size() {
        if( sorted ) return sortedGrid.size();
        else       return unsortedGrid.size();
        
    }

    public SqrNode get(SqrNode node ) {
        indexedGrid.computeIfAbsent(node.q, k -> new HashMap<>());
        return indexedGrid.get( node.q ).get( node.r );
    }

    public Object getInner( SqrNode node ) {
        if( sorted ) return sortedGrid.get( node );
        else       return unsortedGrid.get( node );
    }

    public void remove( SqrNode node ) {
        indexedGrid.computeIfAbsent(node.q, k -> new HashMap<>());
        indexedGrid.get( node.q ).remove(node.r);
        if( sorted ) sortedGrid.remove( node );
        else       unsortedGrid.remove( node );
        if( indexedGrid.get( node.q ).size() == 0 )
            indexedGrid.remove( node.q );
    }

    public void forEach(BiConsumer<SqrNode, Object> callback ) {
        if (callback == null)
             throw new NullPointerException();
        else 
            if( sorted ) sortedGrid.forEach(callback::accept);
            else       unsortedGrid.forEach(callback::accept);
    }

    public void clear() {
        indexedGrid.clear();
        sortedGrid.clear();
        unsortedGrid.clear();
    }

}
