package edumacation.doublesum;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TitleScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "CutiveMono-Regular.ttf");
        TextView title = (TextView) this.findViewById(R.id.mainTitle);
        TextView twox = (TextView) this.findViewById(R.id.twox);
        Button play = (Button) this.findViewById(R.id.playButton);
        Button directions = (Button) this.findViewById(R.id.directionsButton);

        title.setTypeface(typeface);
        twox.setTypeface(typeface);
        play.setTypeface(typeface);
        directions.setTypeface(typeface);

    }

    public void transitionLevelSelector(View v)
    {
        Intent transition = new Intent(this, LevelSelectorScreen.class);
        this.startActivity(transition);
    }

    public void transitionDirections(View v)
    {
        Intent transition = new Intent(this, DirectionsScreen.class);
        this.startActivity(transition);
    }
}
