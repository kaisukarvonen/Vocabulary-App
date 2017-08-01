package com.scoctail.vocabularyapp.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scoctail.vocabularyapp.R;

/**
 * Created by Kaisu on 1.8.2017.
 */

public class ThemesFragment extends Fragment
{
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.themes_list, container,false);
        return view;
    }


}
