package com.lipki.bulla.scene2d.tilemap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.lipki.bulla.GC;
import com.lipki.bulla.grid.HexNode;
import com.lipki.bulla.grid.TriPixel;
import com.lipki.bulla.grid.aNode;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TileMap extends Actor {

    private Color debugColor;

    private ShapeRenderer shapes = new ShapeRenderer();

    private Array<String> layersOrder = new Array<>();
    private Map<String, Layer> layers = new HashMap<>();
    private Map<String, TileSet> tileSets = new HashMap<>();

    public TileMap( Color debugColor ) {
        this.debugColor = debugColor;
    }

    public Layer addLayer( String name, Layer layer ) {
        layers.put( name, layer );
        layersOrder.add( name );
        layer.name = name;
        return layer;
    }

    public Layer getLayer( String name ) {
        return layers.get( name );
    }

    public void addlayerVirtual() {
    }

    public TileSet addTileSet( String name, TileSet tileSet ) {
        tileSets.put( name, tileSet );
        return tileSet;
    }


    public void addAuto( String in, String out, BiConsumer<LayerEvent, Layer> callback ) {
        getLayer(in).addListener(getLayer(out));
        getLayer(out).auto.put(getLayer(in), callback);
    }




    @Override
    public void draw (Batch batch, float parentAlpha) {

        shapes.setProjectionMatrix(getStage().getCamera().combined);

        ((Array<String>) layersOrder).forEach(new Consumer<String>() {
			@Override
			public void accept(String name) {
				Layer layer = getLayer(name);
				layer.forEach(new BiConsumer<SqrNode, Object>() {
					@Override
					public void accept(SqrNode node, Object obj) {

					    Tile tile = (Tile)layer.getTileInner( (SqrNode)node );
					    if( tile != null )
					        if( tile.tileset != null & tile.id != null )
					            tileSets.get(tile.tileset).draw( layer, (SqrNode)node, tile.id, shapes, batch);

					}
				});
			}
		});

    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {

        ((Array<String>) layersOrder).forEach(new Consumer<String>() {
			@Override
			public void accept(String name) {
				Layer layer = getLayer(name);
				layer.forEach((node, var) -> {

		            Vector2 zero = layer.pixelStuff.toPixel( (SqrNode)node ).add(GC.center);
		            Vector2[] dataSet = layer.pixelStuff.polygonCorners( (SqrNode)node );
		
		            shapes.set(ShapeType.Filled);
		            shapes.setColor(debugColor);
		
		            for (Vector2 aDataSet : dataSet) aDataSet.add(new Vector2(getX(), getY()));
		
		            for (int i = 0; i < dataSet.length; i++) {
		            	//shapes.circle(zero.x, zero.y, 50);
		                //int in = (i + 1) % dataSet.length;
		                //shapes.triangle(zero.x, zero.y, dataSet[i].x, dataSet[i].y, dataSet[in].x, dataSet[in].y, Color.PINK, debugColor, debugColor);
		                shapes.line(dataSet[i], dataSet[(i + 1) % dataSet.length]);
		            }
				});
			}
		});

        super.drawDebug(shapes);
    }

    public void resize(Vector2 origin) {
        layers.forEach((name, layer) -> {
            layer.origin = origin;
        });
    }

	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
