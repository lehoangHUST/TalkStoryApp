package com.example.talkstory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class ListStory extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    private EditText text_search;
    private Button btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_story);

        // get the list of stories titles and contents in string array
        String[] titles = getResources().getStringArray(R.array.stories_title);
        String[] contents = getResources().getStringArray(R.array.story_content);

        // Lấy text và button trong thanh text và nút bấm tìm kiếm
        text_search = findViewById(R.id.text_search);
        btn_search = findViewById(R.id.btn_search);

        recyclerView = findViewById(R.id.storiesListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, titles, contents);
        recyclerView.setAdapter(adapter);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt = text_search.getText().toString().toLowerCase();
                ArrayList<String> titles_search = new ArrayList<>();
                ArrayList<String> contents_search = new ArrayList<>();

                if (txt.isEmpty())
                {
                    // Không làm gì cả vẫn để nguyên
                    adapter = new Adapter(ListStory.this, titles, contents);
                    recyclerView.setAdapter(adapter);
                }
                else{
                    for (int i = 0; i < titles.length; i++){
                        titles[i] = titles[i].toLowerCase();
                        if (titles[i].contains(txt)){
                            titles_search.add(titles[i]);
                            contents_search.add(contents[i]);
                        }
                    }

                    // Hiển thị lại danh sách view sau khi bấm nút tìm kiếm thành công
                    // Và trả ra các truyện liên quan đến danh sách đã được tìm kiếm
                    String[] cvt_titles_search = titles_search.toArray(new String[0]);
                    String[] cvt_contents_search = contents_search.toArray(new String[0]);

                    adapter = new Adapter(ListStory.this, titles_search, contents_search);
                    recyclerView.setAdapter(adapter);
                }

            }
        });
    }
}