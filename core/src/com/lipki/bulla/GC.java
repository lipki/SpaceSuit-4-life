package com.lipki.bulla;

import java.util.Locale;
import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.lipki.bulla.grid.aNode;
import com.lipki.bulla.scene2d.screen.DevScreen;
import com.lipki.bulla.scene2d.screen.LoadGameScreen;
import com.lipki.bulla.scene2d.screen.MainMenuScreen;
import com.lipki.bulla.scene2d.screen.MoreScreen;
import com.lipki.bulla.scene2d.screen.NewGameScreen;
import com.lipki.bulla.scene2d.screen.GameScreen;
import com.lipki.bulla.scene2d.screen.stdScreen;

public class GC {

    public static I18NBundle locals;

    public static Vector2 center;
    public static Preferences gameState;
    
    public static AssetManager assets;

    public static Application game;
    public static Skin skin;
    
	public static GameScreen gameScreen;
	public static MainMenuScreen mainMenuScreen;
	private static NewGameScreen newGameScreen;
	private static LoadGameScreen loadGameScreen;
	private static MoreScreen moreScreen;

    public static SqrNode selectedHex;

	private static Preferences preferences;

	public static Label cooLabel;

	public static void init( Application gamee ) {
		
		game = gamee;
        
    	assets = new AssetManager();
    	assets.load("ui/uiskin.json", Skin.class);
    	//assets.load("graphics/testPack.atlas",TextureAtlas.class);
    	assets.load("ui/default.fnt", BitmapFont.class);
    	
    	preferences = Gdx.app.getPreferences("settings");
    	
    	if( preferences.getString("locale") == null ) preferences.putString( "locale", "fr" );
    	locals = I18NBundle.createBundle( Gdx.files.internal("i18n/local"), new Locale(preferences.getString("locale")) );
    	
        center = new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        
    	GC.preferences.flush ();
        
        game.setScreen( new DevScreen() );
	}
	
    public static void uiInit() {
    	
    	skin = assets.get("ui/uiskin.json", Skin.class);
    	
    	cooLabel = new Label("base", GC.skin);
    	
    	mainMenuScreen = new MainMenuScreen();
    	mainMenuScreen.init();

        game.setScreen( mainMenuScreen );
		
	}
    
    
    
    
    
    
    
    

	public static Actor fullScreenButton() {
		return stdButton( "full", Color.valueOf("FF5E35"), (event) -> {
            if( !Gdx.graphics.isFullscreen() )
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
           else Gdx.graphics.setWindowedMode(1280,720);
		});
	}

	public static Actor newButton() {
		if( newGameScreen == null ) newGameScreen = new NewGameScreen();
		return stdButton( "new" , Color.valueOf("5E1742"), newGameScreen );
	}

	public static Actor loadButton() {
		if( loadGameScreen == null ) loadGameScreen = new LoadGameScreen();
		return stdButton( "load", Color.valueOf("962E40"), loadGameScreen );
	}

	public static Actor moreButton() {
		if( moreScreen == null ) moreScreen = new MoreScreen();
		return stdButton( "more", Color.valueOf("C9463D"), moreScreen );
	}

	public static Actor exitButton() {
		return stdButton( "exit", Color.valueOf("FF5E35"), (event) -> {
			dispose();
			Gdx.app.exit();
		});
	}

	public static Actor new2Button() {
		return stdButton( "new" , Color.valueOf("5E1742") );
	}

	public static Actor backButton() {
		return stdButton( "back", Color.valueOf("330136"), mainMenuScreen );
	}

	//TODO stage
	/*public static Actor outButton() {  
		System.out.println("-out--"+profileViewScreen);
		return stdButton( "out", Color.valueOf("C9463D"), profileViewScreen );
	}

	public static Actor inButton() {
		System.out.println("-in--"+topViewScreen);
		return stdButton( "in", Color.valueOf("C9463D"), topViewScreen );
	}*/

    public static Actor stdButton( String label, Color color, stdScreen screen ) {

        return stdButton( label, color, (event) -> {
    		game.setScreen( screen );
    	});
        
    }

    public static Actor stdButton( String label, Color color, Consumer<InputEvent> callback ) {
    	
    	Actor button = stdButton( label, color );
        
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	callback.accept(event);
            }
        });

        return button;
        
    }

    public static Actor stdButton( String label, Color color ) {

        int offsetBT = 5;
        int offsetLR = 10;

        TextButton button = new TextButton( locals.format( label ), skin);
        button.setColor( color );
        button.pad(offsetBT, offsetLR, offsetBT, offsetLR);
        
        return button;
        
    }

    public static void dispose() {

        locals = null;
        center = null;
        gameState = null;
        preferences = null;
        selectedHex = null;
        
        game.dispose();
        skin.dispose();
    	gameScreen.dispose();
    	mainMenuScreen.dispose();
    	newGameScreen.dispose();
    	loadGameScreen.dispose();
    	moreScreen.dispose();
        assets.dispose();
    }

}
