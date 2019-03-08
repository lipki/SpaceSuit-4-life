package com.lipki.bulla.scene2d.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lipki.bulla.GC;

public class MainMenuScreen extends com.lipki.bulla.scene2d.screen.stdScreen {

    public MainMenuScreen(){
    	super();
    }

	public void init() {
		
        table.add( GC.fullScreenButton() ).colspan(6).expandX().align(Align.right);
        table.row();

        Label title = new Label( GC.locals.format("title"), GC.skin );
        title.setFontScale( 5 );
        table.add( title ).colspan(6).expand();
        table.row();

        int offset = 10;

        table.add().expandX();
        table.add( GC.newButton() ).pad( offset );
        table.add( GC.loadButton() ).pad( offset );
        table.add( GC.moreButton() ).pad( offset );
        table.add( GC.exitButton() ).pad( offset );
        table.add().expandX();
        table.row();

        int alignL = Align.left | Align.bottom;
        int alignR = Align.right | Align.bottom;

        table.add( new Label( GC.locals.format( "credit" ), GC.skin) ).align( alignL ).colspan(3).expand();
        table.add( new Label( GC.locals.format( "version" ), GC.skin) ).align( alignR ).colspan(3).expand();
		
	}
    
    @Override
    public void show() {
    	super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor((float)51/255, (float)1/255, (float)54/255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

        super.render(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
