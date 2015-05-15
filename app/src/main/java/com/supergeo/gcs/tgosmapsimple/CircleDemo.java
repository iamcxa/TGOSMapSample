package com.supergeo.gcs.tgosmapsimple;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import tgos.TGOnlineMap;
import tgos.exception.TGRuntimeRemoteException;
import tgos.model.TGCircle;
import tgos.model.TGCircleOptions;
import tgos.model.TGLatLng;

public class CircleDemo extends Activity  implements OnSeekBarChangeListener{

	RelativeLayout AddMapView;
	TGOnlineMap _MapView = null;
	
	TGCircle circle = null;
	TGLatLng pt1 =  new TGLatLng(23.97, 121.78);

    private static final int WIDTH_MAX = 50;
    private static final int HUE_MAX = 360;
    private static final int ALPHA_MAX = 255;
    
    private SeekBar mColorBar;
    private SeekBar mAlphaBar;
    private SeekBar mWidthBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.circle_demo);
		AddMapView = (RelativeLayout)findViewById(R.id.AddMapView);
		try {
			_MapView = new TGOnlineMap(this);
		
			_MapView.setBackgroundColor(Color.rgb(165,191,221));
			AddMapView.addView(_MapView);
				
			//--Circle的介面控制項-------------------------------------------------------
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
			//-----------------------------------------------------------------------
			
	        int color = Color.HSVToColor(
	                mAlphaBar.getProgress(), new float[] {mColorBar.getProgress(), 1, 1});
	        
			circle = _MapView.addCircle(new TGCircleOptions()//加入一個圓
					.center(pt1)//設定中心點(坐標要使用WGS84)
					.radius(20000)							//設定半徑(公尺)
					.strokeWidth(mWidthBar.getProgress())	//設定邊寬
					.fillColor(color));						//設定顏色
			
			TGCircle TP = _MapView.addCircle(new TGCircleOptions()
					.center(new TGLatLng(25.060721,121.442871))
					.radius(5000)
					.strokeWidth(3)
					.fillColor(Color.RED));
			
			TGCircle CH = _MapView.addCircle(new TGCircleOptions()
					.center(new TGLatLng(24.746831,120.992432))
					.radius(5000)
					.strokeWidth(3)
					.fillColor(Color.GREEN));
			TGCircle ME = _MapView.addCircle(new TGCircleOptions()
					.center(new TGLatLng(24.45215,120.800171))
					.radius(5000)
					.strokeWidth(3)
					.fillColor(Color.YELLOW));

		} catch (TGRuntimeRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		 if (circle == null) {
	            return;
	        }

	        if (seekBar == mColorBar) {//可以動態改變Circle的邊寬、顏色
	        	circle.setFillColor(Color.HSVToColor(
	                    Color.alpha(circle.getFillColor()), new float[] {progress, 1, 1}));
	        } else if (seekBar == mAlphaBar) {
	            float[] prevHSV = new float[3];
	            Color.colorToHSV(circle.getFillColor(), prevHSV);
	            circle.setFillColor(Color.HSVToColor(progress, prevHSV));
	        } else if (seekBar == mWidthBar) {
	        	circle.setStrokeWidth(progress);
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
