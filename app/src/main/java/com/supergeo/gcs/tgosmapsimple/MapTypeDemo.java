package com.supergeo.gcs.tgosmapsimple;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import tgos.TGOnlineMap;
import tgos.TGOnlineMap.MapType;
import tgos.exception.TGRuntimeRemoteException;

public class MapTypeDemo extends Activity  implements OnItemSelectedListener{
	TGOnlineMap _MapView = null;
	RelativeLayout AddMapView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_type_demo);
		AddMapView = (RelativeLayout)findViewById(R.id.AddMapView);
        try {
			_MapView = new TGOnlineMap(this);
		
	        AddMapView.addView(_MapView);
			_MapView.setBackgroundColor(Color.rgb(165,191,221));
			 Spinner spinner = (Spinner) findViewById(R.id.MapType_spinner);
			 
		  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	                this, R.array.MapType_array, android.R.layout.simple_spinner_item);
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spinner.setAdapter(adapter);
	        spinner.setOnItemSelectedListener(this);
        } catch (TGRuntimeRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	/*圖磚代碼
	 名稱說明
	 
	TGOSMAP
	 TGOS MAP
	 
	NLSCMAP
	 通用版電子地圖
	 
	ROADMAP
	 混合地圖
	 
	F2IMAGE
	 福衛二號衛星影像*/
	 
	  private void setLayer(String layerName) {
	        if (layerName.equals(getString(R.string.TGOSMAP))) {
	        	_MapView.setMapType(MapType.TGOSMAP);
	        } else if (layerName.equals(getString(R.string.NLSCMAP))) {
	        	_MapView.setMapType(MapType.NLSCMAP);
	        } else if (layerName.equals(getString(R.string.ROADMAP))) {
	        	_MapView.setMapType(MapType.ROADMAP);
	        } else if (layerName.equals(getString(R.string.F2IMAGE))) {
	        	_MapView.setMapType(MapType.F2IMAGE);
	        } else {
	            Log.i("LDA", "Error setting layer with name " + layerName);
	        }
	        _MapView.invalidate(true);
	    }
	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		setLayer((String) parent.getItemAtPosition(position));
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
