package com.example.lento.demotest.samsungdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lento.demotest.R;
import com.example.lento.demotest.samsungdemo.calllog.CallLogModel;
import com.example.lento.demotest.samsungdemo.calllog.CallLogsArrayAdapter;

public class CallLogs extends Activity {
	private ListView mainListView;
	private ArrayAdapter<CallLogModel> listAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.samsung_demo_listview);
		mainListView = (ListView) findViewById(R.id.listView);
		mainListView.setSmoothScrollbarEnabled(true);

		Bundle extras = getIntent().getExtras();
		int callType = extras.getInt(CallLogsDemoActivity.CALL_TYPE);
		if (callType == CallLogsDemoActivity.OUTGOING_CALLS)
			listAdapter = new CallLogsArrayAdapter(this,
					CallLogsDemoActivity.outgoingList);
		else if (callType == CallLogsDemoActivity.INCOMING_CALLS)
			listAdapter = new CallLogsArrayAdapter(this,
					CallLogsDemoActivity.incomingList);
		else if (callType == CallLogsDemoActivity.MISSED_CALLS)
			listAdapter = new CallLogsArrayAdapter(this,
					CallLogsDemoActivity.missedcallList);
		mainListView.setAdapter(listAdapter);

	}

	public void initElements() {
		listAdapter.notifyDataSetChanged();
	}
}
