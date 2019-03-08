package com.lipki.bulla.scene2d.tilemap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.lipki.bulla.grid.HexNode;
import com.lipki.bulla.grid.TriNode;
import com.lipki.bulla.grid.aNode;

public class TileSetMisc extends TileSet {

    private Color[] colors;

    public TileSetMisc( Color[] colors ) {
        super("",new Vector2(), new Vector2(), new Vector2() );

        this.colors = colors;
    }

    @Override
    public void draw (Layer layer, SqrNode node, String id, ShapeRenderer shapes, Batch batch) {

        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapes.setAutoShapeType(true);
        shapes.begin();

        switch( id ) {

            case "threeColor" :
                threeColorDraw( shapes, layer, node );
                break;
            case "twoColorDraw" :
            	twoColorDraw( shapes, layer, node );
                break;
            case "randomColorDraw" :
            	randomColorDraw( shapes, layer, node );
                break;
            case "select" :
                select( shapes, layer, node );
                break;
            case "border" :
                border( shapes, layer, node );
                break;

        }

        shapes.end();

        batch.begin();
        Gdx.gl.glDisable(GL20.GL_BLEND);

    }

    private void border(ShapeRenderer shapes, Layer layer, SqrNode node) {

        Vector2[] dataSet = layer.pixelStuff.polygonCorners( node );

        shapes.set(ShapeRenderer.ShapeType.Line);
        shapes.setColor(colors[4]);

        for (Vector2 aDataSet : dataSet) aDataSet.add(layer.origin);

        for (int i = 0; i < dataSet.length; i++)
            shapes.line(dataSet[i], dataSet[(i + 1) % dataSet.length]);


    }

    private void select(ShapeRenderer shapes, Layer layer, SqrNode node ) {

        shapes.set(ShapeRenderer.ShapeType.Filled);

        Vector2 zero = layer.pixelStuff.toPixel( node ).add(layer.origin);
        Vector2[] dataSet = layer.pixelStuff.polygonCorners( node );


        System.out.println( layer.origin );

        for (Vector2 aDataSet : dataSet) aDataSet.add(layer.origin);

        for (int i = 0; i < dataSet.length; i++) {
            int in = (i + 1) % dataSet.length;
            shapes.triangle(zero.x, zero.y, dataSet[i].x, dataSet[i].y, dataSet[in].x, dataSet[in].y, colors[3], colors[3], colors[3]);
        }

    }

    private void threeColorDraw( ShapeRenderer shapes, Layer layer, SqrNode node ) {
        HexNode hex = (HexNode)node;

        shapes.set(ShapeRenderer.ShapeType.Filled);

        Vector2 zero = layer.pixelStuff.toPixel( node ).add(layer.origin);
        Vector2[] dataSet = layer.pixelStuff.polygonCorners( node );

        Color c = colors[0];

        int sn = 0, rn = 0;
        if( Math.abs(hex.s) != hex.s ) sn = 3;
        if( Math.abs(hex.r) != hex.r ) rn = 3;

        if( Math.abs(sn+hex.s%3)%3 == 0 ) {
            //if( Math.abs(rn+hex.r%3)%3 == 0 ) c = colors[0];
            if( Math.abs(rn+hex.r%3)%3 == 1 ) c = colors[1];
            if( Math.abs(rn+hex.r%3)%3 == 2 ) c = colors[2];
        }
        if( Math.abs(sn+hex.s%3)%3 == 1 ) {
            //if( Math.abs(rn+hex.r%3)%3 == 1 ) c = colors[0];
            if( Math.abs(rn+hex.r%3)%3 == 2 ) c = colors[1];
            if( Math.abs(rn+hex.r%3)%3 == 0 ) c = colors[2];
        }
        if( Math.abs(sn+hex.s%3)%3 == 2 ) {
            //if( Math.abs(rn+hex.r%3)%3 == 2 ) c = colors[0];
            if( Math.abs(rn+hex.r%3)%3 == 0 ) c = colors[1];
            if( Math.abs(rn+hex.r%3)%3 == 1 ) c = colors[2];
        }

        for (Vector2 aDataSet : dataSet) aDataSet.add(layer.origin);

        for (int i = 0; i < dataSet.length; i++) {
            int in = (i + 1) % dataSet.length;
            shapes.triangle(zero.x, zero.y, dataSet[i].x, dataSet[i].y, dataSet[in].x, dataSet[in].y, c, c, c);
        }

    }

    private void twoColorDraw( ShapeRenderer shapes, Layer layer, SqrNode node ) {
        TriNode tri = (TriNode)node;

        shapes.set(ShapeRenderer.ShapeType.Filled);

        Vector2 zero = layer.pixelStuff.toPixel( tri ).add(layer.origin);
        Vector2[] dataSet = layer.pixelStuff.polygonCorners( tri );
        
        boolean qpair = Math.floor(tri.q/2) == tri.q/2f;
        boolean rpair = Math.floor(tri.r/2) == tri.r/2f;
    	
        Color c = colors[0];
        if( qpair == rpair )
        	c = colors[1];

        for (Vector2 aDataSet : dataSet) aDataSet.add(layer.origin);

        for (int i = 0; i < dataSet.length; i++) {
            int in = (i + 1) % dataSet.length;
            shapes.triangle(zero.x, zero.y, dataSet[i].x, dataSet[i].y, dataSet[in].x, dataSet[in].y, c, c, c);
        }

    }

    private void randomColorDraw( ShapeRenderer shapes, Layer layer, SqrNode node ) {
        TriNode tri = (TriNode)node;

        shapes.set(ShapeRenderer.ShapeType.Filled);

        Vector2 zero = layer.pixelStuff.toPixel( tri ).add(layer.origin);
        Vector2[] dataSet = layer.pixelStuff.polygonCorners( tri );

        Color c = colors[(int)(Math.random()*colors.length)];

        for (Vector2 aDataSet : dataSet) aDataSet.add(layer.origin);

        for (int i = 0; i < dataSet.length; i++) {
            int in = (i + 1) % dataSet.length;
            shapes.triangle(zero.x, zero.y, dataSet[i].x, dataSet[i].y, dataSet[in].x, dataSet[in].y, c, c, c);
        }

    }


}
