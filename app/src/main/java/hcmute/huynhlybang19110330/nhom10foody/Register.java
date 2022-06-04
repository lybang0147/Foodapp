package hcmute.huynhlybang19110330.nhom10foody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    Database DB;
    ImageView imgback;
    EditText username,password,retypepassword,phone;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.etext_register_usn);
        password = (EditText) findViewById(R.id.etext_register_pwd);
        retypepassword = (EditText) findViewById(R.id.etext_register_retypepwd);
        phone = (EditText) findViewById(R.id.etext_register_phone);
        btnRegister = (Button) findViewById(R.id.btn_register_register);
        imgback = (ImageView) findViewById(R.id.imgview_register_back);
        DB= new Database(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty() || retypepassword.getText().toString().trim().isEmpty() || phone.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(Register.this, "Please fill all the field!!!", Toast.LENGTH_SHORT).show();
                }
                else
                    if (password.getText().toString().trim().equals(retypepassword.getText().toString().trim()))
                    {
                        if (DB.checkusername(username.getText().toString().trim()))
                        {
                            Toast.makeText(Register.this, "Username is already exists!!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if (DB.insertUser(username.getText().toString().trim(),password.getText().toString().trim(),phone.getText().toString().trim()))
                            {
                                Toast.makeText(Register.this, "Register Successfully!!!!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Register.this,Login.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(Register.this, "Something Wrong!!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                    else
                    {
                        Toast.makeText(Register.this, "Password and Retypepassword must be the same!!", Toast.LENGTH_SHORT).show();
                    }
                    username.setText("");
                    password.setText("");
                    retypepassword.setText("");
                    phone.setText("");
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });
    }
}