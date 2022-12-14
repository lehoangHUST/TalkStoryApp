package com.example.talkstory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /* Define the UI elements */
    private EditText eAccount;
    private EditText ePassword;
    private Button eLogin;
    private Button eRegister;

    private ArrayList<User> users = new ArrayList<>();

    String Account = "";
    String Password = "";

    boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eAccount = findViewById(R.id.editAccount);
        ePassword = findViewById(R.id.editPassword);
        eLogin = findViewById(R.id.btnLogin);
        eRegister = findViewById(R.id.btnRegister);

        // Sự kiện đọc dữ liệu từ firebase
        readData();

        // Sự kiện nút bấm đăng nhập
        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Người dùng nhập tài khoản và mật khẩu của người đó */
                Account = eAccount.getText().toString();
                Password = ePassword.getText().toString();

                if (Account.isEmpty() || Password.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please enter name and password!", Toast.LENGTH_LONG).show();
                }else
                {
                    // Kiểm tra xem các trường nhập vào xem có đúng hay không ??
                    isValid = validate(users, Account, Password);

                    /* if not valid */
                    if (!isValid)
                    {
                        Toast.makeText(MainActivity.this, "Incorrect credentials, please try again!", Toast.LENGTH_LONG).show();
                    }
                    /* if valid */
                    else{
                        /* Allow the user in to your app by going into the next activity */
                        startActivity(new Intent(MainActivity.this, ListStory.class));
                    }
                }
            }
        });

        // Sự kiện nút bấm đăng kí cho người dùng
        eRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đi đến activity đăng kí người dùng
                startActivity(new Intent(MainActivity.this, Register_User.class));
            }
        });
    }


    private boolean validate(ArrayList<User> users,String account, String pass)
    {
        // So sánh với kết quả đã có trong firebase hay chưa
        for (User user: users)
        {
            if (user.account.equals(account) && user.password.equals(pass))
                return true;
        }
        return false;
    }

    // Sử dụng firebase để tiến hành đăng nhập
    private void readData()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot snap: snapshot.getChildren()) {
                    User user = snap.getValue(User.class);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}