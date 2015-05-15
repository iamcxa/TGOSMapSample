package com.supergeo.gcs.tgosmapsimple;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import supergis.runtime.sdk.core.MapGPSDisplay;
import supergis.runtime.sdk.definition.GPSPositionType;
import supergis.runtime.sdk.definition.ILocationAdapter;
import supergis.runtime.sdk.definition.ILocationAdapterListener;
import supergis.runtime.sdk.definition.ISensorAdapter;
import supergis.runtime.sdk.definition.ISensorAdapterListener;
import supergis.runtime.sdk.utility.Location;
import tgos.AR.TGSensorAdapter;
import tgos.TGLocationAdapter;
import tgos.TGOnlineMap;
import tgos.TGUiSettings;
import tgos.exception.TGRuntimeRemoteException;

public class UISetting_Demo extends Activity implements ILocationAdapterListener, ISensorAdapterListener {
	RelativeLayout AddMapView;
	TGLocationAdapter _LocationAdapter = null;
	TGSensorAdapter _SensorAdapter = null;
	TGOnlineMap _MapView = null;
	MapGPSDisplay _MapGPSDisplay = null;
	TGUiSettings MapUiSetting = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uisetting_demo);
		AddMapView = (RelativeLayout)findViewById(R.id.AddMapView);
		try {
			_MapView = new TGOnlineMap(this);
		
			_MapView.setBackgroundColor(Color.rgb(165,191,221));
			AddMapView.addView(_MapView);
			MapUiSetting = _MapView.getUiSetting();
			//以下程式碼乃是控制 UiSetting 
			MapUiSetting.setScrollGesturesEnabled(true);//設定地圖可否平移
			MapUiSetting.setZoomControlsEnabled(true);//設定是否顯示地圖控制項
			MapUiSetting.setZoomGesturesEnabled(true);//設定地圖可否縮放
	        // --
			//[LocationAdapter]
	  		_LocationAdapter = new TGLocationAdapter(this);//建立GPS物件
	  		_LocationAdapter.AddLocationAdapterListener(this);
	  		_LocationAdapter.setSpatialReference(_MapView.getMap().getSpatialReference());//設定地圖的坐標系統
	  		//[SensorAdapter]
	  		_SensorAdapter = new TGSensorAdapter(this);//建立Sensor物件
	  		_SensorAdapter.AddSensorAdapterListener(this);
	  		
	  		//[MapGPSDisplay]
			if(_MapGPSDisplay == null)//設定使用者的位置
			{
		  		_MapGPSDisplay = new MapGPSDisplay(_MapView, _LocationAdapter, _SensorAdapter,
		  				BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));		
		  		_MapGPSDisplay.setGPSPositionType(GPSPositionType.XY);//設定坐標系統
		  		_MapView.getMapViewOverlays().add(_MapGPSDisplay);//把使用者GPS位置加入圖層
		  		
		  		_MapView.FullExtent();//顯示全地圖
		  		_MapView.invalidate(true);
			}
		} catch (TGRuntimeRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    //CheckBox Event
    private boolean checkReady() {
        if (_MapView == null) {
            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void setZoomButtonsEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        MapUiSetting.setZoomControlsEnabled(((CheckBox) v).isChecked());
    }
    
    public void setScrollGesturesEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        MapUiSetting.setScrollGesturesEnabled(((CheckBox) v).isChecked());
    }
    public void setZoomGesturesEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        MapUiSetting.setZoomGesturesEnabled(((CheckBox) v).isChecked());
    }
    
	@Override
	protected void onStart() 
	{
		super.onStart();
		try 
		{	
			_SensorAdapter.Start();
			_LocationAdapter.Start();		
		} 
		catch (Exception e) {
		}
	}
    
    @Override
	protected void onStop() 
	{
		super.onStop();
		try 
		{	
			_SensorAdapter.Stop();
			_LocationAdapter.Stop();
		} 
		catch (Exception e) {
		}
	}
	@Override
	public void SensorChanged(ISensorAdapter arg0) {
		// TODO Auto-generated method stub
		if(!_MapView.getIsLocked())
			_MapView.invalidate(false);	
	}

	@Override
	public void LocationChanged(ILocationAdapter arg0, Location arg1) {
		// TODO Auto-generated method stub
		if(!_MapView.getIsLocked())
			_MapView.invalidate(false); 
	}

}
