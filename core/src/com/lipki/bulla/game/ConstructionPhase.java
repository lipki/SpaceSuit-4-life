package com.lipki.bulla.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.lipki.bulla.GC;
import com.lipki.bulla.grid.HexGrid;
import com.lipki.bulla.grid.HexNode;
import com.lipki.bulla.grid.HexOrientation;
import com.lipki.bulla.grid.HexPixel;
import com.lipki.bulla.grid.TriGrid;
import com.lipki.bulla.grid.TriNode;
import com.lipki.bulla.grid.TriPixel;
import com.lipki.bulla.grid.aNode;
import com.lipki.bulla.scene2d.tilemap.Layer;
import com.lipki.bulla.scene2d.tilemap.LayerEvent;
import com.lipki.bulla.scene2d.tilemap.Tile;
import com.lipki.bulla.scene2d.tilemap.TileMap;
import com.lipki.bulla.scene2d.tilemap.TileSet;
import com.lipki.bulla.scene2d.tilemap.TileSetMisc;

public class ConstructionPhase extends GamePhase {
	
	public TileMap tilemap;
	
	public ConstructionPhase() {
		super( "ConstructionPhase" );

        tilemap = new TileMap( Color.RED );
        //tilemap.setDebug(true);
        phaseStage.addActor( tilemap );

        Color[] colors = { Color.valueOf("07A0E8"), Color.valueOf("05E0FF"), Color.valueOf("007BFF"), Color.valueOf("37FE5C"), new Color(0,0,0,0.05f) };
        tilemap.addTileSet( "misc", new TileSetMisc( colors ) );
        tilemap.addTileSet( "hexa", new TileSet("maps/tilesethexa.png", new Vector2(8,8), new Vector2(.5f, .5f), new Vector2(1.25f,0.63f) ));
        tilemap.addTileSet( "wall", new TileSet("maps/wall.png", new Vector2(8,8), new Vector2(.5f, .5f), new Vector2(1.35f,1.12f) ));
        tilemap.addTileSet( "cyan", new TileSet("maps/tilesethexaSelectCyan.png", new Vector2(8,8), new Vector2(.5f, .5f), new Vector2(1.25f,0.63f) ));
        //tilemap.addTileSet( "wallcyan", new TileSet("maps/tilesetwallSelectCyan.png", new Vector2(8,8), new Vector2(.5f, .5f), new Vector2(1.35f,1.12f) ));


    Color[] ramps = new Color[10];
    int b = (int) (Math.random()*360);
    for( int i = 0 ; i< 10 ; i++ ) {
    	ramps[i] = new Color().fromHsv( b, (float)Math.random()/2f+.2f, .7f );
    	ramps[i].a = 1;
    }
    tilemap.addTileSet( "rand", new TileSetMisc( ramps ) );
        
        int radius = 10;
        int radiusbig = radius*4;
        
    tilemap.addLayer( "backP", new Layer(new TriPixel( com.lipki.bulla.grid.TriOrientation.POINTY, new Vector2(20, 20 ) ), GC.center, new TriGrid()));
        
        tilemap.addLayer( "autoGround5", new Layer(new HexPixel(HexOrientation.FLAT, new Vector2(radiusbig * 2, radiusbig)), GC.center, new HexGrid()));
        tilemap.addLayer( "ground5", new Layer(new HexPixel( HexOrientation.FLAT, new Vector2(radiusbig*2, radiusbig ) ), GC.center, new HexGrid()));
        tilemap.addLayer( "select5", new Layer(new HexPixel( HexOrientation.FLAT, new Vector2(radiusbig*2, radiusbig ) ), GC.center, new HexGrid()));
        tilemap.addLayer( "grid0", new Layer(new HexPixel( HexOrientation.FLAT, new Vector2(radius*2, radius ) ), GC.center, new HexGrid()));

        
    TriNode.parallelograms( -40, 40, -40, 40, (q, r) -> {
    	tilemap.getLayer("backP").setTile( new TriNode(q,r), new Tile("rand", "randomColorDraw"));
    });

        
        tilemap.addAuto( "autoGround5", "grid0", (event, out) -> {

            Layer in = (Layer) event.target;
            SqrNode node = (SqrNode) event.node;

            Vector2 pixin = in.pixelStuff.toPixel( node ); // on convertie en pixel, pour avoir le centre en pixel
            SqrNode hexout = out.pixelStuff.pixelTo( pixin ); // on récupére la position du tile correspondant dans la grille fine

            SqrNode[] corner1 = hexout.neighbors( 4 );
            SqrNode[] corner2 = hexout.neighbors( 2 );
            for ( int s = 0; s < corner1.length; s++)
                if( out.getTile( corner1[s] ) != null )
                    out.toggleTile( corner2[s], new Tile( "misc", "border" ) );

            HexNode.hexagons( 2, (q, r) -> {
                SqrNode hex = HexNode.add(hexout, new HexNode(q,r));
                out.toggleTile( hex, new Tile( "misc", "border") );
            });

        });

        tilemap.addAuto( "autoGround5", "ground5", (event, out) -> {

            Layer in = (Layer) event.target;
            SqrNode node = (SqrNode) event.node;

            if( event.id == LayerEvent.REMOVE_TILE ) out.removeTile(node);

            in.forEach((iNode, tile) -> {
                StringBuilder comb = new StringBuilder();

                SqrNode[] neighbors = ((SqrNode)iNode).neighbors();
                for (int n = neighbors.length-1; n >= 0; n--)
                    comb.append(in.getTile(neighbors[n]) != null ? "1" : "0");

                String id =  String.valueOf( Integer.parseInt(comb.toString(), 2) );

                out.setTile( new HexNode( ((HexNode)iNode).q, ((HexNode)iNode).r ), new Tile( "hexa", id ) );
            });

        });
        
	}

