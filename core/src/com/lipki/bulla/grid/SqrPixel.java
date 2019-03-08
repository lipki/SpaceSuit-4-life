package com.lipki.bulla.grid;

import com.badlogic.gdx.math.Vector2;

public class SqrPixel {

    public Vector2 size;
    protected int corner;

    public SqrPixel(Vector2 size ) {

        this.size = size;
        this.corner = 4;

    }

    public Vector2 toPixel( SqrNode node ) {
        Vector2 pix = new Vector2();
        pix.x = (float)(node.q * size.x);
        pix.y = (float)(node.r * size.x);
        return pix;
    }
    
    public SqrNode pixelTo(Vector2 pix ) {
        Vector2 pt = new Vector2(pix.x /size.x, pix.y /size.y);
        Vector2 frhex = new Vector2();
        frhex.x = (float)(pt.x + pt.y);
        frhex.y = (float)(pt.x + pt.y);
        return SqrNode.round(frhex);
    }

    public Vector2 cornerOffset(double corner) {
        double angle = 2.0 * Math.PI * corner / this.corner;
    	float radius = (float)(size.x*Math.sqrt(3)/this.corner);
        return new Vector2(radius * (float)Math.cos(angle), radius * (float)Math.sin(angle));
    }
    
    public Vector2[] polygonCorners( SqrNode node ) {
        Vector2[] corners = new Vector2[3];
        Vector2 center = toPixel(node);
        for (int i = 0; i < 3; i++) {
            Vector2 offset = cornerOffset(i);
            corners[i] = new Vector2(center).add(offset);
        }
        return corners;
    }
    
    public String toString() {
        return "TriPixel : "+size;
    }

}
