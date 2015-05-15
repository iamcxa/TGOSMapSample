package com.supergeo.gcs.tgosmapsimple;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import tgos.TGOnlineMap;
import tgos.TGViewerUpdateFactory;
import tgos.exception.TGRuntimeRemoteException;
import tgos.model.TGBitmapDescriptorFactory;
import tgos.model.TGGroundOverlay;
import tgos.model.TGGroundOverlayOptions;
import tgos.model.TGLatLng;

public class GroundDemo extends Activity implements OnSeekBarChangeListener {
	TGOnlineMap _MapView = null;
	RelativeLayout AddMapView;
	private static final TGLatLng KL = new TGLatLng(25.197796,121.614532);
    private TGGroundOverlay mGroundOverlay;
    private SeekBar mTransparencyBar;
    private static final int TRANSPARENCY_MAX = 100;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ground_demo);
		AddMapView = (RelativeLayout)findViewById(R.id.AddMapView);
        try {
			_MapView = new TGOnlineMap(this);
		
	        AddMapView.addView(_MapView);
			_MapView.setBackgroundColor(Color.rgb(165,191,221));
			mTransparencyBar = (SeekBar) findViewById(R.id.transparencySeekBar);
	        mTransparencyBar.setMax(TRANSPARENCY_MAX);
	        mTransparencyBar.setProgress(0);
	        mTransparencyBar.setOnSeekBarChangeListener(this);
	        
	        //設定圖片的位置及大小
			mGroundOverlay= _MapView.addGroundOverlay(new TGGroundOverlayOptions()
	        .image(TGBitmapDescriptorFactory.fromResource(R.drawable.klmap))//設定圖片
	        .anchor(0, 0)//設定圖片的圓點
	        .position(KL, 22580f, 16000f));//設定坐標及圖片的大小(公尺)
			
			_MapView.moveViewer(TGViewerUpdateFactory.newLatLng(KL));//移動畫面
        } catch (TGRuntimeRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
		// TODO Auto-generated method stub
		 if (mGroundOverlay != null) {//Ground的透明度
	            mGroundOverlay.setTransparency((float) progress / (float) TRANSPARENCY_MAX);
	        }
	}
	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		_MapView.invalidate(true);
	}


}
