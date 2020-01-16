package jp.ac.jjc.travelmemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout rootView;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootView = findViewById(R.id.root);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Button b = findViewById(R.id.button);
        SampleListener sl = new SampleListener();
        b.setOnClickListener(sl);

    }

    private class SampleListener implements View.OnClickListener {
        private int index = 1; // 付与するIDの連番を追加
        @Override
        public void onClick(View view) {
            View subView = inflater.inflate(R.layout.sub, null);
            rootView.addView(subView, rootView.getChildCount() - 1);
        }
    }
}
