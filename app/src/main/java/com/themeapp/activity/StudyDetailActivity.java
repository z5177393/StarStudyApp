package com.themeapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.themeapp.R;
import com.themeapp.adapter.CommentRvAdapter;
import com.themeapp.dbdao.JavaDatabase;
import com.themeapp.dbdata.CommentDb;
import com.themeapp.dbdata.TaskDb;
import com.themeapp.utils.VolleyUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StudyDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_header;
    private TextView tv_title;
    private TextView tv_date;
    private TextView tv_explanation;
    private EditText et_input_comment;
    private Button btn_input;
    private TaskDb taskDb;
    private RecyclerView rv_comment;
    private ImageView iv_share;
    private ActionBar myActionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studylearn);
        iv_header = findViewById(R.id.iv_header);
        et_input_comment = findViewById(R.id.et_input_comment);
        btn_input = findViewById(R.id.btn_input);
        tv_title = findViewById(R.id.tv_title);
        tv_date = findViewById(R.id.tv_date);
        tv_explanation = findViewById(R.id.tv_explanation);
        rv_comment = findViewById(R.id.rv_comment);
        iv_share = findViewById(R.id.iv_share);
        initData();

        btn_input.setOnClickListener(this);
        iv_share.setOnClickListener(this);

        myActionBar = getSupportActionBar();
        if (myActionBar != null) {
            myActionBar.setHomeButtonEnabled(true);
            myActionBar.setDisplayHomeAsUpEnabled(true);
            myActionBar.setTitle("Detail");
        }
    }

    private void initData() {
        taskDb = (TaskDb) getIntent().getSerializableExtra("postiondata");
        VolleyUtils.getInstance().setImageUrl(taskDb.url, iv_header);
        tv_title.setText(taskDb.title);
        tv_date.setText(taskDb.date);
        tv_explanation.setText(taskDb.explanation);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_comment.setLayoutManager(linearLayoutManager);
        getAllComment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_input:
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                final String inputComment = et_input_comment.getText().toString().trim();
                if (TextUtils.isEmpty(inputComment)) {
                    Toast.makeText(this, "warning! input comment is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                et_input_comment.setText("");
                new Thread() {
                    @Override
                    public void run() {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                        CommentDb commentDb = new CommentDb(taskDb.tid, inputComment, df.format(new Date()));
                        JavaDatabase.instance().commentDbDao().insertAll(commentDb);
                        getAllComment();
                    }
                }.start();

                break;

            case R.id.iv_share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);

                shareIntent.putExtra(Intent.EXTRA_TEXT, taskDb.title);
                shareIntent.setType("text/plain");

                startActivity(Intent.createChooser(shareIntent, "share"));
                break;
        }
    }

    private void getAllComment() {

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        final List<CommentDb> getvaluebytid = JavaDatabase.instance().commentDbDao().getvaluebytid("" + taskDb.tid);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (getvaluebytid != null && getvaluebytid.size() > 0) {
                                    CommentRvAdapter commentRvAdapter = new CommentRvAdapter(StudyDetailActivity.this, getvaluebytid);
                                    rv_comment.setAdapter(commentRvAdapter);
                                }
                            }
                        });
                    }
                }

        ).start();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
