package com.lipki.bulla.grid;

import com.badlogic.gdx.math.Vector2;
import com.lipki.bulla.grid.TriOrientation;

public class TriPixel extends SqrPixel {

    private TriOrientation orientation;

    public TriPixel(TriOrientation orientation, Vector2 size ) {
    	super(size);
        this.corner = 3;
        this.orientation = orientation;
    }
    
    public int pair( TriNode node ) {
        boolean qpair = Math.floor(node.q/2) == node.q/2f;
        boolean rpair = Math.floor(node.r/2) == node.r/2f;

    	int pair = 1;
    	if( qpair == rpair ) pair = -1;
    	
    	return pair;
    }

	@Override
    public Vector2 toPixel( SqrNode node ) {
        TriNode tri = (TriNode)node;
        TriOrientation M = orientation;
        Vector2 pix = new Vector2();
        pix.x = (float)(tri.q * size.x * M.f0);
        pix.y = (float)(tri.r * size.x * M.f1);
    	int pair = pair( tri );
        pix.add( new Vector2( 
        		(float)(size.x * pair * M.f2),
        		(float)(size.x * pair * M.f3) ) );
        return pix;
    }

    @Override
    public TriNode pixelTo(Vector2 pix ) { // TODO
        TriOrientation M = orientation;
        Vector2 pt = new Vector2(pix.x /size.x, pix.y /size.y);
        Vector2 frhex = new Vector2();
        frhex.x = (float) (M.b0 * pt.x + M.b1 * pt.y);
        frhex.y = (float) (M.b2 * pt.x + M.b3 * pt.y);
        return TriNode.round(frhex);
    }

    @Override
    public Vector2[] polygonCorners( SqrNode node ) {
    	TriNode tri = (TriNode)node;
    	int pair = pair( tri );
    	float orient = pair > 0 ? .5f : 0;
        Vector2[] corners = new Vector2[3];
        Vector2 center = toPixel(tri);
        for (int i = 0; i < 3; i++) {
            Vector2 offset = cornerOffset(orientation.start_angle+orient+i);
            corners[i] = new Vector2(center).add(offset);
        }
        return corners;
    }

}
