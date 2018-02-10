package com.lei.loginschool;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,OnItemSelectedListener{
	
	private EditText edit_username,edit_password;
	private Spinner category;
	private CheckBox remeberMe,autoLogin;
	private Button login;
	private SharedPreferences spf;
	private Editor editor;
	private List<String> categories;
	private ArrayAdapter<String> adapter;
	private String strCategory,username,password,tempCategory;
	private boolean is_remeber,is_autoLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//取消标题  
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		//取消状态栏  注意：要放到setContentView前面
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		setContentView(R.layout.activity_main);
		
		init();
		getInfo();
	}


	private boolean login() {
		
		username = edit_username.getText().toString().trim();
		password = edit_password.getText().toString().trim();
		strCategory = category.getSelectedItem().toString();
		
		Toast.makeText(this, "username:"+username+"password:"+password+"category"+tempCategory, 3000).show();
		is_remeber = remeberMe.isChecked();
		is_autoLogin = autoLogin.isChecked();
		
		editor.putString("username",username);
		editor.putString("password", password);
		editor.putString("category", strCategory);
		editor.putBoolean("rememberMe", is_remeber);
		editor.putBoolean("autoLogin", is_autoLogin);
		editor.commit();//注意！！要提交
		
		Log.i("main", "username:"+username+"password:"+password+"category"+tempCategory);
		return false;
		
	}


	private void init() {
		edit_username = (EditText) findViewById(R.id.username);
		edit_password = (EditText) findViewById(R.id.password);
		category = (Spinner) findViewById(R.id.category);
		remeberMe = (CheckBox) findViewById(R.id.rememberMe);
		autoLogin = (CheckBox) findViewById(R.id.autoLogin);
		login = (Button) findViewById(R.id.login);
		
		spf = getSharedPreferences("myConfig",MODE_PRIVATE);
		editor = spf.edit();
		
		categories = new ArrayList<String>();
		categories.add("校园网");
		categories.add("中国移动");
		categories.add("中国联通");
		categories.add("校园单宽");
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,categories);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		category.setAdapter(adapter);
		category.setOnItemSelectedListener(this);
		login.setOnClickListener(this);
		
	}
	
	/**
	 * 读取配置项里面的值
	 */
	private void getInfo() {
		username = spf.getString("username", null);
		password = spf.getString("password", null);
		is_remeber = spf.getBoolean("rememberMe", false);
		is_autoLogin = spf.getBoolean("autoLogin", false);
		strCategory = spf.getString("category", null);
		
		Log.i("main", "user:"+username);
		
		if (username!=null&&!"".equals(username)) {
			edit_username.setText(username);
		}
		if (password!=null&&!"".equals(password)) {
			edit_password.setText(password);
		}
		if (strCategory!=null&&!"".equals(strCategory)) {
			setSpinnerItemSelectedByValue(category,strCategory);
		}
		remeberMe.setChecked(is_remeber);
		autoLogin.setChecked(is_autoLogin);
		
	}
	
	/**
	 * 根据值，设置Spinner默认选中的值
	 * @param spinner
	 * @param value
	 */
	private void setSpinnerItemSelectedByValue(Spinner spinner,String value){
	    SpinnerAdapter apsAdapter= spinner.getAdapter(); //得到SpinnerAdapter对象
	    int k= apsAdapter.getCount();
	    for(int i=0;i<k;i++){
	        if(value.equals(apsAdapter.getItem(i).toString())){
	            spinner.setSelection(i,true);// 默认选中项
	            break;
	        }
	    }
	} 


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		switch (position) {
		case 0:
			tempCategory = "1";
			break;
		case 1:
			tempCategory = "2";
			break;
		case 2:
			tempCategory = "3";
			break;
		case 3:
			tempCategory = "4";
			break;
		default:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}

	@Override
	public void onClick(View v) {
		login();
	}

}
