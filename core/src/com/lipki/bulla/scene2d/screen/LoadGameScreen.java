package com.lipki.bulla.scene2d.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.lipki.bulla.GC;

public class LoadGameScreen extends stdScreen {

    List<String> list;

    public LoadGameScreen(){
    	super();

		list = new List<String>(GC.skin);
		list.getSelection().setRequired(true);
		list.getSelection().setToggle(true);
        ScrollPane scrollPane = new ScrollPane(list, GC.skin);
        table.add( scrollPane ).expand();

        table.row();

        table.pad(Value.percentWidth(0.1f));
		table.add( GC.backButton() ).expand();
        //table.setDebug(true);
        

        list.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	
            	List<String> list = (List<String>)event.getTarget();
            	String selected = (String)list.getSelected();
            	
            	GC.gameState = Gdx.app.getPreferences("Bulla_gameState_"+selected);

                GC.game.setScreen( new LoadingScreen() );
            	
            }
        });
        
    }
    
    @Override
    public void show() {
    	super.show();
    	
        if( Gdx.files.absolute("Bulla/saveStates/").isDirectory() ) {

			Array<String> listEntries = new Array<>();
			FileHandle[] files = Gdx.files.absolute("Bulla/saveStates/").list();
			for(FileHandle file: files)
				if( file.nameWithoutExtension().contains("Bulla_gameState_") )
					listEntries.add(file.nameWithoutExtension().split("Bulla_gameState_")[1]);
			
			list.setItems(listEntries);
		}
        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor((float)150/255, (float)46/255, (float)64/255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

        super.render(delta);
    }
}