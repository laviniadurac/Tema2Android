package com.example.tema2.models;

import androidx.annotation.Nullable;

public class Post extends Artist{
    private int userId;
    private int id;
    private String title;
    private String body;

    public Post(int userId, int id, String title, String body) {
        super(ArtistType.POST);
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Post))
            return false;
        return getId() == ((Post) obj).getId();
    }
}
