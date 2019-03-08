package com.lipki.bulla.scene2d.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class stdScreen implements Screen {

    protected Stage stage;
    protected Table table;

    public stdScreen(){
    	System.out.println( "Create : "+this.getClass().getSimpleName() );

        stage = new Stage(new ScreenViewport());
        table = new Table();
        stage.addActor(table);

    }

    public void show() {
    	System.out.println( "show : "+this.getClass().getSimpleName() );
        Gdx.input.setInputProcessor(stage);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height) {
        table.setSize(width,height);
        stage.getViewport().update(width, height, true);
    }

    public void hide() {
    	System.out.println( "hide : "+this.getClass().getSimpleName() );
    }

    public void dispose() {
    	System.out.println( "dispose : "+this.getClass().getSimpleName() );
        //stage.dispose();
    }

	public void pause() {}
	public void resume() {}
}
