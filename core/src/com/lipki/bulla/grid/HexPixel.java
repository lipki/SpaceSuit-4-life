package com.lipki.bulla.grid;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.lipki.bulla.grid.aNode;
import com.lipki.bulla.grid.aPixel;

public class HexPixel extends aPixel {

    private com.lipki.bulla.grid.HexOrientation orientation;
    public Vector2 size;

    public HexPixel(com.lipki.bulla.grid.HexOrientation orientation, Vector2 size ) {

        this.orientation = orientation;
        this.size = size;

    }

    @Override
    public Vector2 toPixel( SqrNode node ) {
        HexNode hex = (HexNode)node;
        com.lipki.bulla.grid.HexOrientation M = orientation;
        Vector2 pix = new Vector2();
        pix.x = (float)(M.f0 * hex.q + M.f1 * hex.r) * size.x;
        pix.y = (float)(M.f2 * hex.q + M.f3 * hex.r) * size.y;
        return pix;
    }

    @Override
    public SqrNode pixelTo(Vector2 pix ) {
        com.lipki.bulla.grid.HexOrientation M = orientation;
        Vector2 pt = new Vector2(pix.x /size.x, pix.y /size.y);
        Vector3 frhex = new Vector3();
        frhex.x = (float) (M.b0 * pt.x + M.b1 * pt.y);
        frhex.y = (float) (M.b2 * pt.x + M.b3 * pt.y);
        frhex.z = -frhex.x-frhex.y;
        return HexNode.round(frhex);
    }

    private Vector2 cornerOffset(int corner) {
        double angle = 2.0 * Math.PI * (orientation.start_angle + corner) / 6;
        return new Vector2(size.x * (float)Math.cos(angle), size.y * (float)Math.sin(angle));
    }

    @Override
    public Vector2[] polygonCorners( SqrNode node ) {
        Vector2[] corners = new Vector2[6];
        Vector2 center = toPixel(node);
        for (int i = 0; i < 6; i++) {
            Vector2 offset = cornerOffset(i);
            corners[i] = new Vector2(center).add(offset);
        }
        return corners;
    }

    @Override
    public String toString() {
        return "HexPixel : "+orientation+","+size;
    }

}
