package com.example.tema2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tema2.R;
import com.example.tema2.adapters.AppAdapter;
import com.example.tema2.controllers.Controller;
import com.example.tema2.models.Album;
import com.example.tema2.models.Artist;
import com.example.tema2.models.Photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class F3 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Album currentAlbum;
    private View view;
    private ArrayList<Artist> elements = new ArrayList<>();
    private AppAdapter adapter = null;

    public F3() {
        // Required empty public constructor
    }

    public F3(Artist currentAlbum) {
        this.currentAlbum = (Album) currentAlbum;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static F3 newInstance(String param1, String param2) {
        F3 fragment = new F3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_3, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.photos_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2);
        elements.clear();
        getPhotos();
        adapter = new AppAdapter(this.elements);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(this.adapter);
        recyclerView.post(() -> adapter.notifyDataSetChanged());
        return view;
    }

    void getPhotos() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Controller.BASE_URL + "/photos?" + Controller.ALBUM_ID + "=" + currentAlbum.getId();
        StringRequest getAlbumsRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            handlePhotosResponse(response);
                        } catch (JSONException exception) {
                            exception.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "PHOTO ERROR", Toast.LENGTH_LONG).show();
                    }
                }
        );
        queue.add(getAlbumsRequest);
    }

    void handlePhotosResponse(String response) throws JSONException {
        JSONArray photosJSONArray = new JSONArray(response);
        for (int index = 0; index < photosJSONArray.length(); ++index) {
            JSONObject userPostJSON = (JSONObject) photosJSONArray.get(index);
            int id = userPostJSON.getInt("id");
            String title = userPostJSON.getString("title");
            String url = userPostJSON.getString("url");
            String thumbnailUrl = userPostJSON.getString("thumbnailUrl");
            Photo photos = new Photo(id, title, url, thumbnailUrl);
            this.elements.add(photos);

        }
        adapter.notifyDataSetChanged();
    }
}
