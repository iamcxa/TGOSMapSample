package com.supergeo.gcs.tgosmapsimple;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tgos.TGOnlineMap;
import tgos.TGOnlineMap.OnMapClickListener;
import tgos.TGOnlineMap.OnViewerChangeListener;
import tgos.TGOnlineMap.onMapLongClickListener;
import tgos.TGProjection;
import tgos.TGTransformation;
import tgos.TGViewerPosition;
import tgos.exception.TGRuntimeRemoteException;
import tgos.model.TGLatLng;

public class EventDemo extends Activity 
			implements  OnMapClickListener,onMapLongClickListener ,OnViewerChangeListener{//實做OnMapClickListener,onMapLongClickListener ,OnViewerChangeListener
	
	RelativeLayout AddMapView;
	TGOnlineMap _MapView = null;
	
	TextView ShowMsg;
//	TGViewerPosition lastViewerPosition;
	String lastViewerPosition="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_demo);
		AddMapView = (RelativeLayout)findViewById(R.id.AddMapView);
		ShowMsg = (TextView)findViewById(R.id.ShowMsg);
		
		try {
			_MapView = new TGOnlineMap(this);
			//添加Listener-------------------------------------------
			_MapView.setOnMapClickListener(this);
			_MapView.setOnMapLongClickListener(this);
			_MapView.setOnViewerChangeListener(this);
			//------------------------------------------------------
			_MapView.setBackgroundColor(Color.rgb(165,191,221));
			AddMapView.addView(_MapView);
			
		} catch (TGRuntimeRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onViewerChnage(TGViewerPosition arg0) {//畫面有變動時觸發
		// TODO Auto-generated method stub
		//避免重複呼叫
		TGLatLng target=arg0._Target;//螢幕中央位置的坐標
		String msg = "onViewerChnage: \nTarget X = "+target.X +",\nY = "+ target.Y+"\n";
		msg+="ZOOM = "+ arg0._Zoom;//目前的地圖大小層級
		if (lastViewerPosition.equals(msg))
		{
			return;
		}
		Log.i("test", msg);
		ShowMsg.setText(msg);
		lastViewerPosition = msg;
	}
	@Override
	public void onMapLongClick(TGLatLng arg0) {//地圖長按時觸發       傳入長按坐標
		// TODO Auto-generated method stub
		
		//建立地圖坐標轉換相關物件
		TGProjection PJ =_MapView.getProjection();
		
		TGLatLng mapPt= arg0;
		//地圖坐標轉成螢幕坐標
		Point pt = PJ.toScreenLocation(mapPt);
		String msg = "onMapLongClick: \nScreen X = "+pt.x +",Y = "+ pt.y+"\n";
		msg +="TWD97 X = "+mapPt.X+",Y = "+ mapPt.Y+"\n";
		
		//不同的坐標系統轉換
		TGLatLng TFPt = TGTransformation.WGS84toTWD97(false, mapPt);//true為WGS84轉TWD97，false為TWD97轉WGS84
		msg +="WGS84 Lat = "+TFPt.getLat()+",Lng = "+ TFPt.getLon()+"\n";
		ShowMsg.setText(msg);
	}
	@Override
	public void onMapClick(TGLatLng arg0) {//點擊地圖時觸發        傳入點擊坐標
		// TODO Auto-generated method stub
		
		//建立地圖坐標轉換相關物件
		TGProjection PJ =_MapView.getProjection();
		
		TGLatLng mapPt= arg0;
		//地圖坐標轉成螢幕坐標
		Point pt = PJ.toScreenLocation(mapPt);
		String msg = "onMapClick: \nScreen X = "+pt.x +",Y = "+ pt.y+"\n";
		msg +="TWD97 X = "+mapPt.X+",Y = "+ mapPt.Y+"\n";
		
		//不同的坐標系統轉換
		TGLatLng TFPt = TGTransformation.WGS84toTWD97(false, mapPt);//true為WGS84轉TWD97，false為TWD97轉WGS84
		msg +="WGS84 Lat = "+TFPt.getLat()+",Lng = "+ TFPt.getLon()+"\n";
		ShowMsg.setText(msg);
	}

}
