package com.lipki.bulla;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import com.lipki.bulla.GC;

public class Application extends Game {

    @Override
	public void create () {
        GC.init(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        
		getScreen().render( Gdx.graphics.getDeltaTime() );
	}

	@Override
	public void pause() {
		System.out.println( "Application pause" );
	}

    @Override
    public void resume() {
        System.out.println( "Application resume" );
    }
	
	@Override
    public void dispose () {
	    getScreen().dispose();
	}

}
