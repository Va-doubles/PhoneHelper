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


		function1.put("description", "�ײ�������ѯ");
		function1.put("icon",R.drawable.ic_jog_dial_decline);


		function2.put("description", "��������ѯ");
		function2.put("icon",R.drawable.ic_jog_dial_vibrate_on);


		function3.put("description", "��ֵҵ���ѯ");
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
				
				case 0: query_Traffic(key);	//��ѯ�������	
				Toast.makeText(MainActivity.this, "��ѯ�����Ķ����Ѿ�����������", Toast.LENGTH_SHORT).show();
				

					break;
				
				case 1: inquiry_Calls(key);//��ѯ�������
				Toast.makeText(MainActivity.this, "��ѯ���Ѷ����Ѿ�����������", Toast.LENGTH_SHORT).show();
				
					break;

				case 2:                    //��ѯ��ֵҵ��
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
			//��Ϊ�ƶ�������46000�µ�IMSI�Ѿ����꣬����������һ��46002��ţ�134/159�Ŷ�ʹ���˴˱�� //�й��ƶ�
			if(imsi.startsWith("46000") || imsi.startsWith("46002"))

				return MOBIE;


			//smsManager.sendTextMessage(MOBIE, null, content, null, null);

			else if(imsi.startsWith("46001"))



				//�й���ͨ
				return UNICOM;

			else if(imsi.startsWith("46003"))

				//�й�����
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







