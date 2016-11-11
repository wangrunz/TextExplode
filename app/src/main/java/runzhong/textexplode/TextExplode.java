package runzhong.textexplode;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;

public class TextExplode extends AppCompatActivity implements GestureDetector.OnGestureListener{

    @Override
    public void onLongPress(MotionEvent e) {

        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple text",selectedText.getText());
        clipboardManager.setPrimaryClip(clip);
        Toast.makeText(this,"["+selectedText.getText()+"] copy to clipboard", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
    private GestureDetector mDetector;
    private TextView selectedText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_explode);
        mDetector = new GestureDetector(this,this);
        CharSequence text = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        String input = (String)text;
        final GridLayout gridLayout = (GridLayout)findViewById(R.id.text_grid);
        selectedText = (TextView)findViewById(R.id.textView2);
        selectedText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mDetector.onTouchEvent(event);
            }
        });


        String[] terms = input.split("\\W+");
        for (String item:terms) {
            View v = LayoutInflater.from(this).inflate(R.layout.item,null,false);
            final TextView tv = (TextView) v.findViewById(R.id.textView);
            final CardView cv = (CardView)v.findViewById(R.id.cardView);
            tv.setText(item);
            v.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED),GridLayout.spec(GridLayout.UNDEFINED,1f)));
            tv.setClickable(true);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cv.getAlpha()==1){
                        cv.setAlpha((float)0.5);
                    }
                    else{
                        cv.setAlpha(1);
                    }
                    int count = gridLayout.getChildCount();
                    String selectText="";
                    for(int i = 0 ; i <count ; i++){
                        View child = gridLayout.getChildAt(i);
                        CardView card = (CardView)child.findViewById(R.id.cardView);
                        if (card.getAlpha()==0.5){
                            String text = (String)((TextView)card.findViewById(R.id.textView)).getText();
                            selectText+=text+" ";
                        }
                    }
                    selectedText.setText(selectText);
                }
            });
            gridLayout.addView(v);
        }
    }
}
