package com.kapture.zaf.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;

import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kapture.zaf.R;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {

    View v;
    ViewGroup transitionContainer;
    View layout1,layout2;
    Boolean visible;


    Button btnSignIn,btnSignInActual;
    TextView tvSignUp,tvSignUp2;

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view;

        transitionContainer = v.findViewById(R.id.layout_frame);
        layout1 = transitionContainer.findViewById(R.id.layout_start);
        layout2 = transitionContainer.findViewById(R.id.layout_signup);

        visible = true;

        btnSignIn = v.findViewById(R.id.btnSignIn);
        btnSignInActual = v.findViewById(R.id.btnSigninActual);
        tvSignUp = v.findViewById(R.id.tvSignUp);
        tvSignUp2 = v.findViewById(R.id.tvSignUp2);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visible = !visible;

                TransitionSet set = new TransitionSet()
                        .addTransition(new Scale(0.2f))
                        .addTransition(new Fade())
                        .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                                new FastOutLinearInInterpolator());

                TransitionManager.beginDelayedTransition(transitionContainer, set);
                layout2.setVisibility(visible ? View.VISIBLE : View.GONE);
                layout1.setVisibility(visible ? View.GONE : View.VISIBLE);
            }
        });

        btnSignInActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickSignInActual();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickSignUp();
            }
        });

        tvSignUp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickSignUp();
            }
        });
    }

    public interface ClickListener{
        void onClickSignInActual();
        void onClickSignUp();
    }

    private ClickListener listener;
    public void setOnClickListener(ClickListener listener){
        this.listener = listener;
    }
}
