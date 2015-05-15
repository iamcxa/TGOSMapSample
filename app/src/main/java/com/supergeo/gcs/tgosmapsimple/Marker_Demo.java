package com.supergeo.gcs.tgosmapsimple;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import tgos.TGOnlineMap;
import tgos.TGOnlineMap.OnInfoWindowClickListener;
import tgos.TGOnlineMap.OnMarkerClickListener;
import tgos.TGOnlineMap.OnMarkerDragListener;
import tgos.exception.TGRuntimeRemoteException;
import tgos.model.TGBitmapDescriptorFactory;
import tgos.model.TGLatLng;
import tgos.model.TGMarker;
import tgos.model.TGMarkerOptions;

public class Marker_Demo extends Activity implements OnInfoWindowClickListener, OnMarkerClickListener ,OnMarkerDragListener{
	RelativeLayout AddMapView;
	TGOnlineMap _MapView = null;
	TGMarker NoMove;
	TGMarker canMove;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marker__demo);
		AddMapView = (RelativeLayout)findViewById(R.id.AddMapView);
		try {
			_MapView = new TGOnlineMap(this);
		
			_MapView.setBackgroundColor(Color.rgb(165,191,221));
			AddMapView.addView(_MapView);
			_MapView.setOnInfoWindowClickListener(this);
			_MapView.setOnMarkerClickListener(this);
			_MapView.setOnMarkerDragListener(this);
			
			//設定Marker
			NoMove = _MapView.addMarker(new TGMarkerOptions()
			.position(new TGLatLng(24.4,120.0))//坐標用WGS84
			.title("title:Marker1")//設置標題
			.snippet("snippet:Marker1")//設置內文
			.draggable(false)//可否拖曳
			.icon(TGBitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));//設定圖片
			canMove = _MapView.addMarker(new TGMarkerOptions()
			.position(new TGLatLng(23.4,121.0))
			.title("title:canMove")
			.snippet("snippet:canMove")
			.draggable(true)
			.icon(TGBitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
			
		} catch (TGRuntimeRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void OnChangeMarkVisible(View v)
	{
		NoMove.setVisible(((CheckBox) v).isChecked());//設置Marker是否顯示
		canMove.setVisible(((CheckBox) v).isChecked());
		_MapView.invalidate(true);//設定完要更新畫面
	}
	@Override
	public void onInfoWindowClick(TGMarker arg0) {//點擊InfoWindow時觸發
		// TODO Auto-generated method stub
		Toast.makeText(this, "onInfoWindowClick:"+arg0.getTitle(), Toast.LENGTH_LONG).show(); 
	}
	@Override
	public void onMarkerClick(TGMarker arg0) {//點擊Marker時觸發
		// TODO Auto-generated method stub
		Toast.makeText(this,"onMarkerClick:"+arg0.getTitle()+"X = "+arg0.getPosition().X+"Y = "+arg0.getPosition().Y, Toast.LENGTH_LONG).show();
	}
	@Override
	public void onMarkerDrag(TGMarker arg0) {//Marker拖曳時觸發
		// TODO Auto-generated method stub
		Log.i("Marker", arg0.getTitle()+":Dragging");
	}
	@Override
	public void onMarkerDragEnd(TGMarker arg0) {//Marker拖曳結束時觸發
		// TODO Auto-generated method stub
		Log.i("Marker", arg0.getTitle()+":Drag End");
	}
	@Override
	public void onMarkerDragStart(TGMarker arg0) {//Marker觸碰時觸發
		// TODO Auto-generated method stub
		Log.i("Marker", arg0.getTitle()+":Drag Start");
	}


}
