package com.example.tema2.models;

public class Artist {

    public ArtistType getElement() {
        return type;
    }

    public void setType(ArtistType type) {
        this.type = type;
    }

    ArtistType type;

    public Artist(ArtistType type) {
        this.type = type;
    }
}
