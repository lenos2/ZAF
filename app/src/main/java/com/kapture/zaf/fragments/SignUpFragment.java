package com.kapture.zaf.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kapture.zaf.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    View v;

    Button btnSignUp;
    EditText etEmail, etUsername, etPassword, etPassword2;

    String username,email,password1,passsword2;
    ProgressDialog progressDialog;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view;

        etEmail = (EditText) v.findViewById(R.id.etEmail);
        etPassword = (EditText)v.findViewById(R.id.etPassword);
        etPassword2 = (EditText)v.findViewById(R.id.etReenterPassword);
        etUsername = (EditText)v.findViewById(R.id.etUsername);

        btnSignUp = (Button)v.findViewById(R.id.btnSigningUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etUsername.getText().toString().trim();
                password1 = etPassword.getText().toString();
                passsword2 = etPassword2.getText().toString();
                email = etEmail.getText().toString().trim();

                if (username.equals("") || password1.equals("") || email.equals("")){
                    Toast.makeText(v.getContext(),"Please Fill in all details",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password1.equals(passsword2)){
                    Toast.makeText(v.getContext(),"Passwords Do not match, PLease Reenter.",Toast.LENGTH_LONG).show();
                    etPassword.setText("");
                    etPassword2.setText("");
                    return;
                }

                progressDialog = ProgressDialog.show(v.getContext(),null,"Signing up...");
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password1).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(v.getContext(),"Failed to sign up, error occured. Error: "+ e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //Toast.makeText(v.getContext(),"Welcome " /*+ username*/,Toast.LENGTH_LONG).show();
                        listener.onSuccess();
                    }
                });
            }
        });

    }

    public interface OnLogInListener{
        void onSuccess();
    }
    private OnLogInListener listener;
    public void setOnLogInListener(OnLogInListener listener){
        this.listener = listener;
    }
}
