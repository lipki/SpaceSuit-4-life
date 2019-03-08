package com.lipki.bulla.grid;

import com.badlogic.gdx.math.Vector2;

public class TriNode extends SqrNode {

    private static TriNode[] directions = {
        new TriNode(1, 0), new TriNode(1, -1), new TriNode(0, -1), new TriNode(-1, -1),
        new TriNode(-1, 0), new TriNode(-1, 1), new TriNode(0, 1), new TriNode(1, 1)
    };
    
    public TriNode(int q, int r) {
		super(q, r);
	}
    
	@Override
    public TriNode clone() {
        return new TriNode( q, r );
    }

    public static TriNode add( SqrNode a, SqrNode b ) {
        return new TriNode( a.q+b.q, a.r+b.r );
    }

    private static TriNode sub( SqrNode a, SqrNode b ) {
        return new TriNode( a.q-b.q, a.r-b.r );
    }

    private static TriNode mul(SqrNode node, int k) {
        return new TriNode( node.q*k, node.r*k );
    }

    public static TriNode rotateLeft( SqrNode hex ) { // TODO réécrire
        return new TriNode(-hex.q, -hex.r);
    }

    public static TriNode rotateRight( SqrNode hex ) { // TODO réécrire
        return new TriNode(-hex.r, -hex.q);
    }

    @Override
    protected TriNode direction(int direction) {
        assert (0 <= direction && direction <= 7);
        return directions[direction];
    }

    static TriNode round(Vector2 frhex) { //TODO
        int nq = Math.round(frhex.x);
        int nr = Math.round(frhex.y);
        double q_diff = Math.abs(nq - frhex.x);
        double r_diff = Math.abs(nr - frhex.y);
        return new TriNode(nq, nr);
    }

    @Override
    public TriNode neighbor( int direction ) {
        return add(this, direction(direction));
    }

    @Override
    protected TriNode neighbor(int direction, int distance) {
        return add(this, mul( direction(direction), distance ) );
    }

    @Override
    public TriNode[] neighbors() {
        return neighbors(1);
    }

    @Override
    public TriNode[] neighbors( int distance ) {
    	TriNode[] list = new TriNode[6];
        for ( int k = 0 ; k < 6 ; k++ )
            list[k] = neighbor(k, distance);
        return list;
    }



    @Override
    public TriNode[] lineDraw( SqrNode node ) {
        int N = distance( node );
        TriNode[] results = new TriNode[N+1];
        double step = 1.0 / Math.max(N, 1);
        for (int i = 0; i <= N; i++)
            results[i] = round(hexLerp(new Vector2( q, r ), new Vector2( node.q, node.r ), step*i));

        return results;
    }
}