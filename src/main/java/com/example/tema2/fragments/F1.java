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
import com.example.tema2.R;
import com.example.tema2.adapters.AppAdapter;
import com.example.tema2.controllers.Controller;
import com.example.tema2.interfaces.ActivityFragment;
import com.example.tema2.interfaces.OnItemClickListener;
import com.example.tema2.models.Artist;
import com.example.tema2.models.Post;
import com.example.tema2.models.User;
import com.example.tema2.singletons.VolleyConfigSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Stack;

public class F1 extends Fragment implements OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ActivityFragment activityFragment;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    private ArrayList<Artist> elements = new ArrayList<>();
    private ArrayList<Post> posts = new ArrayList<>();
    private AppAdapter adapter = null;

    public F1() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static F1 newInstance(String param1, String param2) {
        F1 fragment = new F1();
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
        view = inflater.inflate(R.layout.fragment_1, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.artist_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout = view.findViewById(R.id.swipe_fragment1);

        getUserDetails();

        adapter = new AppAdapter(elements, this);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            elements.clear();
            adapter.notifyDataSetChanged();
            getUserDetails();
            swipeRefreshLayout.setRefreshing(false);
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ActivityFragment) {
            activityFragment = (ActivityFragment) context;
        }
    }


    void getUserDetails() {
        VolleyConfigSingleton volleyConfigSingleton = VolleyConfigSingleton.getInstance(view.getContext());
        RequestQueue queue = volleyConfigSingleton.getRequestQueue();
        String url = Controller.BASE_URL + "/users";
        StringRequest getAlbumsRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    try {
                        handleUsersResponse(response);
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                },
                error -> Toast.makeText(getContext(), "USERS ERROR", Toast.LENGTH_LONG).show()
        );
        queue.add(getAlbumsRequest);
    }


    void getPosts(User artist) {
        VolleyConfigSingleton volleyConfigSingleton = VolleyConfigSingleton.getInstance(this.getContext());
        RequestQueue queue = volleyConfigSingleton.getRequestQueue();
        String url = Controller.BASE_URL + "/posts?" + Controller.USER_ID + "=" + artist.getId();
        StringRequest getAlbumsRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    try {
                        handlePostsResponse(response);
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                },
                error -> Toast.makeText(getContext(), "POSTS ERROR", Toast.LENGTH_LONG).show()

        );
        queue.add(getAlbumsRequest);
    }

    void handlePostsResponse(String response) throws JSONException {
        JSONArray postsJSONArray = new JSONArray(response);
        for (int index = 0; index < postsJSONArray.length(); ++index) {
            JSONObject userPostJSON = (JSONObject) postsJSONArray.get(index);

            int id = userPostJSON.getInt("id");
            int userId = userPostJSON.getInt("userId");

            String title = userPostJSON.getString("title");
            String body = userPostJSON.getString("body");

            Post newPost = new Post(userId, id, title, body);

            if (!posts.contains(newPost))
                posts.add(newPost);
        }
        adapter.notifyDataSetChanged();
    }


    void handleUsersResponse(String response) throws JSONException {
        JSONArray postsJSONArray = new JSONArray(response);
        for (int index = 0; index < postsJSONArray.length(); ++index) {
            JSONObject userPostJSON = (JSONObject) postsJSONArray.get(index);
            int id = userPostJSON.getInt("id");
            String name = userPostJSON.getString("name");
            String username = userPostJSON.getString("username");
            String email = userPostJSON.getString("email");
            elements.add(new User(id, name, username, email));
        }
        adapter.notifyDataSetChanged();
    }

    private void deletePosts(User user) {
        Stack<Artist> postsToDelete = new Stack<>();

        for (Artist artist : elements) {
            if (artist instanceof Post && ((Post) artist).getUserId() == user.getId()) {
                postsToDelete.add(artist);
            }
        }
        while (!postsToDelete.empty()) {
            elements.remove(postsToDelete.pop());
        }
        adapter.notifyDataSetChanged();
    }

    private void reorderElements() {
        ArrayList<Artist> reorderedElements = new ArrayList<>();
        for (Artist artist : elements) {
            if (artist instanceof User) {
                reorderedElements.add(artist);
                if (((User) artist).isExpandPosts()) {
                    for (Post post : posts) {
                        if (((User) artist).getId() == post.getUserId()) {
                            reorderedElements.add(post);
                        }
                    }
                }
            }
        }
        elements.clear();
        elements.addAll(reorderedElements);
    }

    @Override
    public void onItemClick(Artist artist) {
        activityFragment.openF2(artist);
    }

    @Override
    public void onImageClick(User artist) {
        getPosts(artist);
        if (!artist.isExpandPosts()) {
            artist.setExpandPosts(true);
            reorderElements();
            adapter.notifyDataSetChanged();
        } else {
            artist.setExpandPosts(false);
            deletePosts(artist);
        }
    }
}
