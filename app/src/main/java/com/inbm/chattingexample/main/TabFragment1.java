package com.inbm.chattingexample.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.inbm.chattingexample.R;
import com.inbm.chattingexample.inbm.AbsFragment;

@SuppressLint("ValidFragment")
public class TabFragment1 extends AbsFragment {


    public TabFragment1(Context listener) {
        super(listener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fragment_1, container, false);

        Button btn = (Button) v.findViewById(R.id.btn_1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.fromFragment("btn1");
            }
        });


        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
}