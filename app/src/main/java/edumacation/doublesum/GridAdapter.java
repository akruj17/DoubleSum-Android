package edumacation.doublesum;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Stack;

/**
 * Created by KUMUD on 12/28/2016.
 */

public class GridAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    GridContext mGridContext;
    int tileMatrix[][] = new int[5][5];
    int actualSum = 1;
    int level;
    int dimension = 5;
    boolean isValid;
    Stack<View> moves = new Stack();
    Stack<Integer> positions = new Stack();
    Typeface typeface;


    public static enum GridContext {
        LEVEL_SELECTOR,
        TILE_MATRIX
    }

    public GridAdapter(LayoutInflater inflater, int level, GridContext gridContext, Context context) {
        mInflater = inflater;
        mGridContext = gridContext;
        this.level = level;
        typeface = Typeface.createFromAsset(context.getAssets(), "CutiveMono-Regular.ttf");

    }

    public GridAdapter(LayoutInflater inflater, GridContext gridContext, Context context) {
        mInflater = inflater;
        mGridContext = gridContext;
        typeface = Typeface.createFromAsset(context.getAssets(), "CutiveMono-Regular.ttf");

    }


    @Override
    public int getCount() {
        if (mGridContext == GridContext.TILE_MATRIX) {
            return 25;
        }
        return 9;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mGridContext == GridContext.TILE_MATRIX) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.number_tile, parent, false);
                if (level==2 && position==12)
                {
                    convertView.setBackgroundResource(R.drawable.end_tile_border);
                }
                else if (level==7 && position==20)
                {
                    convertView.setBackgroundResource(R.drawable.end_tile_border);
                    ((LayerDrawable) convertView.getBackground().mutate()).setLayerInset(0,0,-6,0,0);

                }
                else if (level!=2 && level!=7 && position==24)
                {
                    convertView.setBackgroundResource(R.drawable.end_tile_border);
                }
                else
                {
                    convertView = mInflater.inflate(R.layout.number_tile, parent, false);
                }

                if (position == 0) {
                    TextView tile = (TextView) convertView.findViewById(R.id.numDisplay);
                    tile.setText(Integer.toString(1));
                    tile.setTypeface(typeface);

                    tileMatrix[0][0] = 1;
                }
            } else {
                if (position != 0 && (tileMatrix[position / dimension][position % dimension] == 0)) {
                    TextView tile = (TextView) convertView.findViewById(R.id.numDisplay);
                    tile.setTypeface(typeface);
                    int multiplier = calculateTileValue(position);
                    if (multiplier != 0) {
                        int value = 2 * multiplier;
                        tile.setText(Integer.toString(value));
                        tile.setTextSize(calculateFontSize(value, (int)tile.getTextSize()));
                        tileMatrix[position / dimension][position % dimension] = value;
                        actualSum += value;
                        moves.push(convertView);
                        positions.push(position);
                        isValid = true;
                    }
                }
                else {
                    isValid = false;
                }
            }
        } else {
          if (convertView == null) {
              convertView = mInflater.inflate(R.layout.level_tile, parent, false);
              TextView levelTile = (TextView) convertView.findViewById(R.id.levelNumber);
              levelTile.setText(Integer.toString(position + 1));
              levelTile.setTypeface(typeface);

              SharedPreferences myPrefs = parent.getContext().getSharedPreferences("LevelProgress", Context.MODE_PRIVATE);
              if(myPrefs.contains(Integer.toString(position+1)))
              {
                  ImageView levelCheck = (ImageView) convertView.findViewById(R.id.levelCompleted);
                  levelCheck.setVisibility(View.VISIBLE);
              }
          }
        }

        return convertView;
    }

    public int calculateTileValue(int position)
    {
        int row = position/dimension;
        int col = position%dimension;
        int largestMultiplier = 0;

        if(col != 0)
        {
            largestMultiplier = tileMatrix[row][col-1];
        }
        if(col != (dimension-1))
        {
            largestMultiplier = Math.max(largestMultiplier, tileMatrix[row][col+1]);
        }
        if(row != 0)
        {
            largestMultiplier = Math.max(largestMultiplier, tileMatrix[row-1][col]);
        }
        if(row != (dimension-1))
        {
            largestMultiplier = Math.max(largestMultiplier, tileMatrix[row+1][col]);
        }

        return largestMultiplier;
    }

    public int calculateFontSize(int value, int initialSize){
        int numDigits = (int) Math.floor(Math.log10(value));
        int size = (int) initialSize;

        if(numDigits < 2)
        {
            size = 45 + (initialSize-40);
        }
        if(numDigits == 2)
        {
            size = 35 + (initialSize-40);
        }
        if (numDigits == 3)
        {
            size = 25 + (initialSize-40);
        }
        if(numDigits == 4)
        {
            size = 20 + (initialSize-50);
        }
        if (numDigits == 5)
        {
            size = 16 + (initialSize-50);
        }
        if (numDigits == 6)
        {
            size = 14 + (initialSize-50);
        }
        if (numDigits == 7)
        {
            size = 12 + (initialSize-50);
        }

        return size;
    }

    public int getScore()
    {
        return actualSum;
    }

    public void undoMove()
    {
        if(moves.size() > 0)
        {
            View v = moves.pop();
            int lastPosition = positions.pop();
            TextView lastTile = (TextView) v.findViewById(R.id.numDisplay);

            int value = Integer.parseInt(lastTile.getText().toString());
            actualSum -= value;
            lastTile.setText("");

            tileMatrix[lastPosition/dimension][lastPosition%dimension] = 0;

        }
    }

    public boolean isValidMove()
    {
        return isValid;
    }
}
