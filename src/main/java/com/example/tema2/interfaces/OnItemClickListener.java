package com.example.tema2.interfaces;

import com.example.tema2.models.Artist;
import com.example.tema2.models.User;

public interface OnItemClickListener {
    void onItemClick(Artist artist);
    void onImageClick(User user);
}
