package com.example.talkstory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Register_User extends AppCompatActivity {

    // Khai báo một số biến của người dùng
    private EditText register_account;
    private EditText register_password;
    private EditText register_email;
    private EditText register_name;
    private EditText register_phone;
    private EditText register_gender;
    private Button btn_register;

    // Khai báo một số biến liên quan đến
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;

    // Lưu trữ dữ liệu User
    private ArrayList<User> users = new ArrayList<User>();

    String eRegister_account = "";
    String eRegister_password = "";
    String eRegister_email = "";
    String eRegister_name = "";
    String eRegister_phone = "";
    String eRegister_gender = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // Tìm kiếm id của các trường cần nhập
        register_account = findViewById(R.id.edit_register_account);
        register_password = findViewById(R.id.edit_register_password);
        register_email = findViewById(R.id.edit_register_email);
        register_name = findViewById(R.id.edit_register_name);
        register_phone = findViewById(R.id.edit_register_phone);
        register_gender = findViewById(R.id.edit_register_gender);
        btn_register = findViewById(R.id.Register_valid);

        // Firebase cho người dùng đăng nhập
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy text từ người dùng nhập vào
                eRegister_account = register_account.getText().toString();
                eRegister_password = register_password.getText().toString();
                eRegister_email = register_email.getText().toString();
                eRegister_name = register_name.getText().toString();
                eRegister_phone = register_phone.getText().toString();
                eRegister_gender = register_gender.getText().toString();


                // Kiểm tra một số điều kiện như sau

                if (validate(eRegister_account, eRegister_password, eRegister_email, eRegister_name, eRegister_phone, eRegister_gender)) {
                    Toast.makeText(Register_User.this, "Yêu cầu nhập đủ các trường thông tin ở phía trên", Toast.LENGTH_LONG).show();
                } else {
                    // Kiểm tra xem tài khoản đó liệu đã có trong database chưa
                    if (TextUtils.isEmpty(userId)) {
                        userId = mFirebaseDatabase.push().getKey();
                    }

                    User user = new User(eRegister_account, eRegister_password, eRegister_email, eRegister_phone, eRegister_name, eRegister_gender);
                    mFirebaseDatabase.child(userId).setValue(user);

                }
                // Quay trở lại màn hình đăng nhập sau khi đăng kí thành công
                startActivity(new Intent(Register_User.this, MainActivity.class));
            }
        });
    }

    // Kiểm tra các trường đăng kí được nhập
    private boolean validate(String account, String password, String email, String name, String phone, String gender)
    {
        /*
                    + Nếu người dùng để trống 1 trong các text thì cũng sẽ trả về lỗi => Không nhập đủ các trường
                    + Kiểm tra xem có trùng lặp giữa các tài khoản với nhau không ??
                    + Kiểm tra xem mật khẩu đó đã đủ mạnh hay chưa ??
                    + Kiểm tra xem email đó có chuỗi @gmail.com hay không
                    + Kiểm tra phone phải là một chuỗi các số (không có bất kì kí tự nào khác ngoài chữ số)
         */
        if (account.isEmpty() || password.isEmpty() || email.isEmpty()
                || name.isEmpty() || phone.isEmpty() || gender.isEmpty())
        {
            Toast.makeText(Register_User.this, "Bạn chưa nhập trường nào", Toast.LENGTH_LONG).show();
            return false;
        }

        // Kiểm tra xem mật khẩu bạn nhập đã mạnh hay chưa ??
        boolean check_pass_strong = false;
        if (password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@^/*])(?=.{8,})")){
            check_pass_strong = true;
        } else if (password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,})")){
            check_pass_strong = true;
        } else if (password.matches("^(?=.*[a-z])(?=.*[0-9])(?=.{8,})")){
            check_pass_strong = true;
        } else if (password.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.{8,})")){
            check_pass_strong = false;
        } // etc

        if (!check_pass_strong)
        {
            Toast.makeText(Register_User.this, "Mật khẩu nhập vẫn yếu", Toast.LENGTH_LONG).show();
            return false;
        }

        // Kiểm tra số điện thoại
        boolean check_phone = false;
        if (phone.matches("[0-9]+"))
        {
            check_phone = true;
        }else
        {
            check_phone = false;
        }

        if (!check_phone)
        {
            Toast.makeText(Register_User.this, "Nhập sai trường số điện thoại", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}