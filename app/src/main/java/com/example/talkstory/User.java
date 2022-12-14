package com.example.talkstory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    public String account; // Tài khoản của user đó
    public String password; // Mật khẩu của user đó
    public String email; // Email của user đó
    public String date; // Ngày tạo của user đó
    public String name; // Họ và tên của user đó
    public String gender; // Giới tính của user đó
    public String phone; // Số điện thoại của user đó

    // Default constructor required for calls to
    public User() {

    }

    public User(String account,
                String password,
                String email,
                String phone,
                String name,
                String gender)
    {
        this.account = account;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.email = email;

        // Lấy thời gian tạo ra user đó
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        this.date = sdf.format(new Date());
    }
}
