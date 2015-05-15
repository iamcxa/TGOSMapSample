package com.supergeo.gcs.tgosmapsimple;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;

import tgos.TGOnlineMap;
import tgos.exception.TGRuntimeRemoteException;

public class BaseMap_Demo extends Activity {
	TGOnlineMap _MapView = null;
	RelativeLayout AddMapView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.base_map_demo);
        AddMapView = (RelativeLayout)findViewById(R.id.AddMapView);//放入地圖的Layout，如果需要套疊不同的View就用RelativeLayout
        try {
			_MapView = new TGOnlineMap(this);//建立TGOSMap
	        AddMapView.addView(_MapView);//加入到畫面中
			_MapView.setBackgroundColor(Color.rgb(165,191,221));//設定地圖底色
        } catch (TGRuntimeRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
