package com.lipki.bulla.scene2d.tilemap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.lipki.bulla.grid.aNode;

import java.util.HashMap;

public class TileSet {

    private Vector2 uv;
    private Vector2 originTileSize;
    private Vector2 zoom; // Percentage

    private HashMap<Integer, TextureRegion> regions = new HashMap<>();
    private Texture texture;

    public TileSet(String internalPath, Vector2 uv, Vector2 center, Vector2 zoom ) {

        if(!internalPath.equals("")) {
            texture = new Texture( internalPath );

            this.uv = uv;
            this.zoom = zoom;

            originTileSize = new Vector2(texture.getWidth()/uv.x, texture.getHeight()/uv.y);
        }
    }

    public void draw (Layer layer, SqrNode node, String sid, ShapeRenderer shapes, Batch batch) {

        int id = Integer.valueOf(sid);
        int tx = (int)((id%uv.x)*originTileSize.x);
        int ty = (int)(Math.floor(id/uv.x)*originTileSize.y);

        if( regions.get( id ) == null ) {
            regions.put(id, new TextureRegion(texture, tx, ty, (int)originTileSize.x, (int)originTileSize.y));
            //regions.get( id ).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // c'est null, bordure noir
        }

        Vector2 zero = layer.pixelStuff.toPixel( node ).add(layer.origin);

        batch.draw(
            regions.get( id ), // TextureRegion
            zero.x-(originTileSize.x*zoom.x)/2, // x
            zero.y-(originTileSize.y*zoom.y)/2, // y
            originTileSize.x*zoom.x, // width
            originTileSize.y*zoom.y // height
        );

    }
}
