package com.squareup.lib.activity;

import android.os.Bundle;

import com.squareup.lib.frament.BaseFrament;
import com.squareup.lib.TabAdapter;

import java.util.ArrayList;

/**
 * Created by H on 2017/9/1.
 */

public class TabBaseActivity extends BaseActivity {
    public TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragments = new ArrayList<BaseFrament>();
        tabAdapter = new TabAdapter(getSupportFragmentManager(), fragments);
    }
}
