package com.app.dubaiculture.ui.components.customLinkMovementMethod;

import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

/*
*ClickableSpan makes whole line clickable including text and empty space. Use this to avoid calling onClick() empty space.
*/
public class CustomMovementMethod extends LinkMovementMethod {

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            if (off >= widget.getText().length()) {
                // Return true so click won't be triggered in the leftover empty space
                return true;
            }
        }

        return super.onTouchEvent(widget, buffer, event);
    }

    private static CustomMovementMethod mInstance;

    public static CustomMovementMethod getInstance() {
        if (mInstance == null)
            mInstance = new CustomMovementMethod();

        return mInstance;
    }
}
//https://stackoverflow.com/questions/9274331/clickablespan-strange-behavioronclick-called-when-clicking-empty-space