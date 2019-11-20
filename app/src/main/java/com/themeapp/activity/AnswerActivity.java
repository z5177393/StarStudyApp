package com.themeapp.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.themeapp.R;
import com.themeapp.dbdao.JavaDatabase;
import com.themeapp.dbdata.ModuleSelectData;
import com.themeapp.dbdata.TaskDb;
import com.themeapp.utils.VolleyUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AnswerActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup rg_select;
    private RadioButton rb_select_one;
    private RadioButton rb_select_two;
    private RadioButton rb_select_three;
    private ImageView iv_answer_question;
    private ImageView iv_answer_result;
    private String title;
    private TaskDb taskDb;
    private ActionBar myActionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);


        iv_answer_question = this.findViewById(R.id.iv_answer_question);
        rg_select = this.findViewById(R.id.rg_select);
        rb_select_one = this.findViewById(R.id.rb_select_one);
        rb_select_two = this.findViewById(R.id.rb_select_two);
        rb_select_three = this.findViewById(R.id.rb_select_three);
        Button btn_answer = this.findViewById(R.id.btn_answer);
        iv_answer_result = this.findViewById(R.id.iv_answer_result);
        btn_answer.setOnClickListener(this);
        initView();
    }

    private void initView() {
        taskDb = (TaskDb) getIntent().getSerializableExtra("postiondata2");
        VolleyUtils.getInstance().setImageUrl(taskDb.url, iv_answer_question);
        title = taskDb.title;
        ArrayList<String> strings = new ArrayList<>();
        strings.add(title);
        Random random = new Random();
        int index = random.nextInt(8) + 1;
        strings.add(ModuleSelectData.getModeData().get(index));
        strings.add(ModuleSelectData.getModeData().get(index - 1));
        Collections.shuffle(strings);

        rb_select_one.setText(strings.get(0));
        rb_select_two.setText(strings.get(1));
        rb_select_three.setText(strings.get(2));

        myActionBar = getSupportActionBar();
        if (myActionBar != null) {
            myActionBar.setHomeButtonEnabled(true);
            myActionBar.setDisplayHomeAsUpEnabled(true);
            myActionBar.setTitle("Detail");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_answer:
                RadioButton rb = (RadioButton) this.findViewById(rg_select.getCheckedRadioButtonId());
                if (null != rb) {
                    CharSequence text = rb.getText();
                    if (text.toString().equals(title)){
                        iv_answer_result.setVisibility(View.VISIBLE);

                        if (  taskDb.tid  == 11){
                            Toast.makeText(this, "Success! you have finished the study of this topic", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(this, "Congratulations! you can now start attempting the next topic", Toast.LENGTH_SHORT).show();
                            nexTaskUpdate();
                        }
                    }else {
                        iv_answer_result.setVisibility(View.GONE);
                        Toast.makeText(this, "Incorrect answer!!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "warning! please select a choicet", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void nexTaskUpdate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                TaskDb nextTask = JavaDatabase.instance().raskDbDao().getvaluebytid(taskDb.tid + 1);
                nextTask.isstudy = "1";
                int update = JavaDatabase.instance().raskDbDao().update(nextTask);

            }
        }).start();

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
