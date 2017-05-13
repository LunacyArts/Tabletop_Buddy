package lunacyarts.abgstudios.butany62.accessories.dndapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alien on 5/12/2017.
 */

public class PopupHelper {

    private List<EditText> fields; public EditText getField(int value) { return fields.get(value); }

    private Button accept;
    private Button cancel;

    private  LinearLayout buttonsLayout;

    private LinearLayout mainLayout;

    private PopupWindow window; public PopupWindow getPopupWindow() { return window; }

    public PopupHelper(Context context) {
        fields = new ArrayList<>();

        mainLayout = new LinearLayout(context);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        mainLayout.setBackgroundColor(Color.WHITE);

        buttonsLayout = new LinearLayout(context);
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonsLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        accept = createPopupButton("Submit", context); buttonsLayout.addView(accept);
        cancel = createPopupButton("Cancel", context); buttonsLayout.addView(cancel);

        buttonsLayout.findViewById(cancel.getId()).setOnClickListener(new Button.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                //Does nothing and dismisses the window.
                window.dismiss();
            }
        });

        mainLayout.addView(buttonsLayout);

        window = new PopupWindow(mainLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        window.setContentView(mainLayout);
    }

    private Button createPopupButton(String text, Context context) {
        Button popupButton = new Button(context);
        popupButton.setText(text);
        popupButton.setGravity(Gravity.CENTER_HORIZONTAL);
        popupButton.setId(View.generateViewId());
        return popupButton;
    }

    public void addPopupField(String Hint, int inputType, Context context) {
        final EditText Field = new EditText(context);
        Field.setHint(Hint);
        Field.setId(View.generateViewId());
        Field.setInputType(inputType);
        fields.add(Field);

        mainLayout.removeAllViews();
        for (EditText i : fields)
            mainLayout.addView(i);
        mainLayout.addView(buttonsLayout);
    }

    public void setOnClickListenerAccept(Button.OnClickListener listener) {
        buttonsLayout.findViewById(accept.getId()).setOnClickListener(listener);
    }

    public void showPopupAsDropDown(View anchor){
        window.showAsDropDown(anchor, 0, 0);
        window.setFocusable(true); window.update();
    }
}
