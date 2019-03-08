package com.lipki.bulla.scene2d.tilemap;

public class Tile {

    public String tileset;
    public String id;

    public Tile() {}

    public Tile(String tileset, String id) {

        this.tileset = tileset;
        this.id = id;

    }

    @Override
    public String toString() {
        return "Tile : "+tileset+","+id;
    }
}