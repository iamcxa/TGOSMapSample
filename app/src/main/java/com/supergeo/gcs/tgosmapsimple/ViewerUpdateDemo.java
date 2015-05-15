package com.supergeo.gcs.tgosmapsimple;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import tgos.TGOnlineMap;
import tgos.TGViewerUpdateFactory;
import tgos.exception.TGRuntimeRemoteException;
import tgos.model.TGBitmapDescriptorFactory;
import tgos.model.TGLatLng;
import tgos.model.TGLatLngBounds;
import tgos.model.TGMarkerOptions;

public class ViewerUpdateDemo extends Activity {
	RelativeLayout AddMapView;
	TGOnlineMap _MapView = null;
	private static final int SCROLL_BY_PX = 100;
	
//    private static final TGLatLng farLeft = new TGLatLng(24.733, 121.038);
//    private static final TGLatLng farRight = new TGLatLng(24.704, 121.336);
//    private static final TGLatLng nearLeft = new TGLatLng(24.551, 120.902);
//    private static final TGLatLng nearRight = new TGLatLng(24.520, 121.505);
    
    private static final TGLatLng boundPt1 = new TGLatLng(24.642, 121.134);
    private static final TGLatLng boundPt2 = new TGLatLng(24.610, 121.296);
    private static final TGLatLng boundPt3 = new TGLatLng(24.575, 121.181);
    private static final TGLatLng KL = new TGLatLng(25.197796,121.614532);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer_update_demo);
		AddMapView = (RelativeLayout)findViewById(R.id.AddMapView);
		try {
			_MapView = new TGOnlineMap(this);
		
			_MapView.setBackgroundColor(Color.rgb(165,191,221));
			AddMapView.addView(_MapView);
			
			_MapView.addMarker(new TGMarkerOptions()
			.position(boundPt1)
			.draggable(false)
			.icon(TGBitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
			_MapView.addMarker(new TGMarkerOptions()
			.position(boundPt2)
			.draggable(false)
			.icon(TGBitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
			_MapView.addMarker(new TGMarkerOptions()
			.position(boundPt3)
			.draggable(false)
			.icon(TGBitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
		} catch (TGRuntimeRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void onMovetToKL(View view) {
    	_MapView.moveViewer(TGViewerUpdateFactory.newLatLngZoom(KL, 0));//圖窗移動到指定的坐標位置。
    }
    public void onBounds(View view) {
		TGLatLngBounds bounds = new TGLatLngBounds.Builder()
        .include(boundPt1)
        .include(boundPt2)
        .include(boundPt3)
        .build();
    	_MapView.moveViewer(TGViewerUpdateFactory.LatLngBounds(bounds, 5));//圖窗依指定經緯度邊界調整，置於螢幕中心，並縮放至可能之最大縮放層級。
    }
    public void onZoomIn(View view) {
    	_MapView.moveViewer(TGViewerUpdateFactory.zoomIn());//放大地圖
    }
    public void onZoomOut(View view) {
    	_MapView.moveViewer(TGViewerUpdateFactory.zoomOut());//縮小地圖
    }
    //將地圖中心移動指定的距離(Pixel)-----------------------
    public void onScrollLeft(View view) {
    	_MapView.moveViewer(TGViewerUpdateFactory.scrollBy(-SCROLL_BY_PX, 0));
    }
    public void onScrollRight(View view) {
    	_MapView.moveViewer(TGViewerUpdateFactory.scrollBy(SCROLL_BY_PX, 0));
    }
    public void onScrollUp(View view) {
    	_MapView.moveViewer(TGViewerUpdateFactory.scrollBy(0, -SCROLL_BY_PX));
    }
    public void onScrollDown(View view) {
    	_MapView.moveViewer(TGViewerUpdateFactory.scrollBy(0, SCROLL_BY_PX));
    }
    //----------------------------------------------

}
