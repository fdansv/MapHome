package com.mapbox.MapHome;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.RelativeLayout;
import com.mapbox.mapboxsdk.MapView;
import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;

import java.util.Calendar;

public class MyActivity extends Activity {
    private IMapController mapController;
    private MapView mv;
    private RelativeLayout rl;

    String weekDay = "";
    private Typeface bold, boldItalic, light, lightItalic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        rl = (RelativeLayout) findViewById(R.id.parent);
        mv = (MapView) findViewById(R.id.mapview);
        System.out.println("rl "+mv);
        mv.setURL("http://a.tiles.mapbox.com/v3/fdansv.maphome/");
        mapController = mv.getController();
        mapController.setCenter(new GeoPoint(0f,0f));
        mapController.setZoom(3);
        setDayOfWeek();
        loadFonts();
    }

    private void setDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        weekDay = getWeekdayString(day);
    }

    private String getWeekdayString(int day) {
        switch (day){
            case 1: return "Monday";
            case 2: return "Tuesday";
            case 3: return "Wednesday";
            case 4: return "Thursday";
            case 5: return "Friday";
            case 6: return "Saturday";
            case 7: return "Sunday";
            default: return "";
        }
    }
    private void loadFonts(){
        bold = Typeface.createFromAsset(this.getAssets(), "OpenSansBold.ttf");
        boldItalic = Typeface.createFromAsset(this.getAssets(), "OpenSansBoldItalic.ttf");
        light = Typeface.createFromAsset(this.getAssets(), "OpenSansLight.ttf");
        lightItalic = Typeface.createFromAsset(this.getAssets(), "OpenSansLightItalic.ttf");
    }
}
