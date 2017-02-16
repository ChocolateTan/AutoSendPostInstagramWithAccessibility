package com.example.don.testaccessibility;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  private Intent intent;
  private Button btnStart;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
//    intent = new Intent(this, MyAccessibilityService.class);
//    startService(intent);
//    btnStart = (Button)findViewById(R.id.btn_start);
//    findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        if(btnStart.getText().toString().equals("start")){
//          btnStart.setText("stop");
//          MyApplication.sIsOn = true;
//        }
//        if(btnStart.getText().equals("stop")){
//          btnStart.setText("start");
//          MyApplication.sIsOn = true;
//          MyApplication.sIsRun = false;
//        }
//      }
//    });
  }

  @Override
  protected void onDestroy() {
//    stopService(intent);
    super.onDestroy();
  }
}
