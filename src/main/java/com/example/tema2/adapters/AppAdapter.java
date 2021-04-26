package com.example.tema2.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tema2.R;
import com.example.tema2.controllers.Controller;
import com.example.tema2.interfaces.OnItemClickListener;
import com.example.tema2.models.Album;
import com.example.tema2.models.Artist;
import com.example.tema2.models.Photo;
import com.example.tema2.models.Post;
import com.example.tema2.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AppAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Artist> artistArrayList;
    OnItemClickListener onItemClickListener;

    public AppAdapter(ArrayList<Artist> albumArrayList) {
        this.artistArrayList = albumArrayList;
    }

    public AppAdapter(ArrayList<Artist> artists, OnItemClickListener onItemClickListener) {
        this.artistArrayList = artists;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        switch (artistArrayList.get(position).getElement()) {
            case ARTIST:
                return 0;
            case POST:
                return 1;
            case ALBUM:
                return 2;
            case PHOTO:
                return 3;
            default:
                return super.getItemViewType(position);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case 0:
                view = layoutInflater.inflate(R.layout.item_artist, parent, false);
                ArtistViewHolder artist = new ArtistViewHolder(view);
                return artist;

            case 1:
                view = layoutInflater.inflate(R.layout.item_artist, parent, false);
                PostViewHolder post = new PostViewHolder(view);
                return post;

            case 2:
                view = layoutInflater.inflate(R.layout.item_artist, parent, false);
                AlbumViewHolder album = new AlbumViewHolder(view);
                return album;

            case 3:
                view = layoutInflater.inflate(R.layout.item_artist, parent, false);
                PhotoViewHolder photo = new PhotoViewHolder(view);
                return photo;

        }
        return null;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ArtistViewHolder) {
            User artist = (User) artistArrayList.get(position);
            ((ArtistViewHolder) holder).bind(artist);
        } else if (holder instanceof PostViewHolder) {
            Post posts = (Post) artistArrayList.get(position);
            ((PostViewHolder) holder).bind(posts);
        } else if (holder instanceof AlbumViewHolder) {
            Album album = (Album) artistArrayList.get(position);
            ((AlbumViewHolder) holder).bind(album);
        } else if (holder instanceof PhotoViewHolder) {
            Photo photo = (Photo) artistArrayList.get(position);
            ((PhotoViewHolder) holder).bind(photo);
        }
    }


    @Override
    public int getItemCount() {
        return this.artistArrayList.size();
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView username;
        private final TextView email;
        private final ImageView image;
        private final View view;

        ArtistViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            username = view.findViewById(R.id.username);
            email = view.findViewById(R.id.email);
            image = view.findViewById(R.id.imageView);
            this.view = view;
        }


        void bind(User artist) {
            name.setText(artist.getName());
            username.setText(artist.getUsername());
            email.setText(artist.getEmail());
            image.setOnClickListener(
                    v -> {
                        if (onItemClickListener != null) {
                            onItemClickListener.onImageClick(artist);
                            notifyItemChanged(getAdapterPosition());
                        }
                    });

            view.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(artist);
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView body;

        PostViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.post_title);
            body = view.findViewById(R.id.body);
        }

        void bind(Post posts) {
            title.setText(posts.getTitle());
            body.setText(posts.getBody());
        }
    }


    class AlbumViewHolder extends RecyclerView.ViewHolder {
        private final TextView body;
        private final View view;

        public AlbumViewHolder(@NonNull View view) {
            super(view);
            body = view.findViewById(R.id.album_body);
            this.view = view;
        }

        public void bind(Album album) {
            body.setText(album.getBody());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(album);
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final View view;

        public PhotoViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.photo_body);
            this.view = view;
        }

        public void bind(Photo photos) {
            String imageViewUrl = photos.getUrl();
            Picasso picasso = Picasso.get();
            picasso.load(imageViewUrl).resize(Controller.IMAGE_WIDTH, Controller.IMAGE_HEIGHT).into(imageView);
        }
    }
}

