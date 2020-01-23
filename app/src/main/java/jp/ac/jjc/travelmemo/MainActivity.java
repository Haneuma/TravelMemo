package jp.ac.jjc.travelmemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private LinearLayout rootView;
    private LayoutInflater inflater;
    private int index1 = 1;     //内容用
    private int index2 = 1001;  //支出用
    private int index3 = 2001;  //残高用
    private int yosan_int = 0;
    private int zandaka = 0;
    Notification notification = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        EditText hinmoku = findViewById(R.id.et_Title);  //可変に生成されるobjのidは？


        rootView = findViewById(R.id.root);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //追加ボタン
        Button b = findViewById(R.id.button);
        AddButtonListener abl = new AddButtonListener();
        b.setOnClickListener(abl);

        //ここまで変更禁止
        EditText yosan = findViewById(R.id.et_yosan);       //予算欄取得
        //予算入力数字のみ
        yosan.setInputType(InputType.TYPE_CLASS_NUMBER);

        //予算セットボタン
        Button yosan_set = findViewById(R.id.button_set);
        YosanSetButtonListener ysbl = new YosanSetButtonListener();
        yosan_set.setOnClickListener(ysbl);

        //計算ボタン
        Button calc = findViewById(R.id.button_calc);
        CalcButtonListener cbl = new CalcButtonListener();
        calc.setOnClickListener(cbl);


        /*通知関連*/
        String id = "notification_channel";
        String name = getString(R.string.notification);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(id,name,importance);
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

    }

    private class AddButtonListener implements View.OnClickListener {       //追加ボタンを押した際のリスナ


        @Override
        public void onClick(View view) {
            View subView = inflater.inflate(R.layout.sub, null);
            rootView.addView(subView, rootView.getChildCount() - 2);
            //ここまで変更禁止


            //↓以下、各View追加、それに対するID追加処理
            EditText et_naiyo = (EditText) ((LinearLayout) subView).getChildAt(0);      //内容用
            et_naiyo.setId(index1);

            EditText et_shishutsu = (EditText) ((LinearLayout) subView).getChildAt(1);     //支出用
            et_shishutsu.setId(index2);

            TextView tv_zandaka = (TextView) ((LinearLayout) subView).getChildAt(2);      //残高
            tv_zandaka.setId(index3);

            //支出欄入力制限
            et_shishutsu = findViewById(index2);
            et_shishutsu.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED); //入力制限

            //ログ用
/*           if (index1 > 1) {
                EditText et_naiyo2 = findViewById(index1 - 1);
                Log.i("内容ログ", et_naiyo2.getText().toString());
            }
            if (index2 > 1001) {
                EditText et_shishutsu2 = findViewById(index2 - 1);
                Log.i("支出ログ", et_shishutsu2.getText().toString());
            }
            if (index3 > 2001) {
                TextView tv_zandaka2 = findViewById(index3 - 1);
                Log.i("残高ログ", tv_zandaka2.getText().toString());
            }*/



        }
    }

    private class CalcButtonListener implements View.OnClickListener {//計算ボタンを押した際のリスナ
        @Override
        public void onClick(View view) {
            //改めて、予算欄・支出欄・残高欄を取得。

            EditText et_shishutsu = findViewById(index2);
            TextView tv_zandaka = findViewById(index3);

            //入力制限・計算処理

           int shishutsu_int = Integer.parseInt(et_shishutsu.getText().toString());    //支出の取得
           zandaka = zandaka - shishutsu_int;
           tv_zandaka.setText(String.valueOf(zandaka));

            //計算終了後、indexを増やす
            index1++;
            index2++;
            index3++;
            double zandakarate = (double)zandaka/(double)yosan_int;
            System.out.println("debug残高"+zandaka);
            System.out.println("debug予算"+yosan_int);
            System.out.println("debug残高比"+zandakarate);
            if(zandakarate < 0.3){    //残高が少なくなったら通知を飛ばす
                //Notificationを作成するBuilderクラス生成
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,"notification_channel");
                //通知エリアに表示されるアイコン設定
                builder.setSmallIcon(android.R.drawable.ic_dialog_alert);    //ここでアイコンを変更できる
                //通知ドロワーでのタイトル設定
                builder.setContentTitle(getString(R.string.msg_notification_title));
                //通知ドロワーでの表示メッセージ設定
                builder.setContentText(getString(R.string.msg_notification_text));
                //BuilderからNotiオブジェクト生成
                Notification notification = builder.build();
                //NMオブジェクト取得
                NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                //通知
                manager.notify(0,notification);
            }
        }

    }

    private class YosanSetButtonListener implements View.OnClickListener{   //予算セットボタン押下時リスナ
        @Override
        public void onClick(View view){
            //予算欄を取得
            EditText yosan = findViewById(R.id.et_yosan);
            yosan_int = Integer.parseInt(yosan.getText().toString());

            //残高の初期値を予算とする。
            zandaka = yosan_int;
        }
    }
}
