package edumacation.doublesum;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class DirectionsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions_screen);

        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "CutiveMono-Regular.ttf");
        TextView directionsFirst = (TextView) this.findViewById(R.id.directions_first);
        TextView directionsSecond = (TextView) this.findViewById(R.id.directions_second);
        TextView directionsLast = (TextView) this.findViewById(R.id.directions_last);

        directionsFirst.setTypeface(typeface);
        directionsSecond.setTypeface(typeface);
        directionsLast.setTypeface(typeface);
    }
}
