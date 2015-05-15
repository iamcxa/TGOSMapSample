package com.supergeo.gcs.tgosmapsimple;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import supergis.runtime.sdk.definition.ISensorAdapter;
import supergis.runtime.sdk.definition.ISensorAdapterListener;
import supergis.runtime.sdk.utility.Design;
import tgos.AR.TGAugmentedReality;
import tgos.AR.TGCameraView;
import tgos.AR.TGSensorAdapter;
import tgos.TGLocationAdapter;
import tgos.TGOnlineMap;
import tgos.model.TGBitmapDescriptorFactory;
import tgos.model.TGLatLng;
import tgos.model.TGMarkerOptions;

public class ARDemo extends Activity implements ISensorAdapterListener,OnTouchListener{//實做ISensorAdapterListener
	TGOnlineMap mapView = null;
	TGCameraView _CameraView;
	TGAugmentedReality _CameraOverlay = null;
	TGLocationAdapter _LocationAdapter = null;
	TGSensorAdapter _SensorAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	        try
	        {
	        	 mapView = new TGOnlineMap(this);//要new TGOSMap，AR會使用到TGOSMap裡的資源
	        	Camera camera = Camera.open();//開啟相機
	        	supergis.runtime.sdk.utility.Design.FullScreen(this);//設定全螢幕
	        	setContentView (R.layout.activity_ardemo);  
	        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//鎖定螢幕方向，維持螢幕為 PORTRAIT
	            
	    		_LocationAdapter = new TGLocationAdapter(this);//建立GPS相關物件
	    		_SensorAdapter = new TGSensorAdapter(this);    //建立Sensor相關物件	
	    		_SensorAdapter.AddSensorAdapterListener(this); //加入Listener
	    		_CameraView = new TGCameraView(this,camera);	//建立相機畫面	
	    		RelativeLayout  layout =  (RelativeLayout )(findViewById(R.id.CameraLayout));	//放置AR的    Layout
	    		
	    		//建立AR畫面，需要加入相機、GPS、Sensor相關資訊
	    		_CameraOverlay = new TGAugmentedReality(this, _CameraView, _SensorAdapter, _LocationAdapter);
	    		 _CameraOverlay.setMaxDistance(1000);//設定距離(公尺)
	    		 _CameraOverlay.setOnTouchListener(this);
	    		 //加入Mark
	    		TGLatLng PT = new TGLatLng(25.082023,121.567025);//坐標要用WGS84
	    		TGMarkerOptions a = new TGMarkerOptions()
	      	    .position(PT)
	      	    .icon(TGBitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
	      	    .title("西湖捷運站");
	            _CameraOverlay.addMarker(a);
	            
	            //依序把相機畫面及AR畫面加入
	    		layout.addView(_CameraView);
	    		layout.addView(_CameraOverlay, layout.getChildCount()); 
	    		
	        }
	        catch(Exception ex)
	        {
	        	Design.ShowAlert("onCreate", ex.toString());
	        }
	}

	   @Override
	    protected void onStart() 
	    {
	    	super.onStart();//開啟Sensor及GPS
			_LocationAdapter.Start();
			_SensorAdapter.Start();
	    }

	    
	    @Override
	    protected void onStop() 
	    {
	    	super.onStop();//關閉Sensor及GPS
			_LocationAdapter.Stop();
			_SensorAdapter.Stop();
			_CameraView.surfaceDestroyed(null);
	    }  	 

		@Override
		public void SensorChanged(ISensorAdapter arg0) {
			try
			{			//手機 Sensor有動作時更新畫面
				if(_CameraView != null && _CameraOverlay != null &&
					_CameraView.getWidth() <= 0 || _CameraView.getHeight() <= 0)
					return;
				_CameraOverlay.RefreshOverlay(); 
			}
			catch(Exception ex){}
			
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			int TX = (int) event.getRawX();
			int TY = (int) event.getRawY();
			TGMarkerOptions TMarker = _CameraOverlay.TGARHitTest(TX, TY);
			if(TMarker != null)
			{
				Toast.makeText(this, "TMarker:"+TMarker.getTitle(), Toast.LENGTH_LONG).show();  
			}
			return false;
		}

}
