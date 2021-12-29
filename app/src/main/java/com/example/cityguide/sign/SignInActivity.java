package com.example.cityguide.sign;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cityguide.activity.MainActivity;
import com.example.cityguide.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends AppCompatActivity {
    private EditText emailTextView, passwordTextView;
    private Button Btn;
    private TextView register, forgetPassword;
   // private ProgressBar progressbar;
private GoogleSignInClient mGoogleSignInClient;
private ImageView googelogin;

    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mAuth = FirebaseAuth.getInstance();

        emailTextView = findViewById(R.id.inputEmail);
        passwordTextView = findViewById(R.id.inputPassword);
        Btn = findViewById(R.id.btnLogin);
        register = findViewById(R.id.gotoregister);
        forgetPassword = findViewById(R.id.forgotPassword);
        googelogin=findViewById(R.id.googleLogin);
       ///progressbar = findViewById(R.id.progressBar);

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent
                        = new Intent(SignInActivity.this,
                        signupActivity.class);
                startActivity(intent);

            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOpen();
            }
        });

        createRequest();

googelogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        signIn();
    }
});
    }

    private void createRequest() {


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == RC_SIGN_IN) {
            Task task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = (GoogleSignInAccount) task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);


                        } else {
                            Toast.makeText(SignInActivity.this, "Sorry auth failed.", Toast.LENGTH_SHORT).show();


                        }



                    }
                });
    }
    private void dialogOpen() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("EMAIL");

        final View customLayout
                = getLayoutInflater()
                .inflate(
                        R.layout.forget_dialog,
                        null);
        builder.setView(customLayout);

        builder
                .setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                EditText editText = customLayout.findViewById(R.id.Email);
                                String email = editText.getText().toString();
                                if(email.isEmpty()){
                                    Toast.makeText(getApplicationContext(),
                                            "Please ! Enter Email To Reset Password",
                                            Toast.LENGTH_LONG)
                                            .show();

                                }else{
                                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    Toast.makeText(getApplicationContext(),
                                                            "Mail sent successfully!!",
                                                            Toast.LENGTH_LONG)
                                                            .show();

                                                }
                                            } }); }
                            } });


                                            AlertDialog dialog = builder.create();
                                            dialog.show();
                                        }


                                private void loginUserAccount ()
                                {

                                     //progressbar.setVisibility(View.VISIBLE);

                                    String email, password;
                                    email = emailTextView.getText().toString();
                                    password = passwordTextView.getText().toString();

                                    if (TextUtils.isEmpty(email)) {
                                        Toast.makeText(getApplicationContext(),
                                                "Please enter email!!",
                                                Toast.LENGTH_LONG)
                                                .show();
                                        return;
                                    }

                                    if (TextUtils.isEmpty(password)) {
                                        Toast.makeText(getApplicationContext(),
                                                "Please enter password!!",
                                                Toast.LENGTH_LONG)
                                                .show();
                                        return;
                                    }

                                    mAuth.signInWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(
                                                    new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(
                                                                @NonNull Task<AuthResult> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(getApplicationContext(),
                                                                        "Login successful!!",
                                                                        Toast.LENGTH_LONG)
                                                                        .show();

                                                                //    progressbar.setVisibility(View.GONE);

                                                                Intent intent
                                                                        = new Intent(SignInActivity.this,
                                                                        MainActivity.class);
                                                                startActivity(intent);
                                                            } else {

                                                                Toast.makeText(getApplicationContext(),
                                                                        "Login failed!!",
                                                                        Toast.LENGTH_LONG)
                                                                        .show();

                                                               //progressbar.setVisibility(View.GONE);
                                                            }
                                                        }
                                                    });
                                }


                            }
