package com.lipki.bulla.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.lipki.bulla.GC;
import com.lipki.bulla.grid.TriGrid;
import com.lipki.bulla.grid.TriNode;
import com.lipki.bulla.grid.TriOrientation;
import com.lipki.bulla.grid.TriPixel;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.lipki.bulla.scene2d.tilemap.Layer;
import com.lipki.bulla.scene2d.tilemap.Tile;
import com.lipki.bulla.scene2d.tilemap.TileMap;
import com.lipki.bulla.scene2d.tilemap.TileSetImage;
import com.lipki.bulla.scene2d.tilemap.TileSetMisc;

public class ExplorationPhase extends GamePhase {

    private TileMap tilemap;
	
	public ExplorationPhase() {
		super( "ExplorationPhase" );

        tilemap = new TileMap( Color.RED );
        //tilemap.setDebug(true);
        phaseStage.addActor( tilemap );
        
        
        //Color c = new Color().fromHsv( 360, 1, 1 );
        Color[] colors = new Color[10];
        int b = (int) (Math.random()*360);
        for( int i = 0 ; i< 10 ; i++ ) {
        	colors[i] = new Color().fromHsv( b, (float)Math.random()/2f+.2f, .7f );
        	colors[i].a = 1;
        }
        tilemap.addTileSet( "misc", new TileSetMisc( colors ) );
        
        
        //image tileSet
        tilemap.addTileSet( "image", new TileSetImage() );
        

        //tilemap.addLayer( "back", new Layer(new TriPixel( Orientation.FLAT, new Vector2((float)(Math.sqrt(3)*radiusbig/2), radiusbig ) ), GC.center, new TriGrid()));
        tilemap.addLayer( "backP", new Layer(new TriPixel( TriOrientation.POINTY, new Vector2(20, 20) ), GC.center, new TriGrid()));
        //tilemap.addLayer( "backF", new Layer(new TriPixel( Orientation.FLAT, new Vector2(100, 100 ) ), GC.center, new TriGrid()));

        TriNode.parallelograms( 0, 64, 0, 64, (q, r) -> {
        	tilemap.getLayer("backP").setTile( new TriNode(q,r), new Tile("image", "Underwater1.jpg"));
        });
        
        /*TriNode.parallelograms( 1, 5, -2, 2, (q, r) -> {
        	tilemap.getLayer("backF").setTile( new TriNode(q,r), new Tile("misc", "border"));
        });*/
        
        
        //phaseStage.addActor( new Image(new Texture(Gdx.files.internal("devlogo.png"))) );
        
        
	}

	@Override
	public void resize(int width, int height) {
		super.resize( width, height );
		tilemap.resize( new Vector2(0,0) );
		if( ((TriPixel)tilemap.getLayer("backP").pixelStuff).size != null ) {
			System.out.println(width/64f);
			((TriPixel)tilemap.getLayer("backP").pixelStuff).size.x = height/32f;
			((TriPixel)tilemap.getLayer("backP").pixelStuff).size.y = height/32f;
		}
	}

    @Override
    public void dispose() {
    	tilemap.dispose();
        super.dispose();
    }
	
	
}
