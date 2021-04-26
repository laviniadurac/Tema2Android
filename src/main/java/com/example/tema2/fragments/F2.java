package com.example.tema2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tema2.R;
import com.example.tema2.adapters.AppAdapter;
import com.example.tema2.controllers.Controller;
import com.example.tema2.interfaces.ActivityFragment;
import com.example.tema2.interfaces.OnItemClickListener;
import com.example.tema2.models.Album;
import com.example.tema2.models.Artist;
import com.example.tema2.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class F2 extends Fragment implements OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ActivityFragment activityFragmentCommunication;
    private User currentArtist;
    private View view;
    private ArrayList<Artist> elements = new ArrayList<>();
    private AppAdapter adapter = null;
    private SwipeRefreshLayout swipeRefreshLayout;

    public F2() {
        // Required empty public constructor
    }

    public F2(Artist currentArtist) {
        this.currentArtist = (User) currentArtist;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static F2 newInstance(String param1, String param2) {
        F2 fragment = new F2();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ActivityFragment) {
            activityFragmentCommunication = (ActivityFragment) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_2, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.album_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        swipeRefreshLayout = view.findViewById(R.id.swipe_fragment2);
        elements.clear();
        getAlbums();
        adapter = new AppAdapter(this.elements, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(this.adapter);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            elements.clear();
            adapter.notifyDataSetChanged();
            getAlbums();
            swipeRefreshLayout.setRefreshing(false);
        });
        return view;
    }


    void getAlbums() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Controller.BASE_URL + "/albums?" + Controller.USER_ID + "=" + currentArtist.getId();
        StringRequest getAlbumsRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    try {
                        handleAlbumResponse(response);
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                },
                error -> Toast.makeText(getContext(), "ALBUMS ERROR", Toast.LENGTH_LONG).show()
        );
        queue.add(getAlbumsRequest);
    }

    void handleAlbumResponse(String response) throws JSONException {
        JSONArray albumJSONArray = new JSONArray(response);
        for (int index = 0; index < albumJSONArray.length(); ++index) {
            JSONObject userPostJSON = (JSONObject) albumJSONArray.get(index);
            int id = userPostJSON.getInt("id");
            String title = userPostJSON.getString("title");
            Album album = new Album(id, title);
            if (!elements.contains(album))
                this.elements.add(album);

        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Artist artist) {
        activityFragmentCommunication.openF3(artist);
    }

    @Override
    public void onImageClick(User artist) {

    }
}
