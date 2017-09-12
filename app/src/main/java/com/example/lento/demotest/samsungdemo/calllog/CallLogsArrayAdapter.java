package com.example.lento.demotest.samsungdemo.calllog;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lento.demotest.R;

public class CallLogsArrayAdapter extends ArrayAdapter<CallLogModel> {
	private LayoutInflater inflater;

	/**
	 * @purpose Dynamic binding of xml
	 * @param context
	 */

	/* Constructor */
	public CallLogsArrayAdapter(Context context, List<CallLogModel> callLogsList) {
		super(context, R.layout.samsung_demo_list_item, R.id.nameTV, callLogsList);
		// Cache the LayoutInflate to avoid asking for a new one each time.
		inflater = LayoutInflater.from(context);
	}

	/*
	 * Function name: getType Parameters: none Return: int
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Contact to display
		final CallLogModel callLogModel = this.getItem(position);

		TextView nameTV;		
		TextView numberTV;
		TextView dateTV;
		
		TextView durationTV;

		//Code is kept simple and not optimized
		convertView = inflater.inflate(R.layout.samsung_demo_list_item, null);

		// Find the child views.
		nameTV = (TextView) convertView.findViewById(R.id.nameTV);		
		numberTV = (TextView) convertView.findViewById(R.id.numberTV);
		dateTV = (TextView) convertView.findViewById(R.id.dateTV);		
		durationTV = (TextView) convertView.findViewById(R.id.durationTV);

		nameTV.setText("Name: "+callLogModel.getName());
		numberTV.setText("Number: "+callLogModel.getNumber());
		dateTV.setText("Date & Time: "+callLogModel.getDate());				
		durationTV.setText("Duration: "+callLogModel.getDuration()+" secs");
		return convertView;
	}

}
