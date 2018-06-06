package com.vitamin.wecantalk.UIActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.vitamin.wecantalk.Adapter.SnsCommentListViewAdapter;
import com.vitamin.wecantalk.R;

public class CommentActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_comment_layout);

        ListView listView = findViewById(R.id.comment_listview);
        SnsCommentListViewAdapter adapter = new SnsCommentListViewAdapter();
        listView.setAdapter(adapter);
        adapter.addItem(getResources().getDrawable(R.drawable.pro), "김관희", "나는 가끔 눈물을 흘린다..", "Hi");
    }
}
