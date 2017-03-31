package ando.com.loginasssignment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFName, etLName, etEmail, etPass, etConfPass;
    private TextView tvBDate;
    private CheckBox cbReading, cbMovies, cbArts, cbMale, cbFemale;
    private Button btnRegister;
    private UserTable userTable;
    private Dialog dateDialog;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String email = getIntent().getStringExtra("username");

        init();
        onclicks();
        if(email != null) {
            if (!email.isEmpty()) {
                etEmail.setText(email);
            }
        }

    }

    private void init(){
        etFName = (EditText) findViewById(R.id.etFName);
        etLName = (EditText) findViewById(R.id.etLName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);
        etConfPass = (EditText) findViewById(R.id.etConfPass);
        tvBDate = (TextView) findViewById(R.id.tvBDate);
        cbReading = (CheckBox) findViewById(R.id.cbReading);
        cbMovies = (CheckBox) findViewById(R.id.cbMovies);
        cbArts = (CheckBox) findViewById(R.id.cbArts);
        cbMale = (CheckBox) findViewById(R.id.cbMale);
        cbFemale = (CheckBox) findViewById(R.id.cbFemale);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        userTable = new UserTable(RegisterActivity.this);
        dateDialog = new Dialog(RegisterActivity.this);
        View view = LayoutInflater.from(RegisterActivity.this).inflate(R.layout.date_dialog,null);
        datePicker = (DatePicker) view.findViewById(R.id.dpDatePicker);
        dateDialog.setContentView(view);
        dateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                tvBDate.setText(String.valueOf(datePicker.getDayOfMonth()) + "/"
                        + String.valueOf(datePicker.getMonth()) + " /"
                        + String.valueOf(datePicker.getYear()));
            }
        });


    }

    private void onclicks(){
        tvBDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.dismiss();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    if(!userTable.isUserExist(getString(etEmail))){
                        User user = new User();
                        user.setFname(getString(etFName));
                        user.setLname(getString(etLName));
                        user.setEmail(getString(etEmail));
                        user.setPass(getString(etPass));
                        user.setbDate(tvBDate.getText().toString());
                        user.setHobbies(getHobbies());
                        user.setGender(getGender());
                        long id = userTable.insertUser(user);
                        if(id > 0){
                            Helper.currentUser = user;
                            Toast.makeText(RegisterActivity.this, "User Register Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this,LoginSuccessful.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this,"User Register Failed",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Email Already Exist! :-P", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        cbMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbMale.isChecked()){
                    cbFemale.setChecked(false);
                }
            }
        });

        cbFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbFemale.isChecked()){
                    cbMale.setChecked(false);
                }
            }
        });
    }

    private String getHobbies() {
        String hobbies = "";
        if(cbReading.isChecked())
            hobbies = hobbies + "Reading";
        if(cbMovies.isChecked())
            hobbies = hobbies + ", Movies";
        if(cbArts.isChecked())
            hobbies = hobbies + ", Arts";

        return hobbies;
    }

    private String getGender(){
        if(cbMale.isChecked())
            return "Male";
        else{
            return "Female";
        }
    }

    private String getString(EditText editText){
        return editText.getText().toString();
    }

    private boolean validate(){
        if(getString(etFName).isEmpty()){
            etFName.setError("Fill This");
            return false;
        }

        if(getString(etLName).isEmpty()){
            etLName.setError("Fill This");
            return false;
        }

        if(getString(etConfPass).isEmpty()){
            etConfPass.setError("Fill This");
            return false;
        }

        if(getString(etPass).isEmpty()){
            etPass.setError("Fill This");
            return false;
        }else{
            if(!getString(etPass).equals(getString(etConfPass))){
                etConfPass.setText("");
                Toast.makeText(this, "Password Didn't Match", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if(!cbReading.isChecked() && !cbArts.isChecked() && !cbMovies.isChecked()){
            Toast.makeText(this, "One Hobby is Compulsory", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!cbMale.isChecked() && !cbFemale.isChecked()){
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
