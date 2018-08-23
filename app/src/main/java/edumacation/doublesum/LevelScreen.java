package edumacation.doublesum;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

public class LevelScreen extends AppCompatActivity {

    GridAdapter adapter;
    TextView actualScore;
    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_screen);
        int levelScores[] = this.getResources().getIntArray(R.array.level_scores);

        final int levelNumber = getIntent().getIntExtra("levelNumber", 1);
        setTitle("Level " + Integer.toString(levelNumber));

        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "CutiveMono-Regular.ttf");
        TextView sumTitle = (TextView) this.findViewById(R.id.sumText);
        TextView requiredText = (TextView) this.findViewById(R.id.requiredLabel);
        TextView actualText = (TextView) this.findViewById(R.id.actualLabel);

        sumTitle.setTypeface(typeface);
        requiredText.setTypeface(typeface);
        actualText.setTypeface(typeface);


        GridView table = (GridView) findViewById(R.id.grid);
        actualScore = (TextView) findViewById(R.id.actualScore);
        final TextView requiredScore = (TextView) findViewById(R.id.requiredScore);
        requiredScore.setText(Integer.toString(levelScores[levelNumber-1]));
        actualScore.setTypeface(typeface);
        requiredScore.setTypeface(typeface);



        adapter = new GridAdapter(getLayoutInflater(), levelNumber, GridAdapter.GridContext.TILE_MATRIX, this);

        table.setAdapter(adapter);
        table.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parent.getAdapter().getView(position, view, parent);
                actualScore.setText(Integer.toString(((GridAdapter)parent.getAdapter()).getScore()));

                if(adapter.isValidMove()) {
                    if ((levelNumber != 2 && levelNumber != 7 && position == 24) || (levelNumber == 2 && position == 12) || (levelNumber == 7 && position == 20)) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                        int actScore = Integer.parseInt(actualScore.getText().toString());
                        int reqScore = Integer.parseInt(requiredScore.getText().toString());

                        if(actScore == reqScore) {
                            builder.setTitle("Congrats!!").setMessage("You beat this level");

                            if (levelNumber != 9) {
                                builder.setPositiveButton("Next Level", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(c, LevelScreen.class);
                                        intent.putExtra("levelNumber", levelNumber + 1);
                                        c.startActivity(intent);
                                    }
                                });
                            } else {
                                builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(c, LevelScreen.class);
                                        intent.putExtra("levelNumber", levelNumber);
                                        c.startActivity(intent);
                                    }
                                });
                            }

                            SharedPreferences myPrefs = getSharedPreferences("LevelProgress", Context.MODE_PRIVATE);
                            if (!myPrefs.contains(Integer.toString(levelNumber))) {
                                SharedPreferences.Editor editor = myPrefs.edit();
                                editor.putBoolean(Integer.toString(levelNumber), true);
                                editor.commit();
                            }

                        }
                        else {
                            builder.setTitle("Sorry").setMessage("You reached the end of the level without the required score");

                            builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(c, LevelScreen.class);
                                    intent.putExtra("levelNumber", levelNumber);
                                    c.startActivity(intent);
                                }
                            });
                        }

                        builder.setNegativeButton("Level Selector", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(c, LevelSelectorScreen.class);
                                c.startActivity(intent);
                            }
                        });

                        builder.create().show();
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_level_screen, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.undo_button) {
            adapter.undoMove();
            actualScore.setText(Integer.toString(adapter.getScore()));

        }

        return super.onOptionsItemSelected(item);
    }
}
