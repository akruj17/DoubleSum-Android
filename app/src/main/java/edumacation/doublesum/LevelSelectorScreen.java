package edumacation.doublesum;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

public class LevelSelectorScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector_screen);

        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "CutiveMono-Regular.ttf");
        TextView levelSelectorTitle = (TextView) this.findViewById(R.id.levelSelector_title);
        levelSelectorTitle.setTypeface(typeface);

        GridView levelGrid = (GridView) findViewById(R.id.levelGrid);
        levelGrid.setAdapter(new GridAdapter(getLayoutInflater(), GridAdapter.GridContext.LEVEL_SELECTOR, this));
        levelGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent transition = new Intent(parent.getContext(), LevelScreen.class);
                transition.putExtra("levelNumber", position + 1);
                parent.getContext().startActivity(transition);
            }
        });
    }
}
