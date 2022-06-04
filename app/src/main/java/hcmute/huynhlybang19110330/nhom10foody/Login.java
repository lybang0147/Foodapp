package hcmute.huynhlybang19110330.nhom10foody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hcmute.huynhlybang19110330.nhom10foody.model.User;

public class Login extends AppCompatActivity {
    EditText username,password;
    Button btnlogin,btnregister;
    Database DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText) findViewById(R.id.etext_login_usn);
        password=(EditText) findViewById(R.id.etext_login_pwd);
        btnlogin=(Button) findViewById(R.id.btn_login_login);
        btnregister=(Button) findViewById(R.id.btn_login_register);
        DB = new Database(this);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if (DB.checkusernamepassword(user,pass))
                {
                    Cursor cursor = DB.getuserbyusername(user);
                    while (cursor.moveToNext()) {
                        User person = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                        Global.setId(person.getId());
                        Global.setName(person.getName());
                        Global.setPhone(person.getPhone());
                    }

                    Intent intent = new Intent(Login.this,MainPage.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(Login.this, "Login failed!!", Toast.LENGTH_SHORT).show();
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
    }
}