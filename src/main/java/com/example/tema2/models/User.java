package com.example.tema2.models;

import androidx.annotation.Nullable;

public class User extends Artist{
    private int id;
    private String name;
    private String username;
    private String email;
    private boolean expandPosts;

    public User(int id, String name, String username, String email) {
        super(ArtistType.ARTIST);
        this.id = id;
        this.email = email;
        this.name = name;
        this.username = username;
        expandPosts = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isExpandPosts() {
        return expandPosts;
    }

    public void setExpandPosts(boolean expandPost) {
        this.expandPosts = expandPost;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        assert obj != null;
        if (!(obj instanceof User))
            return false;
        return this.id == ((User) obj).id;
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
