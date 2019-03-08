package com.lipki.bulla.scene2d.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lipki.bulla.GC;

public class NewGameScreen extends stdScreen {

    TextField saveName;

	public NewGameScreen() {
		super();
        
        Label ask = new Label("Entrer votre nom complet", GC.skin);
        saveName = new TextField("Karen", GC.skin);
        saveName.setAlignment(Align.center);

        table.add( ask ).colspan(4);
        table.row();
        
        table.add( saveName ).colspan(4);
        table.row();

        int offset = 10;
        
        Actor new2Button = GC.new2Button();

        table.add().expandX();
        table.add( new2Button ).pad( offset );
        table.add( GC.backButton() ).pad( offset );
        table.add().expandX();
        table.row();
        
        
        new2Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	GC.gameState = Gdx.app.getPreferences("Bulla_gameState_"+saveName.getText());
            	GC.gameState.flush ();
            	GC.game.setScreen( new LoadingScreen() );
            }
        });
        
        
    }
    
    @Override
    public void show() {
    	super.show();
    }
    
    @Override
    public void hide() {
    	super.hide();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor((float)94/255, (float)23/255, (float)66/255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

        super.render(delta);
    }
}
