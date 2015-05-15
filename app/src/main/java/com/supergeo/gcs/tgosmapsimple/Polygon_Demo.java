package com.supergeo.gcs.tgosmapsimple;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import tgos.TGOnlineMap;
import tgos.exception.TGRuntimeRemoteException;
import tgos.model.TGLatLng;
import tgos.model.TGPolygon;
import tgos.model.TGPolygonOptions;

public class Polygon_Demo extends Activity  implements OnSeekBarChangeListener{
	RelativeLayout AddMapView;
	TGOnlineMap _MapView = null;
	
	TGPolygon polygon = null;
	TGLatLng pt1 =  new TGLatLng(25.085599,121.772461);
	TGLatLng pt2 = new TGLatLng(25.060721,121.442871);
	TGLatLng pt3 =  new TGLatLng(24.746831,120.992432);
	TGLatLng pt4 =  new TGLatLng(24.45215,120.800171);
	TGLatLng pt5 =  new TGLatLng(24.026397,121.415405);
	

    private static final int WIDTH_MAX = 50;
    private static final int HUE_MAX = 360;
    private static final int ALPHA_MAX = 255;
    
    private SeekBar mColorBar;
    private SeekBar mAlphaBar;
    private SeekBar mWidthBar;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.polygon__demo);
		AddMapView = (RelativeLayout)findViewById(R.id.AddMapView);
		try {
			_MapView = new TGOnlineMap(this);
		
			_MapView.setBackgroundColor(Color.rgb(165,191,221));
			AddMapView.addView(_MapView);
				
			//Polygon 介面控制項----------------------------------------------------------
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
	        //--------------------------------------------------------------------------
	        
	        
	        int color = Color.HSVToColor(
	                mAlphaBar.getProgress(), new float[] {mColorBar.getProgress(), 1, 1});
	        
	        
	        polygon = _MapView.addPolygon(new TGPolygonOptions()
			.add(pt1, pt2,pt3,pt4,pt5)//坐標要用WGS84，依照頂點新增順序建立Polygon
			.fillColor(color)
			.strokeWidth(mWidthBar.getProgress()));
	        
	        // Example 2: Polygon with two holes		 
			TGPolygon polygonWithHoles = _MapView.addPolygon(new TGPolygonOptions()
					.add(new TGLatLng(24.4,119.0), new TGLatLng(24.4,121.0), new TGLatLng(23.0,121.0),new TGLatLng(23.0,120.0))//設定polygon
					.addHoles(new TGLatLng(24.3,120.1), new TGLatLng(24.3,120.2)
					, new TGLatLng(23.1,120.2),new TGLatLng(23.1,120.1))//設定Holes，不同的Hole要用","分開
					.fillColor(Color.argb(60, 0, 0, 255))//設定顏色
					.strokeWidth(1));//設定邊框
		} catch (TGRuntimeRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		 if (polygon == null) {
	            return;
	        }
	        if (seekBar == mColorBar) {//可以動態修改顏色及邊寬
	        	polygon.setFillColor(Color.HSVToColor(
	                    Color.alpha(polygon.getFillColor()), new float[] {progress, 1, 1}));
	        } else if (seekBar == mAlphaBar) {
	            float[] prevHSV = new float[3];
	            Color.colorToHSV(polygon.getFillColor(), prevHSV);
	            polygon.setFillColor(Color.HSVToColor(progress, prevHSV));
	        } else if (seekBar == mWidthBar) {
	        	polygon.setStrokeWidth(progress);
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
