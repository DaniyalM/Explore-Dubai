package com.app.dubaiculture.ui.components.customspinner;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.Timer;
import java.util.TimerTask;

public class CustomSearchableSpinner extends SearchableSpinner {
    private boolean isSpinnerDialogOpen = false;
    private Timer waitTimer;

    public CustomSearchableSpinner(Context context) {
        super(context);
    }

    public CustomSearchableSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSearchableSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIsSpinnerDialogOpen(Boolean value){
        this.isSpinnerDialogOpen = value;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (!isSpinnerDialogOpen) {
                setIsSpinnerDialogOpen(true);
                return super.onTouch(v, event);
            }
        }

        if(waitTimer != null){
            waitTimer.cancel();
        }

        temporizador();
        return true;
    }

    private void temporizador(){
        waitTimer = new Timer();
        waitTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                setIsSpinnerDialogOpen(false);
            }
        }, 200);
    }
}