	@Override
    public boolean mouseMoved(Vector2 pos) {

        Vector2 tilemapCoo = tilemap.screenToLocalCoordinates( pos );
        Layer select5 = tilemap.getLayer("select5");
        Layer ground5 = tilemap.getLayer("ground5");

        SqrNode hex = select5.pixelStuff.pixelTo( tilemapCoo );
        Tile tile = (Tile) ground5.getTileInner(hex);
        String id = tile != null ? tile.id : "0";
        GC.cooLabel.setText( hex.toString() );

        GC.selectedHex = hex;

        select5.clear();
        select5.setTile( hex, new Tile( "cyan", id ) );

        return false;
    }

	@Override
	public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
        makebulle();
	}
    
    public void makebulle() {
    	
        Layer autoGround5 = tilemap.getLayer("autoGround5");

        //outView.toggleTile( GameController.selectedHex, new Tile( "misc", "threeColor" ) );
        autoGround5.toggleTile( (HexNode) GC.selectedHex, new Tile() );
        
        
        
        Array<int[]> groundList = new Array<int[]>();
        autoGround5.forEach((node, tile) -> {
            final int[] no = { ((HexNode)node).q, ((HexNode)node).r };
            groundList.add( no );
        });
        Json json = new Json();
        String serializedGroundList = json.toJson(groundList);
        
        GC.gameState.putString("groundList", serializedGroundList);
    	GC.gameState.flush();

    }

	@Override
	public void scrolled(InputEvent event, float x, float y, int amount) {

        OrthographicCamera cam = ((OrthographicCamera) phaseStage.getCamera());

        cam.zoom += 0.1f*amount;
        cam.zoom = cam.zoom > 2 ? 2 : cam.zoom;
        cam.zoom = cam.zoom < 0.3 ? 0.3f : cam.zoom;
        cam.zoom = (float)Math.floor( cam.zoom*10 )/10;

    	GC.gameState.putFloat( "zoom", cam.zoom );
    	//GC.gameState.flush();
    	
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		tilemap.setX( width/2 );
		tilemap.setY( height/2 );
		tilemap.resize( new Vector2( width/2, height/2 ) );
	}

    @Override
    public void dispose() {
    	tilemap.dispose();
        super.dispose();
    }

}
