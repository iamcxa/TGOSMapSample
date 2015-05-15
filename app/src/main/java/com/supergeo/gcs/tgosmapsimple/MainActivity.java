package com.supergeo.gcs.tgosmapsimple;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.supergeo.gcs.view.FeatureView;

public class MainActivity extends ListActivity {

	private static class DemoDetails {
        /**
         * The resource id of the title of the demo.
         */
        private final int titleId;

        /**
         * The resources id of the description of the demo.
         */
        private final int descriptionId;

        /**
         * The demo activity's class.
         */
        private final Class<? extends android.support.v4.app.FragmentActivity> activityClass;

        public DemoDetails(int titleId, int descriptionId,
                Class activityClass) {
            super();
            this.titleId = titleId;
            this.descriptionId = descriptionId;
            this.activityClass = activityClass;
        }
    }

    private static class CustomArrayAdapter extends ArrayAdapter<DemoDetails> {

        /**
         * @param demos An array containing the details of the demos to be displayed.
         */
        public CustomArrayAdapter(Context context, DemoDetails[] demos) {
            super(context, R.layout.feature, R.id.title, demos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FeatureView featureView;
            if (convertView instanceof FeatureView) {
                featureView = (FeatureView) convertView;
            } else {
                featureView = new FeatureView(getContext());
            }

            DemoDetails demo = getItem(position);

            featureView.setTitleId(demo.titleId);
            featureView.setDescriptionId(demo.descriptionId);

            return featureView;
        }
    }

    private static final DemoDetails[] demos = {
		new DemoDetails(
	            R.string.base_map_demo,  R.string.BaseMap_Demo_description, BaseMap_Demo.class),
		new DemoDetails(
				R.string.viewer_update_demo,  R.string.viewer_update_demo_description, ViewerUpdateDemo.class),
		new DemoDetails(
				R.string.event_demo,  R.string.event_demo_description, EventDemo.class),
		new DemoDetails(
				R.string.UIsetting,  R.string.UIsetting_description, UISetting_Demo.class),
		new DemoDetails(
				R.string.polyline_demo,  R.string.polyline_demo_description, Polyline_Demo.class),
		new DemoDetails(
				R.string.circle_demo,  R.string.circle_demo_description, CircleDemo.class),
		new DemoDetails(
				R.string.polygon_demo,  R.string.polygon_demo_description, Polygon_Demo.class),
		new DemoDetails(
				R.string.marker_demo,  R.string.marker_demo_description, Marker_Demo.class),
		new DemoDetails(
				R.string.map_type_demo,  R.string.map_type_demo_description, MapTypeDemo.class),
		new DemoDetails(
				R.string.ground_demo,  R.string.ground_demo_description, GroundDemo.class),
		new DemoDetails(
				R.string.ardemo,  R.string.ardemo_description, ARDemo.class)
		};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "1onCreate");
		setContentView(R.layout.activity_main);
		ListAdapter adapter = new CustomArrayAdapter(this, demos);

        setListAdapter(adapter);
	}
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        DemoDetails demo = (DemoDetails) getListAdapter().getItem(position);
        Intent intent = new Intent(this, demo.activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        this.startActivity(intent);
    }
    String TAG = "1";
    public void onStart(){
    	super.onStart();
    	Log.v(TAG, "1onStart");
    }
    public void onResume(){
        super.onResume(); 	
        Log.v(TAG, "1onResume");  
    }
    public void onPause(){
    	super.onPause();
    	Log.v(TAG,"1onPause");
    }
    public void onStop(){
    	super.onStop();
    	Log.v(TAG,"1onStop");
    }
    public void onDestroy(){
    	super.onDestroy();
    	Log.v(TAG,"1onDestroy");
    }


}
