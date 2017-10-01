package com.kapture.zaf.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;

import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    FirebaseAuth mAuth;


    Button btnSignIn,btnSignInActual;
    TextView tvSignUp,tvSignUp2;
    EditText etEmail,etPassord;

    ProgressDialog progressDialog;

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

        //Check if the user is aleady signed in
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            listener.onClickSignInActual();
        }

        v = view;

        transitionContainer = v.findViewById(R.id.layout_frame);
        layout1 = transitionContainer.findViewById(R.id.layout_start);
        layout2 = transitionContainer.findViewById(R.id.layout_signup);

        visible = true;

        btnSignIn = v.findViewById(R.id.btnSignIn);
        btnSignInActual = v.findViewById(R.id.btnSigninActual);
        tvSignUp = v.findViewById(R.id.tvSignUp);
        tvSignUp2 = v.findViewById(R.id.tvSignUp2);

        etEmail = v.findViewById(R.id.etEmail);
        etPassord = v.findViewById(R.id.etPassword);



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

                //First check if passwords are correct
                final String email,password;
                email = etEmail.getText().toString().trim();
                password = etPassord.getText().toString();

                if (email.equals("") || password.equals("")){
                    Toast.makeText(v.getContext(),"Please fill in all fields.",Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog = ProgressDialog.show(v.getContext(),null,"Signing in...");
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        Toast.makeText(v.getContext(),"Welcome "/*+user.getDisplayName()*/,Toast.LENGTH_SHORT).show();
                        listener.onClickSignInActual();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(v.getContext(),"Failed To log in. Error : " + e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                progressDialog.dismiss();
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
