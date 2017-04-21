package lunacyarts.abgstudios.butany62.accessories.dndapp;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.PopupMenu;
import android.text.InputType;
import android.text.method.TransformationMethod;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class InitiativeTrackerActivity extends AppCompatActivity{

    //An int the Options Menu can use to identify when one hits the delete button.
    private static final int MENU_ITEM_DELETE = R.id.delete;
    //An int the Options Menu can use to identify when one hits the rearrange button.
    private static final int MENU_ITEM_REARRANGE = R.id.rearrange;
    //The layout of the popup that adds in an initiative tray.
    LinearLayout addInitPopupLayout;
    //The popup that adds in an initiative tray.
    PopupWindow addInitPopup;
    //The layout of the popup that changes the selected initiative tray.
    LinearLayout changeInitPopupLayout;
    //The popup that changes the selected initiative tray.
    PopupWindow changeInitPopup;
    //A variable that represents the circular '+' button.
    FloatingActionButton addInit;
    //A variable that represents the circular '>>' button.
    FloatingActionButton nextInit;
    //The accept button on the Add Initiative Popup.
    Button addInitAcceptButton;
    //The cancel button on the Add Initiative Popup.
    Button addInitCancelButton;
    //The accept button on the Change Initiative Popup.
    Button changeInitAcceptButton;
    //The cancel button on the Change Initiative Popup.
    Button changeInitCancelButton;
    //A sorted list of the initiative trays.
    SortedMap<String, InitTrays> initOrder;
    //A variable that represents the main Table the initiative trays go into.
    TableLayout initTrays;
    //A variable that represents the title tray at the top of the table.
    TableRow initTopTray;

    //When the activity is created, this code runs.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Auto generated. Not sure what it does.
        super.onCreate(savedInstanceState);
        //Sets the View to be shown.
        setContentView(R.layout.activity_initiative_tracker);
        //Auto generated.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Defining the sorted list to have the highest number appear first and the lowest number appear last.
        initOrder = new TreeMap(Collections.reverseOrder());

        //Finds the Initiatives TableLayout and defines that to the variable.
        initTrays = (TableLayout) findViewById(R.id.Initiatives);
        //Finds the Top Row TableRow and defines that to the variable.
        initTopTray = (TableRow) findViewById(R.id.tableTopRow);
        //Finds the Add Init FloatingActionButton and defines that to the variable.
        addInit = (FloatingActionButton) findViewById(R.id.addInit);
        //Finds the Next Init FloatingActionButton and defines that to the variable.
        nextInit = (FloatingActionButton) findViewById(R.id.nextInit);

        //Creates the Popup Layouts.
        addInitPopupLayout = createPopupMenuLayout(LinearLayout.VERTICAL, Gravity.CENTER_HORIZONTAL, Color.WHITE);
        changeInitPopupLayout = createPopupMenuLayout(LinearLayout.VERTICAL, Gravity.CENTER_HORIZONTAL, Color.WHITE);

        //Creates the Popups.
        addInitPopup = new PopupWindow(addInitPopupLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        changeInitPopup = new PopupWindow(changeInitPopupLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //Sets the content to be viewed as the Popup Layouts previously defined.
        addInitPopup.setContentView(addInitPopupLayout);
        changeInitPopup.setContentView(changeInitPopupLayout);

        //Creates the fields to type in for the Add Init Popup and adds them to the Layout.
        final EditText addInitInitField = createPopupField("Init", InputType.TYPE_CLASS_NUMBER); addInitPopupLayout.addView(addInitInitField);
        final EditText addInitNameField = createPopupField("Player Name", InputType.TYPE_CLASS_TEXT); addInitPopupLayout.addView(addInitNameField);
        final EditText addInitAcField = createPopupField("AC", InputType.TYPE_CLASS_NUMBER); addInitPopupLayout.addView(addInitAcField);
        final EditText addInitDexField = createPopupField("Dex Mod", InputType.TYPE_CLASS_NUMBER); addInitPopupLayout.addView(addInitDexField);
        final EditText addInitFortField = createPopupField("Fort", InputType.TYPE_CLASS_NUMBER); addInitPopupLayout.addView(addInitFortField);
        final EditText addInitReflexField = createPopupField("Reflex", InputType.TYPE_CLASS_NUMBER); addInitPopupLayout.addView(addInitReflexField);
        final EditText addInitWillField = createPopupField("Will", InputType.TYPE_CLASS_NUMBER); addInitPopupLayout.addView(addInitWillField);

        //Creates the fields to type in for the Change Init Popup and adds them to the Layout.
        final EditText changeInitInitField = createPopupField("Init (leave blank to unchange)", InputType.TYPE_CLASS_NUMBER);
        changeInitPopupLayout.addView(changeInitInitField);
        final EditText changeInitPlaceField = createPopupField("Place (50 default; leave blank for deault)", InputType.TYPE_CLASS_NUMBER);
        changeInitPopupLayout.addView(changeInitPlaceField);


        LinearLayout buttonsLayout = new LinearLayout(this);
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonsLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        addInitAcceptButton = new Button(this);
        addInitAcceptButton.setText("Submit");
        addInitAcceptButton.setGravity(Gravity.CENTER_HORIZONTAL);
        addInitAcceptButton.setId(View.generateViewId());
        buttonsLayout.addView(addInitAcceptButton);
        addInitCancelButton = new Button(this);
        addInitCancelButton.setText("Cancel");
        addInitCancelButton.setGravity(Gravity.CENTER_HORIZONTAL);
        addInitCancelButton.setId(View.generateViewId());
        buttonsLayout.addView(addInitCancelButton);
        addInitPopupLayout.addView(buttonsLayout);

        LinearLayout buttonsLayout2 = new LinearLayout(this);
        buttonsLayout2.setOrientation(LinearLayout.HORIZONTAL);
        buttonsLayout2.setGravity(Gravity.CENTER_HORIZONTAL);
        changeInitAcceptButton = new Button(this);
        changeInitAcceptButton.setText("Submit");
        changeInitAcceptButton.setGravity(Gravity.CENTER_HORIZONTAL);
        changeInitAcceptButton.setId(View.generateViewId());
        buttonsLayout2.addView(changeInitAcceptButton);
        changeInitCancelButton = new Button(this);
        changeInitCancelButton.setText("Cancel");
        changeInitCancelButton.setGravity(Gravity.CENTER_HORIZONTAL);
        changeInitCancelButton.setId(View.generateViewId());
        buttonsLayout2.addView(changeInitCancelButton);
        changeInitPopupLayout.addView(buttonsLayout2);

        addInit.setOnClickListener(new Button.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                addInitPopup.showAsDropDown(addInit, 0, 0);
                addInitPopup.setFocusable(true); addInitPopup.update();
            }
        });
        addInitAcceptButton.setOnClickListener(new Button.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                int initText = 0;
                String nameText = "";
                int acText = 0;
                int dexText = 0;
                int save1Text = 0;
                int save2Text = 0;
                int save3Text = 0;

                if (!addInitInitField.getText().toString().matches("")) {
                    initText = Integer.parseInt(addInitInitField.getText().toString());
                }
                if (!addInitNameField.getText().toString().matches("")) {
                    nameText = addInitNameField.getText().toString();
                }
                if (!addInitAcField.getText().toString().matches("")) {
                    acText = Integer.parseInt(addInitAcField.getText().toString());
                }
                if (!addInitDexField.getText().toString().matches("")) {
                    dexText = Integer.parseInt(addInitDexField.getText().toString());
                }
                if (!addInitFortField.getText().toString().matches("")) {
                    save1Text = Integer.parseInt(addInitFortField.getText().toString());
                }
                if (!addInitReflexField.getText().toString().matches("")) {
                    save2Text = Integer.parseInt(addInitReflexField.getText().toString());
                }
                if (!addInitWillField.getText().toString().matches("")) {
                    save3Text = Integer.parseInt(addInitWillField.getText().toString());
                }

                InitTrays temp = new InitTrays(initText, nameText, acText, dexText, save1Text, save2Text, save3Text);

                String init0 = "";
                if (temp.getInitiative() < 10) {
                    init0 = "0";
                }
                String dex0 = "";
                if (temp.getDex() < 10) {
                    dex0 = "0";
                }
                String place0 = "";
                if (temp.getPlace() < 10) {
                    place0 = "0";
                }
                String tempNum = init0 + String.valueOf(temp.getInitiative()) + place0 + String.valueOf(temp.getPlace()) + dex0 + String.valueOf(temp.getDex());

                if (!initOrder.containsKey(tempNum)) {
                    initOrder.put(tempNum, temp);
                    if (initOrder.size() == 1)
                        initOrder.get(initOrder.firstKey()).setSelected(true);
                    refreshTrays();
                    addInitInitField.setText("");
                    addInitNameField.setText("");
                    addInitAcField.setText("");
                    addInitDexField.setText("");
                    addInitFortField.setText("");
                    addInitReflexField.setText("");
                    addInitWillField.setText("");
                    addInitPopup.dismiss();
                }

            }
        });
        addInitCancelButton.setOnClickListener(new Button.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                addInitPopup.dismiss();
            }
        });
        nextInit.setOnClickListener(new Button.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                nextInit();
            }
        });
        changeInitAcceptButton.setOnClickListener(new Button.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                for (String i : initOrder.keySet()) {
                    if (initOrder.get(i).isSelected()) {
                        if (changeInitInitField.getText().toString().matches("")) {
                        } else {
                            initOrder.get(i).setInitiative(Integer.parseInt(changeInitInitField.getText().toString()));
                        }
                        if (changeInitPlaceField.getText().toString().matches("")) {
                        } else {
                            initOrder.get(i).setPlace(Integer.parseInt(changeInitPlaceField.getText().toString()));
                        }
                        String init0 = "";
                        if (initOrder.get(i).getInitiative() < 10) { init0 = "0"; }
                        String dex0 = "";
                        if (initOrder.get(i).getDex() < 10) { dex0 = "0"; }
                        String place0 = "";
                        if (initOrder.get(i).getPlace() < 10) { place0 = "0"; }
                        String tempNum = init0 + String.valueOf(initOrder.get(i).getInitiative()) + place0 + String.valueOf(initOrder.get(i).getPlace()) + dex0 + String.valueOf(initOrder.get(i).getDex());
                        InitTrays temp = initOrder.get(i);
                        initOrder.remove(i);
                        initOrder.put(tempNum, temp);
                        break;
                    }
                }
                refreshTrays();
                changeInitPopup.dismiss();
            }
        });
        changeInitCancelButton.setOnClickListener(new Button.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                changeInitPopup.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ITEM_DELETE:
                deleteSelected();
                return true;
            case MENU_ITEM_REARRANGE:
                editInit();

            default:
                return false;
        }
    }

    private LinearLayout createPopupMenuLayout(int orientation, int gravity, int bgColor) {
        LinearLayout PopupLayout = new LinearLayout(this);
        PopupLayout.setOrientation(orientation);
        PopupLayout.setGravity(gravity);
        PopupLayout.setBackgroundColor(bgColor);
        return PopupLayout;
    }

    private EditText createPopupField(String Hint, int inputType) {
        final EditText Field = new EditText(this);
        Field.setHint(Hint);
        Field.setId(View.generateViewId());
        Field.setInputType(inputType);
        return Field;
    }

    private void editInit() {
        changeInitPopup.showAsDropDown(initTrays, 0, 0);
        changeInitPopup.setFocusable(true); changeInitPopup.update();
    }

    private void deleteSelected() {
        Set<String> iter = initOrder.keySet();
        for (String i : iter) {
            if (initOrder.get(i).isSelected())
                initOrder.remove(i);
        }
        if (initOrder.keySet().size() > 0) { initOrder.get(initOrder.firstKey()).setSelected(true); }
        refreshTrays();
    }

    public void refreshTrays(){
        initTrays.removeAllViews();
        initTrays.addView(initTopTray);
        for (final String i : initOrder.keySet()){

            TableRow initTray = new TableRow(this);
            TextView initView = new TextView(this);
            TextView nameView = new TextView(this);
            TextView acView = new TextView(this);
            TextView savesView = new TextView(this);

            initTray.setClickable(true);
            initTray.setOnClickListener(new Button.OnClickListener()

            {
                @Override
                public void onClick(View v) {
                    for (String j : initOrder.keySet())
                        if (initOrder.get(j).isSelected())
                            initOrder.get(j).setSelected(false);

                    if (initOrder.get(i).isSelected()) { initOrder.get(i).setSelected(false); }
                    else { initOrder.get(i).setSelected(true); }

                    refreshTrays();
                }
            });

            @DrawableRes int bg = R.drawable.drawable_cell_shape;
            if (initOrder.get(i).isSelected())
                bg = R.drawable.drawable_cell_shape_darkened;

            initView.setText(String.valueOf(initOrder.get(i).getInitiative()));
            initView.setTextSize(24);
            initView.setGravity(Gravity.START);
            initView.setPadding(5, 0, 5, 0);
            initView.setBackgroundResource(bg);

            nameView.setText(initOrder.get(i).getName());
            nameView.setTextSize(24);
            nameView.setGravity(Gravity.START | Gravity.CENTER_HORIZONTAL);
            nameView.setPadding(5, 0, 5, 0);
            nameView.setBackgroundResource(bg);

            acView.setText(String.valueOf(initOrder.get(i).getAC()));
            acView.setTextSize(24);
            acView.setGravity(Gravity.START);
            acView.setPadding(5, 0, 5, 0);
            acView.setBackgroundResource(bg);

            savesView.setText(initOrder.get(i).getSaves());
            savesView.setTextSize(24);
            savesView.setGravity(Gravity.START);
            savesView.setPadding(5, 0, 5, 0);
            savesView.setBackgroundResource(bg);

            initTray.addView(initView);
            initTray.addView(nameView);
            initTray.addView(acView);
            initTray.addView(savesView);

            initTrays.addView(initTray);
        }
    }

    public void nextInit() {
        boolean changeSelected = false;
        for (String i : initOrder.keySet()) {
            if (!changeSelected) {
                if (initOrder.get(i).isSelected()) {
                    changeSelected = true;
                    initOrder.get(i).setSelected(false);
                }
            }
            else {
                initOrder.get(i).setSelected(true);
                changeSelected = false;
            }
        }
        if (changeSelected) {
            initOrder.get(initOrder.firstKey()).setSelected(true);
        }
        refreshTrays();
    }
}
