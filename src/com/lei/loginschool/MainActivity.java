package com.lei.loginschool;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//取消标题  
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		//取消状态栏  注意：要放到setContentView前面
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		setContentView(R.layout.activity_main);
	}

}
