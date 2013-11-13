package com.geeklub.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String MOBIE = "10086";
	private static final String TELECOM = "10001";
	private static final String UNICOM = "10010";
	private ListView lv ;
	private SmsManager smsManager;

	private TelephonyManager tm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv  = (ListView) this.findViewById(R.id.lv);
		tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
		smsManager = SmsManager.getDefault();

		List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();

		for(int i = 0; i < GloableRes.ICONS.length;i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("icon", GloableRes.ICONS[i]);
			map.put("description", GloableRes.DESCRIPTIONS[i]);
			data.add(map);
		}

		/*
		Map<String,Object> function1 = new HashMap<String,Object>();
		Map<String,Object> function2 = new HashMap<String,Object>();
		Map<String,Object> function3 = new HashMap<String,Object>();


		function1.put("description", "套餐流量查询");
		function1.put("icon",R.drawable.ic_jog_dial_decline);


		function2.put("description", "话费余额查询");
		function2.put("icon",R.drawable.ic_jog_dial_vibrate_on);


		function3.put("description", "增值业务查询");
		function3.put("icon",R.drawable.ic_jog_dial_decline);





		data.add(function1);
		data.add(function2);
		data.add(function3);

		 */

		lv.setAdapter(new SimpleAdapter(this,data,R.layout.list_item,new String[]{"description","icon"},new int[]{R.id.info,R.id.icon}));
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				OnClickListViewItem(position);

			}

			private void OnClickListViewItem(int position) {


				
			
				String key = getIMSI();

				switch(position){
				
				case 0: query_Traffic(key);	//查询流量余额	
				Toast.makeText(MainActivity.this, "查询流量的短信已经发出。。。", Toast.LENGTH_SHORT).show();
				

					break;
				
				case 1: inquiry_Calls(key);//查询话费余额
				Toast.makeText(MainActivity.this, "查询话费短信已经发出。。。", Toast.LENGTH_SHORT).show();
				
					break;

				case 2:                    //查询增值业务
					break;

				default:
					break;
				}


			}

		



		});










	}
	
	


	private String getIMSI() {
		int simState = tm.getSimState();

		String imsi = tm.getSubscriberId();

		SmsManager smsManager = SmsManager.getDefault();

		if(imsi!=null){ 
			//因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号 //中国移动
			if(imsi.startsWith("46000") || imsi.startsWith("46002"))

				return MOBIE;


			//smsManager.sendTextMessage(MOBIE, null, content, null, null);

			else if(imsi.startsWith("46001"))



				//中国联通
				return UNICOM;

			else if(imsi.startsWith("46003"))

				//中国电信
				return TELECOM;

			else
				return "unknow";

		}else

			return "sim is null";


	}

	
	
	private void query_Traffic(String key){
		if(MOBIE==key)
			smsManager.sendTextMessage(key, null, "1581", null, null);
		else if(TELECOM==key)
			smsManager.sendTextMessage(key, null,"108", null, null);		
		else
			smsManager.sendTextMessage(key, null, "5118", null, null);
		
		
	}
	
	
	private void inquiry_Calls(String key){
		if(MOBIE==key)
			smsManager.sendTextMessage(MOBIE, null, "11", null, null);
		
		else if(TELECOM==key)
			smsManager.sendTextMessage(TELECOM, null, "102", null, null);
		
		else
			smsManager.sendTextMessage(UNICOM, null, "102", null, null);

	}
	
	
	

}







