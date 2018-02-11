package com.lei.loginschool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.json.JSONObject;

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
		
		try {
			URL url = new URL("http://10.168.6.10:801/eportal/?c=ACSetting&a=Login&protocol=http:&hostname=10.168.6.10&iTermType=1&wlanuserip="+ getIpv4() +"&wlanacip=10.168.6.9&mac=00-00-00-00-00-00&ip=" + getIpv4() + "&enAdvert=0&queryACIP=0&loginMethod=1?DDDDD=%2C0%2C" + username + "%40" + strCategory + "&upass=" + password + "&R1=0&R2=0&R3=0&R6=0&para=00&0MKKey=123456&buttonClicked=&redirect_url=&err_flag=&username=&password=&user=&cmd=&Login=");
			InputStream openStream = url.openStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int i = 0;
			while((i = openStream.read(b))!=-1) {
				bos.write(b,0,i);
			}
			
			String result = bos.toString();
			
			//JSON格式
			//JSONObject json = new JSONObject(result);
			//json.get("success");
			
			if (result.contains("登录成功")) {
				return true;
			}else {
				return false;
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		writeToConfig();
		return false;
		
	}

	private void writeToConfig() {
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
			tempCategory = "zzulis";
			break;
		case 1:
			tempCategory = "cmcc";
			break;
		case 2:
			tempCategory = "unicom";
			break;
		case 3:
			tempCategory = "other";
			break;
		default:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		tempCategory = "zzulis";
	}

	@Override
	public void onClick(View v) {
		login();
	}
	
	private String getIpv4() {
		String ipv4="";
		Enumeration<NetworkInterface> interfs;
		try {
			interfs = NetworkInterface.getNetworkInterfaces();
			 int i = 1;
		        while (interfs.hasMoreElements())  
		        {  
		            NetworkInterface interf = interfs.nextElement();  
		            Enumeration<InetAddress> addres = interf.getInetAddresses();
		            while (addres.hasMoreElements())  
		            {  
		                InetAddress in = addres.nextElement();  
		                if (in instanceof Inet4Address)  
		                {  
		                	if (i==5) {
		                		ipv4 = in.getHostAddress();  
							}
		                	
		                }  
		                i++;
		            }  
		        }
		} catch (SocketException e) {
			e.printStackTrace();
		}  
		System.out.println(ipv4);
		return ipv4;  
	}
}
