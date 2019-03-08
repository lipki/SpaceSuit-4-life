package com.lipki.bulla.scene2d.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.lipki.bulla.GC;

public class DevScreen extends stdScreen {

    private Texture logo;
    private Image logoImage;
    private boolean anim = false;

    public DevScreen(){
    	super();

        logo = new Texture(Gdx.files.internal("devlogo.png"));
        logoImage = new Image(logo);
        table.add(logoImage).align(Align.center);
    }

    @Override
    public void show() {
        super.show();

        logoImage.addAction (
            Actions.sequence (
                Actions.fadeOut(0),
                Actions.fadeIn(2),
                Actions.run(()-> {anim = true;})
            )
        );

    }

    @Override
    public void render( float delta ) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

        if( GC.assets.update() && (anim || Gdx.input.justTouched() ) ) {
        	// all the assets are loaded
        	GC.uiInit();
        	anim = false;
        }

        super.render(delta);
    }

    @Override
    public void hide() {
        dispose();
    	super.hide();
    }

    @Override
    public void dispose() {
        logo.dispose();
        super.dispose();
    }
}
