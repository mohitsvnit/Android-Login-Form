package ando.com.loginasssignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText etUserName, etPass;
    private TextView tvSignUp;
    private Button btnLogin;
    private UserTable userTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        onClicks();
       // userTable.UpgradeTableUser();
        userTable.getAlluser();
      /*  User user = new User();
        user.setFname("Mohit");
        user.setLname("Sharma");
        user.setEmail("mks@gmail.com");
        user.setPass("mohitksh");
        user.setbDate("15/04/1995");
        user.setHobbies("coding,games,badminton");
        long id = userTable.insertUser(user);
        Helper.log("Database Insert ID: " + String.valueOf(id));*/
    }

    private void init(){
        etUserName = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvSignUp = (TextView) findViewById(R.id.tvSignUP);
        userTable = new UserTable(MainActivity.this);
    }

    private void onClicks(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {

                    if (userTable.isUserExist(getUsername())) {
                        User user = userTable.loginWithEmailPassword(getUsername(), getPass());
                        if (user != null) {
                            Helper.currentUser = user;
                            Intent intent = new Intent(MainActivity.this, LoginSuccessful.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                        intent.putExtra("username", getUsername());
                        startActivity(intent);
                    }
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validate() {
        if(getUsername().isEmpty()){
            etUserName.setError("Username can't be empty");
            return false;
        }

        if(getPass().isEmpty()){
            etPass.setError("Password Can not be empty");
            return false;
        }

        return true;
    }

    private String getUsername(){
        return etUserName.getText().toString();
    }

    private String getPass(){
        return etPass.getText().toString();
    }


}
