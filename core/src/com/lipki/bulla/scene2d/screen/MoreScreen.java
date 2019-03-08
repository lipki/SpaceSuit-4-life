package com.lipki.bulla.scene2d.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Align;
import com.lipki.bulla.GC;

public class MoreScreen extends com.lipki.bulla.scene2d.screen.stdScreen {

    public MoreScreen(){
    	super();
    	
        table.add( GC.fullScreenButton() ).colspan(6).expandX().align(Align.right);
        table.row();

        //table.pad( Value.percentWidth(0.1f) );
        table.add( GC.backButton() ).expand();
        table.setDebug(true);
    }
    
    @Override
    public void show() {
    	super.show();
    }

    @Override
    public void render(float delta) {
    	Gdx.gl.glClearColor((float)201/255, (float)70/255, (float)61/255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

        super.render(delta);
    }
}