package com.lipki.bulla.scene2d.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.lipki.bulla.grid.aNode;
import com.lipki.bulla.scene2d.tilemap.Layer;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Entity extends Image {

    private Texture playerTexture;
    private Layer tileLayerMove;
    private SequenceAction sequence = Actions.sequence();

    public Entity ( Layer tileLayerMove, Layer tilelayerCollision ) {
        super();

        this.tileLayerMove = tileLayerMove;
        playerTexture = new Texture(Gdx.files.internal("pik.png"));
        //playerTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // c'est null, bordure noir

        setDrawable(new TextureRegionDrawable(new TextureRegion(playerTexture)));
        setSize(16, 16);
        setPosition(0-getWidth()/2, 0-getHeight()/2);

    }

    public void move(InputEvent event, float x, float y) {

        /*Vector2 presentPix = pixelToScreen( new Vector2( getX()+getWidth()/2, getY()+getHeight()/2 ) );
        Hex presentHex = tileLayerMove.pixelToHex( presentPix );
        Hex futurHex = tileLayerMove.pixelToHex( pixelToScreen( new Vector2(x,y) ) );

        Array<Hex> path = pathfinding( presentHex, futurHex );

        sequence.reset();
        sequence = Actions.sequence();

        for( int h = 0 ; h < path.size ; h++ ) {
            Vector2 pix = tileLayerMove.hexToPixel(path.get(h));
            pix = screenTopixel( pix );
            sequence.addAction( Actions.moveTo ( pix.x-getWidth()/2, pix.y-getHeight()/2, 0.1f ) );
        }

        sequence.addAction( run(new Runnable() {
            public void run () {
                //    Application.SINGLE.setScreen( Application.SINGLE.topViewScreen );
                System.out.println("Action complete!");
            }
        }));

        addAction (sequence);*/
    }

    public void setScreen( Vector2 pix ) {
        pix = screenTopixel( pix );
        setX(pix.x-getWidth()/2);
        setY(pix.y-getHeight()/2);
    }

    public Vector2 pixelToScreen( Vector2 pix ) {
        pix.x -= Gdx.graphics.getWidth()/2;
        pix.y -= Gdx.graphics.getHeight()/2;
        return pix;
    }

    private Vector2 screenTopixel(Vector2 pix) {
        pix.x += Gdx.graphics.getWidth()/2;
        pix.y += Gdx.graphics.getHeight()/2;
        return pix;
    }

    public Array<SqrNode> pathfinding ( SqrNode start, SqrNode goal ) {

        tileLayerMove.clear();

        Array<SqrNode> path = new Array<>();
        //if (isWall(goal)) return path;

        Map<SqrNode, Integer> openList = new HashMap<>();
        HashMap<SqrNode, SqrNode> cameFrom = new HashMap<>();
        HashMap<SqrNode, Integer> costSoFar = new HashMap<>();

        openList.put( start, 0 );
        cameFrom.put( start, null );
        costSoFar.put( start, 0 );

        while ( openList.size() > 0 ) {

            SqrNode current = entriesSortedByValues(openList).first().getKey();
            openList.remove( entriesSortedByValues(openList).first().getKey() );

            if (current.equals(goal)) {
                goal = current;
                break;
            }

            SqrNode[] neighbors = current.neighbors();
            for (SqrNode next : neighbors) {
                int newCost = costSoFar.get(current) + 1;
                if (!isWall(next))
                    if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                        costSoFar.put(next, newCost);
                        openList.put(next, next.distance(goal));
                        cameFrom.put(next, current);
                    }
            }

        }

        SqrNode current;
        int min = 999999;
        current = goal;

        if( cameFrom.get(goal) == null )
            for (Map.Entry<SqrNode,SqrNode> pair: cameFrom.entrySet())
                if( pair.getKey().distance(goal) < min ) {
                    min = pair.getKey().distance(goal);
                    current = pair.getKey();
                    //tileLayerMove.setTile(new Hex(current.q, current.r, min));
                }

        while ( !current.equals(start) ) {
            path.add(current);
            current = cameFrom.get(current);
            //tileLayerMove.setTile(new Hex(current.q, current.r, 0));
        }
        path.add(start);
        path.reverse();

        return path;

    }

    private boolean isWall( SqrNode next ) {
        /*Vector2 pix = tileLayerMove.hexToPixel( next );
        Hex hex = tileLayerCollision.pixelToHex( pix );*/
        return false;//tileLayerCollision.getTile(hex) == null;
    }

    public void dispose() {
        playerTexture.dispose();
    }

    private static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K, V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<>(
                (e1, e2) -> {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? res : 1;
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
}
