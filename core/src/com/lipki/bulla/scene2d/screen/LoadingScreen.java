package com.lipki.bulla.scene2d.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.lipki.bulla.GC;
import com.lipki.bulla.grid.HexNode;
import com.lipki.bulla.scene2d.tilemap.Tile;

public class LoadingScreen extends stdScreen {
	
    private Label label;
    float startDelta = 0;

	public LoadingScreen() {
		super();
		
		label = new Label("Loading ...", GC.skin);
        table.add( label ).expand();
        
        
        
        GC.gameScreen = new GameScreen();

        String serializedGroundList = GC.gameState.getString("groundList");
        String[] groundList = serializedGroundList.split(",");
        for( int i = 0 ; i < groundList.length ; i+=2 ) {
        	String[] sq = groundList[i].split("\\[");
        	int q = Integer.valueOf(sq[sq.length-1]);
        	int r = Integer.valueOf(groundList[i+1].split("]")[0]);
        	HexNode hex = new HexNode(q,r);
        	GC.gameScreen.constructionPhase.tilemap.getLayer("autoGround5").setTile(hex, new Tile());
        }
        
	}
	
    @Override
    public void show() {
    	super.show();
        
        stage.addAction (
            Actions.sequence (
                Actions.delay(0.1f),
                Actions.run(() -> { GC.game.setScreen( GC.gameScreen ); })
            )
        );
    	
    }

    @Override
    public void hide() {
        dispose();
    	super.hide();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor((float)94/255, (float)23/255, (float)66/255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

        super.render(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
    
}
