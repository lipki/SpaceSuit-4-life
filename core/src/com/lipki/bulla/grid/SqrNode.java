package com.lipki.bulla.grid;

import java.util.Objects;
import java.util.function.BiConsumer;

import com.badlogic.gdx.math.Vector2;

public class SqrNode {

    public final int q, r;
    
    private static SqrNode[] directions = {
        new SqrNode(1, 0), new SqrNode(1, -1), new SqrNode(0, -1), new SqrNode(-1, -1),
        new SqrNode(-1, 0), new SqrNode(-1, 1), new SqrNode(0, 1), new SqrNode(1, 1)
    };

    public SqrNode (int q, int r) {
        this.q = q;
        this.r = r;
    }

    public SqrNode clone() {
        return new SqrNode( q, r );
    }
    
    public boolean equals(SqrNode node) {
        return this.q == node.q && this.r == node.r;
    }

    public boolean equals( Vector2 pix ) {
        return this.q == pix.x && this.r == pix.y;
    }
    
    public boolean equals( Object var1 ) {
    	SqrNode node = (SqrNode)var1;
        return this.q == node.q && this.r == node.r;
    }

    public static SqrNode add( SqrNode a, SqrNode b ) {
        return new SqrNode( a.q+b.q, a.r+b.r );
    }

    private static SqrNode sub(SqrNode a, SqrNode b) {
        return new SqrNode( a.q-b.q, a.r-b.r );
    }

    private static SqrNode mul(SqrNode node, int k) {
        return new SqrNode( node.q*k, node.r*k );
    }

    public static SqrNode rotateLeft( SqrNode node ) { //TODO
        return new SqrNode(-node.q, -node.r);
    }

    public static SqrNode rotateRight( SqrNode node ) { //TODO
        return new SqrNode(-node.r, -node.q);
    }

    public int length() { //TODO
        return (Math.abs(q) + Math.abs(r)) / 2;
    }

    public int distance(SqrNode node) {
        return sub(node,this).length();
    }
    
    protected SqrNode direction(int direction) {
        assert (0 <= direction && direction <= 7);
        return directions[direction];
    }

    static SqrNode round(Vector2 frhex) { //TODO
        int nq = Math.round(frhex.x);
        int nr = Math.round(frhex.y);
        double q_diff = Math.abs(nq - frhex.x);
        double r_diff = Math.abs(nr - frhex.y);
        return new SqrNode(nq, nr);
    }
    
    public SqrNode neighbor(int direction) {
        return add(this, direction(direction));
    }
    
    protected SqrNode neighbor(int direction, int distance) {
        return add(this, mul( direction(direction), distance ) );
    }

    public SqrNode[] neighbors() {
        return neighbors(1);
    }
    
    public SqrNode[] neighbors( int distance ) {
    	SqrNode[] list = new SqrNode[6];
        for ( int k = 0 ; k < 6 ; k++ )
            list[k] = neighbor(k, distance);
        return list;
    }

    private static float lerp(double a, double b, double t) {
        return (float)( a * (1-t) + b * t );
    }

    protected static Vector2 hexLerp( Vector2 a, Vector2 b, double t ) {
        return new Vector2( lerp(a.x, b.x, t),
                            lerp(a.y, b.y, t));
    }

    public SqrNode[] lineDraw( SqrNode node ) {
        int N = distance( node );
        SqrNode[] results = new SqrNode[N+1];
        double step = 1.0 / Math.max(N, 1);
        for (int i = 0; i <= N; i++)
            results[i] = round(hexLerp(new Vector2( q, r ), new Vector2( node.q, node.r ), step*i));

        return results;
    }

    public static void rectangles( int q1, int q2, int r1, int r2 , BiConsumer<? super Integer, ? super Integer> callback ) {
        if (callback == null) {
            throw new NullPointerException();
        } else {
            for ( int q = q1; q <= q2; q++ )
                for (int r = r1; r <= r2; r++)
                    callback.accept( q, r );
        }
    }
    
    public int hashCode() {
        return Objects.hash (q, r);
    }

    public String toString() {
        return this.getClass().getName()+" : "+q+","+r;
    }
    
}
