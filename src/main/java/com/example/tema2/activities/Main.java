package com.example.tema2.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tema2.R;
import com.example.tema2.fragments.F1;
import com.example.tema2.fragments.F2;
import com.example.tema2.fragments.F3;
import com.example.tema2.interfaces.ActivityFragment;
import com.example.tema2.models.Artist;

public class Main extends AppCompatActivity implements ActivityFragment {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat); //am schimbat asta si acum nu mai afiseaza ok :))
        super.onCreate(savedInstanceState);

    }

    @Override
    public void addF1() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        String tag = F1.class.getName();
        F1 fragment1 = F1.newInstance("", "");
        FragmentTransaction addTransaction = transaction.add(
                R.id.frame_layout, fragment1, tag
        );
        addTransaction.commit();
    }

    @Override
    public void openF2(Artist artist) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        String tag = F2.class.getName();
        F2 fragment2 = new F2(artist);
        FragmentTransaction replaceTransaction = transaction.replace(
                R.id.frame_layout, fragment2, tag
        );
        replaceTransaction.commit();
    }

    @Override
    public void openF3(Artist artistAlbum) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        String tag = F3.class.getName();
        F3 fragment3 = new F3(artistAlbum);
        FragmentTransaction replaceTransaction = transaction.replace(
                R.id.frame_layout, fragment3, tag
        );
        replaceTransaction.commit();
    }
}
