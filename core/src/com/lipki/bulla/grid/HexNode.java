package com.lipki.bulla.grid;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.lipki.bulla.grid.aNode;

import java.util.Objects;
import java.util.function.BiConsumer;

public class HexNode extends SqrNode {

    public final int q, r, s;

    public HexNode (int q, int r) {
        this (q, r, -q-r );
    }

    public HexNode (int q, int r, int s ) {
        assert ( q+r+s == 0 );
        this.q = q;
        this.r = r;
        this.s = s;
    }

    @Override
    public SqrNode clone() {
        return new HexNode( q, r, s );
    }


    boolean equals(HexNode hex) {
        return this.q == hex.q && this.r == hex.r;
    }

    public boolean equals( Vector2 pix ) {
        return this.q == pix.x && this.r == pix.y;
    }

    public boolean equals( Vector3 pix ) {
        return this.q == pix.x && this.r == pix.y && this.s == pix.z;
    }

    @Override
    public boolean equals( Object var1 ) {
        HexNode hex = (HexNode)var1;
        return this.q == hex.q && this.r == hex.r;
    }


    public static SqrNode add( SqrNode a, SqrNode b ) {
        HexNode ha = (HexNode)a;
        HexNode hb = (HexNode)b;
        return new HexNode( ha.q+hb.q, ha.r+hb.r );
    }

    private static SqrNode sub(SqrNode a, SqrNode b) {
        HexNode ha = (HexNode)a;
        HexNode hb = (HexNode)b;
        return new HexNode( ha.q-hb.q, ha.r-hb.r );
    }

    private static SqrNode mul(SqrNode node, int k) {
        HexNode hex = (HexNode)node;
        return new HexNode( hex.q*k, hex.r*k );
    }

    public static SqrNode rotateLeft( HexNode hex ) {
        return new HexNode(-hex.s, -hex.q, -hex.r);
    }

    public static SqrNode rotateRight( HexNode hex ) {
        return new HexNode(-hex.r, -hex.s, -hex.q);
    }


    @Override
    public int length() {
        return (Math.abs(q) + Math.abs(r) + Math.abs(s)) / 2;
    }

    @Override
    public int distance(SqrNode node) {
        HexNode hex = (HexNode)node;
        return sub(hex,this).length();
    }


    private static SqrNode[] directions = {
        new HexNode(1, 0, -1), new HexNode(1, -1, 0), new HexNode(0, -1, 1),
        new HexNode(-1, 0, 1), new HexNode(-1, 1, 0), new HexNode(0, 1, -1)
    };

    @Override
    protected SqrNode direction(int direction) {
        assert (0 <= direction && direction <= 5);
        return directions[direction];
    }

    static SqrNode round(Vector3 frhex) {
        int nq = Math.round(frhex.x);
        int nr = Math.round(frhex.y);
        int ns = Math.round(frhex.z);
        double q_diff = Math.abs(nq - frhex.x);
        double r_diff = Math.abs(nr - frhex.y);
        double s_diff = Math.abs(ns - frhex.z);
        if ( q_diff > r_diff && q_diff > s_diff )
            nq = -nr - ns;
        else if ( r_diff > s_diff )
            nr = -nq - ns;
        else
            ns = -nq - nr;
        return new HexNode(nq, nr, ns);
    }

    private static float lerp(double a, double b, double t) {
        return (float)( a * (1-t) + b * t );
    }

    private static Vector3 hexLerp( Vector3 a, Vector3 b, double t ) {
        return new Vector3( lerp(a.x, b.x, t),
                            lerp(a.y, b.y, t),
                            lerp(a.z, b.z, t));
    }






    public SqrNode neighbor( int direction ) {
        return add(this, direction(direction));
    }

    @Override
    protected SqrNode neighbor(int direction, int distance) {
        return add(this, mul( direction(direction), distance ) );
    }

    public SqrNode[] neighbors() {
        return neighbors(1);
    }

    @Override
    public SqrNode[] neighbors( int distance ) {
        SqrNode[] list = new HexNode[6];
        for ( int k = 0 ; k < 6 ; k++ )
            list[k] = neighbor(k, distance);
        return list;
    }



    @Override
    public SqrNode[] lineDraw( SqrNode b ) {
        HexNode hex = (HexNode)b;
        int N = distance( hex );
        SqrNode[] results = new HexNode[N+1];
        double step = 1.0 / Math.max(N, 1);
        for (int i = 0; i <= N; i++)
            results[i] = round(hexLerp(new Vector3( q, r, s ), new Vector3( hex.q, hex.r, hex.s ), step*i));

        return results;
    }

    public static void parallelograms( int q1, int q2, int r1, int r2 , BiConsumer<? super Integer, ? super Integer> callback ) {
        if (callback == null) {
            throw new NullPointerException();
        } else {
            for ( int q = q1; q <= q2; q++ )
                for (int r = r1; r <= r2; r++)
                    callback.accept( q, r );
        }
    }

    public static void triangles( int edge, BiConsumer<? super Integer, ? super Integer> callback ) {
        if (callback == null) {
            throw new NullPointerException();
        } else {
            for (int q = 0; q <= edge; q++)
                for (int r = 0; r <= edge - q; r++)
                    callback.accept( q, r );
        }
    }

    public static void hexagons( int radius, BiConsumer<? super Integer, ? super Integer> callback ) {
        if (callback == null) {
            throw new NullPointerException();
        } else {
            for (int q = -radius; q <= radius; q++) {
                int r1 = Math.max(-radius, -q - radius);
                int r2 = Math.min( radius, -q + radius);
                for (int r = r1; r <= r2; r++)
                    callback.accept(q, r);
            }
        }
    }

    public static void rectangles( int width, int height, BiConsumer<? super Integer, ? super Integer> callback ) {
        if (callback == null) {
            throw new NullPointerException();
        } else {
            for (int r = 0; r < height; r++) {
                int r_offset = r >> 1;
                for (int q = -r_offset; q < width - r_offset; q++)
                    callback.accept(q, r);
            }
        }
    }

    public static void ring( int radius, BiConsumer<? super Integer, ? super Integer> callback ) {
        if (callback == null) {
            throw new NullPointerException();
        } else {
            for( int c = 0; c < 6; c++ )
                for( int s = 0; s < radius; s++ ) {
                    int q = ( c%3 == 0 ? 0 : 1) * (Math.floor(c / 3) == 0 ? 1 : -1);
                    int r = ( c%3 == 1 ? 0 : 1) * (Math.floor((c+1) / 3) != 1 ? 1 : -1);
                    callback.accept(q, r);
            }
        }
    }




    @Override
    public int hashCode() {
        return Objects.hash (q, r, s);
    }

    @Override
    public String toString() {
        return "Hex : "+q+","+r+","+s;
    }
}