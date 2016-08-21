package college.edu.tomer.firebaseusers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }

    public void login(final View view) {
        showProgress();
        FirebaseAuth.getInstance().
                signInWithEmailAndPassword(getEmail(), getPassword())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        gotoMain();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showError(e, view);
                    }
                });
    }

    public void register(final View view) {
        showProgress();
        FirebaseAuth.getInstance().
                createUserWithEmailAndPassword(getEmail(), getPassword()).
                addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        gotoMain();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showError(e, view);
                    }
                });
    }

    ProgressDialog dialog;
    private void showProgress() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setTitle("Logging you in");
            dialog.setMessage("Please wait");
        }
        dialog.show();
    }

    private void hideProgress() {
        if (dialog != null)
            dialog.dismiss();
    }


    private void showError(Exception e, View view) {
        hideProgress();
        Snackbar.make(view,
                e.getLocalizedMessage(),
                Snackbar.LENGTH_INDEFINITE
        ).setAction("dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }

    private void gotoMain() {
        hideProgress();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public String getEmail() {
        return etEmail.getText().toString();
    }

    public String getPassword() {
        return etPassword.getText().toString();
    }
}
