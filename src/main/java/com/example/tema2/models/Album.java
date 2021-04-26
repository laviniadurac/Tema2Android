package com.example.tema2.models;

public class Album extends Artist {
    private int id;
    private String body;

    public Album(int id, String body) {
        super(ArtistType.ALBUM);
        this.id = id;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
