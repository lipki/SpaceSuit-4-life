package com.lipki.bulla.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.lipki.bulla.GC;

public class GamePhase {
	
	public static GamePhase actualPhase;
	
	public Stage phaseStage;
	public String phaseName;
	
	public GamePhase( String phaseName ) {
		
		this.phaseName = phaseName;
		phaseStage = new Stage( new ScreenViewport() );
		
	}

	public static void show(GamePhase constructionPhase) {
		actualPhase = constructionPhase;
    	GC.gameState.putString( "view", constructionPhase.phaseName );
    	GC.gameState.flush();
	}

    public boolean mouseMoved(Vector2 pos) {
        return false;
    }

	public void resize(int width, int height) {
    	phaseStage.getViewport().update(width, height, true);
	}

	public void dispose() {
		phaseStage.dispose();
	}

	public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
	}

	public void scrolled(InputEvent event, float x, float y, int amount) {
	}

}
