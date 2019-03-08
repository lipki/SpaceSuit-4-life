package com.lipki.bulla.scene2d.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lipki.bulla.GC;
import com.lipki.bulla.game.ConstructionPhase;
import com.lipki.bulla.game.ExplorationPhase;
import com.lipki.bulla.game.GamePhase;

public class GameScreen extends stdScreen {
    
    private Label label;

    public ExplorationPhase explorationPhase;
    public ConstructionPhase constructionPhase;

    public GameScreen() {
    	super();

        label = GC.cooLabel;

        table.add( label ).align((Align.left | Align.top)).expandX();
        //table.add( GC.inButton() ).align((Align.right | Align.top)).expandX();
        table.add( GC.backButton() ).align((Align.right | Align.top)).expandX();
        table.add( GC.fullScreenButton() ).align(Align.right | Align.top);
        table.row();
        table.add().expandY();
        
        explorationPhase = new ExplorationPhase();
        constructionPhase = new ConstructionPhase();
        
        GamePhase.show(explorationPhase);


        /*Entity player = new Entity( move, autoLayer );
        player.setScreen(new Vector2(0,0));
        topview.addActor(player);*/

        stage.addListener(new ClickListener(Input.Buttons.RIGHT) {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	GamePhase.actualPhase.touchDown(event, x, y, pointer, button);
                return false;
            }
        });

        stage.addListener(new ClickListener(Input.Buttons.LEFT) {
            //@Override
            /*public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.move(event, x, y);
                return true;
            }*/
            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
            	GamePhase.actualPhase.scrolled(event, x, y, amount);
                return true;
            }
        });
        
    }

    @Override
    public void resize(int width, int height) {
    	GC.center = new Vector2(width/2, height/2);
    	GamePhase.actualPhase.resize(width, height);
        super.resize(width, height);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

        GamePhase.actualPhase.mouseMoved ( new Vector2( Gdx.input.getX(), Gdx.input.getY() ) );

        GamePhase.actualPhase.phaseStage.act(delta);
        GamePhase.actualPhase.phaseStage.draw();

        super.render(delta);
    }

    @Override
    public void dispose() {
        explorationPhase.dispose();
        constructionPhase.dispose();
        super.dispose();
    }

}
