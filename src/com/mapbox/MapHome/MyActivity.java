package com.mapbox.MapHome;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mapbox.mapboxsdk.MapView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyActivity extends Activity {
    private IMapController mapController;
    private MapView mv;
    private RelativeLayout rl;

    private Date theDate;
    private Calendar calendar = Calendar.getInstance();
    private String weekDay = "";
    private Typeface bold, boldItalic, light, lightItalic;
    private TextView date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        rl = (RelativeLayout) findViewById(R.id.parent);
        mv = (MapView) findViewById(R.id.mapview);
        System.out.println("rl "+mv);
        mv.setURL("http://a.tiles.mapbox.com/v3/fdansv.maphome/");
        loadMapCamera();
        loadFonts();
        setDate();
        getNews();
    }

    private void getNews() {
        try {
            Document doc = Jsoup.connect("http://www.reuters.com/news/world").get();
            parseNewsRoot(doc);
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.notnews), Toast.LENGTH_SHORT).show();
        }
    }

    private void parseNewsRoot(Document doc) {

    }

    private void setDate() {
        setDayOfWeek();
        theDate = calendar.getTime();
        String month = new SimpleDateFormat("MMMM").format(theDate);
        String day = getDayOfMonthSuffix();
        String year = new SimpleDateFormat("yyyy").format(theDate);
        date = (TextView)findViewById(R.id.date);
        date.setTypeface(lightItalic);
        date.setText(month+" "+day+", "+year);
    }

    private void loadMapCamera() {mapController = mv.getController();
        mapController.setCenter(new GeoPoint(0f,0f));
        mapController.setZoom(3);
    }

    private void setDayOfWeek() {
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        weekDay = getWeekdayString(day);
        TextView weekDayView = (TextView) findViewById(R.id.weekday);
        weekDayView.setTypeface(bold);
        weekDayView.setText(weekDay);
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
    private String getDayOfMonthSuffix() {
        String[] suffixes =
                //    0     1     2     3     4     5     6     7     8     9
                { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                        //    10    11    12    13    14    15    16    17    18    19
                        "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                        //    20    21    22    23    24    25    26    27    28    29
                        "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                        //    30    31
                        "th", "st" };

        SimpleDateFormat formatDateOfMonth  = new SimpleDateFormat("d");
        int day = Integer.parseInt(formatDateOfMonth.format(theDate));
        return day + suffixes[day];

    }
}
