package com.lei.loginschool;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 启动图的Activity
 * @author pibigstar
 *
 */
public class StartViewActivity extends Activity{

	private int[] imgs = {R.drawable.start_1,R.drawable.start_2,R.drawable.start_3};
	private ImageView img;
	private TextView txt;
	private int count = 3;
	private Animation animation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//取消标题  
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		//取消状态栏  注意：要放到setContentView前面
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); 

		setContentView(R.layout.startview);
		Random random = new Random();
		int i = random.nextInt(2);
		img = (ImageView) findViewById(R.id.img_start);
		img.setBackgroundResource(imgs[i]);
		txt = (TextView) findViewById(R.id.txt_login_time);
		
		animation = AnimationUtils.loadAnimation(this, R.anim.animation_text);
	    //textView.startAnimation(animation);
	    handler.sendEmptyMessageDelayed(0, 1000);

	}

    private int getCount() {
        count--;
        if (count == 0) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return count;
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                txt.setText(getCount()+"");
                handler.sendEmptyMessageDelayed(0, 1000);
                animation.reset();
                txt.startAnimation(animation);
            }

        };
    };
}
