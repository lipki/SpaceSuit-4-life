package com.lipki.bulla.scene2d.tilemap;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.lipki.bulla.grid.TriPixel;
import com.lipki.bulla.grid.aNode;

public class TileSetImage extends TileSet {
	
	private Map<String, Pixmap> images = new HashMap<>();

	public TileSetImage() {
        super("",new Vector2(), new Vector2(), new Vector2() );
        
	}

    @Override
    public void draw (Layer layer, SqrNode node, String id, ShapeRenderer shapes, Batch batch) {

        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapes.setAutoShapeType(true);
        shapes.begin();
    	
    	if( images.get(id) == null ) {
    		Texture texture = new Texture(Gdx.files.internal(id));
    		texture.getTextureData().prepare();
    		images.put( id, texture.getTextureData().consumePixmap());
    	}
    	
        //TriNode tri = (TriNode)node;

        shapes.set(ShapeRenderer.ShapeType.Filled);

        Vector2 zero = ((TriPixel)layer.pixelStuff).toPixel( node ).add(layer.origin);
        Vector2[] dataSet = layer.pixelStuff.polygonCorners( node );
        //System.out.println(layer.name);

        
        Color c = new Color(images.get(id).getPixel((int)(zero.x), (int)(zero.y)));

        for (Vector2 aDataSet : dataSet) aDataSet.add(layer.origin);

        for (int i = 0; i < dataSet.length; i++) {
            int in = (i + 1) % dataSet.length;
            shapes.triangle(zero.x, zero.y, dataSet[i].x, dataSet[i].y, dataSet[in].x, dataSet[in].y, c, c, c);
        }

        shapes.end();

        batch.begin();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    	
    }
	
	

}
