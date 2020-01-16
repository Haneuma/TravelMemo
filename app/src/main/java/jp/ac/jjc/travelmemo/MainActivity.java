package jp.ac.jjc.travelmemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;

import android.text.Editable;
import android.text.TextWatcher;
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


        EditText hinmoku = findViewById(R.id.et_Title);  //可変に生成されるobjのidは？



        rootView = findViewById(R.id.root);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Button b = findViewById(R.id.button);
        AddButtonListener abl = new AddButtonListener();
        b.setOnClickListener(abl);

        //ここまで変更禁止



        Button hanei = findViewById(R.id.hanei);
        HaneiButtonListener hbl = new HaneiButtonListener();
        hanei.setOnClickListener(hbl);


    }

    private class AddButtonListener implements View.OnClickListener {       //追加ボタンを押した際のリスナ
        @Override
        public void onClick(View view) {
            View subView = inflater.inflate(R.layout.sub, null);
            rootView.addView(subView, rootView.getChildCount() - 1);
            //ここまで変更禁止
        }
    }


    private class HaneiButtonListener implements View.OnClickListener{
        EditText yosan = findViewById(R.id.et_yosan);   //予算欄を取得
        EditText pay = findViewById(R.id.et_Pay); //とりあえず普通に入れてるけど
        TextView zandaka = findViewById(R.id.tv_nokori_2);  //なんか動かない気がして　要検証
        @Override
        public void onClick(View view){
            /*計算処理*/
            Editable getyosanText = yosan.getText();
            int int_yosan = Integer.parseInt(getyosanText.toString());
            Editable getpayText = pay.getText();
            int int_pay = Integer.parseInt(getpayText.toString());
            int int_zandaka = int_yosan - int_pay;
             zandaka.setText(Integer.toString(int_zandaka));
            /*反映処理*/


        }
    }

/*

          */


}
