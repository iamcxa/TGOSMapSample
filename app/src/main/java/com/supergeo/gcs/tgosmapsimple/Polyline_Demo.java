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
import tgos.model.TGLatLng;
import tgos.model.TGLatLngBounds;
import tgos.model.TGPolyline;
import tgos.model.TGPolylineOptions;

public class Polyline_Demo extends Activity  implements OnSeekBarChangeListener{
	RelativeLayout AddMapView;
	TGOnlineMap _MapView = null;
	
	TGPolyline polyline = null;
	TGLatLng pt1 =  new TGLatLng(25.06654, 121.53393);
	TGLatLng pt2 = new TGLatLng(25.06651, 121.53332);
	TGLatLng pt3 = new TGLatLng(25.05963, 121.53319);

    private static final int WIDTH_MAX = 50;
    private static final int HUE_MAX = 360;
    private static final int ALPHA_MAX = 255;
    
    private SeekBar mColorBar;
    private SeekBar mAlphaBar;
    private SeekBar mWidthBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.polyline__demo);
		AddMapView = (RelativeLayout)findViewById(R.id.AddMapView);
		try {
			_MapView = new TGOnlineMap(this);
		
			_MapView.setBackgroundColor(Color.rgb(165,191,221));
			AddMapView.addView(_MapView);
			//Polyline 介面的控制項-----------------------------------
	        mColorBar = (SeekBar) findViewById(R.id.ColorBar);
	        mColorBar.setMax(HUE_MAX);
	        mColorBar.setProgress(0);
	
	        mAlphaBar = (SeekBar) findViewById(R.id.AlphaBar);
	        mAlphaBar.setMax(ALPHA_MAX);
	        mAlphaBar.setProgress(255);
	
	        mWidthBar = (SeekBar) findViewById(R.id.WidthBar);
	        mWidthBar.setMax(WIDTH_MAX);
	        mWidthBar.setProgress(10);
	        
	        mColorBar.setOnSeekBarChangeListener(this);
	        mAlphaBar.setOnSeekBarChangeListener(this);
	        mWidthBar.setOnSeekBarChangeListener(this);
				
	        //---------------------------------------------------
	        
	        
	        int color = Color.HSVToColor(
	                mAlphaBar.getProgress(), new float[] {mColorBar.getProgress(), 1, 1});
	        
			polyline = _MapView.addPolyline(new TGPolylineOptions()//地圖上加入polyline
			.add(pt1, pt2,pt3)//坐標要用WGS84
			.width(mWidthBar.getProgress())//設定polyline的寬度
			.color(color)//設定polyline的顏色
			);
			
			TGLatLngBounds bounds = new TGLatLngBounds.Builder()
	        .include(pt1)
	        .include(pt2)
	        .include(pt3)
	        .build();
	    	_MapView.moveViewer(TGViewerUpdateFactory.LatLngBounds(bounds, 5));//圖窗依指定經緯度邊界調整，置於螢幕中心，並縮放至可能之最大縮放層級。
		} catch (TGRuntimeRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean arg2) {
		// TODO Auto-generated method stub
		 if (polyline == null) {
	            return;
	        }
	
	        if (seekBar == mColorBar) {//可以動態修改polyline的顏色及寬度
	        	polyline.setColor(Color.HSVToColor(
	                    Color.alpha(polyline.getColor()), new float[] {progress, 1, 1}));
	        } else if (seekBar == mAlphaBar) {
	            float[] prevHSV = new float[3];
	            Color.colorToHSV(polyline.getColor(), prevHSV);
	            polyline.setColor(Color.HSVToColor(progress, prevHSV));
	        } else if (seekBar == mWidthBar) {
	        	polyline.setWidth(progress);
	        }
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		_MapView.invalidate(true);
	}


}
