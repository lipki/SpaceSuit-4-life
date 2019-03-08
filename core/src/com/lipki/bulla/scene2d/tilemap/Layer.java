package com.lipki.bulla.scene2d.tilemap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.lipki.bulla.grid.aGrid;
import com.lipki.bulla.grid.aNode;
import com.lipki.bulla.grid.aPixel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Layer implements GameDataListener {

    private final DelayedRemovalArray<GameDataListener> listeners = new DelayedRemovalArray<>(0);
    Map<Layer, BiConsumer<LayerEvent, Layer>> auto = new HashMap<>();
    public String name;

    private aGrid tiles;
    public aPixel pixelStuff;
    Vector2 origin;

    public Layer(aPixel pixelStuff, Vector2 origin, aGrid tiles) {

        this.tiles = tiles;
        this.pixelStuff = pixelStuff;
        this.origin = origin;

    }

    public SqrNode getTile( SqrNode node ) {
        return tiles.get( node );
    }

    public Object getTileInner(SqrNode node) {
        return tiles.getInner( node );
    }

    public void setTile(SqrNode node) {
        tiles.put( node );
        notify(new LayerEvent(this, LayerEvent.SET_TILE, node ));
    }

    public void setTile(SqrNode node, Object tile ) {
        tiles.put( node, tile );
        notify(new LayerEvent(this, LayerEvent.SET_TILE_INNER, node ));
    }

    public void removeTile(SqrNode node) {
        if( tiles.get( node ) != null ) {
            tiles.remove( node );
            notify(new LayerEvent(this, LayerEvent.REMOVE_TILE, node ));
        }
    }

    public boolean toggleTile( SqrNode node) {
        if( tiles.get( node ) != null ) {
            removeTile(node);
            return false;
        }

        setTile( node );
        return true;
    }

    public void toggleTile(SqrNode node, Object tile ) {
        if( tiles.get( node ) != null ) {
            removeTile(node);
            return;
        }

        setTile( node, tile );
    }

    public void forEach(BiConsumer<SqrNode, Object> callback) {

        if (callback == null)
            throw new NullPointerException();
        else tiles.forEach((node,tile) -> {
            callback.accept(node,tile);
        });

    }

    public void clear() {
        tiles.clear();
    }





    @Override
    public void listener(LayerEvent event) {
        auto.get( event.target ).accept(event, this);
    }

    public boolean addListener (GameDataListener listener) {
        if (listener == null) throw new IllegalArgumentException("listener cannot be null.");
        if (!listeners.contains(listener, true)) {
            listeners.add(listener);
            return true;
        }
        return false;
    }

    public void notify (LayerEvent event) {
        listeners.begin();
        for (int i = 0, n = listeners.size; i < n; i++)
            listeners.get(i).listener(event);
        listeners.end();
    }
}
