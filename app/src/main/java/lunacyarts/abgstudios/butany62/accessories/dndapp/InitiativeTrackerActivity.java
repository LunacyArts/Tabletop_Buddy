package lunacyarts.abgstudios.butany62.accessories.dndapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Collections;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class InitiativeTrackerActivity extends AppCompatActivity{

    //region Initializing Variables
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
    //A variables for the IDs of the Popup buttons.
    @IdRes int addInitAcceptButtonID;
    @IdRes int addInitCancelButtonID;
    @IdRes int changeInitAcceptButtonID;
    @IdRes int changeInitCancelButtonID;
    //endregion

    //region When the activity is created, this code runs.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //region Auto generated. Not sure what it does.
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //endregion

        //Sets the View to be shown.
        setContentView(R.layout.activity_initiative_tracker);

        //Defining the sorted list to have the highest number appear first and the lowest number appear last.
        initOrder = new TreeMap<>(Collections.reverseOrder());

        //Finds the Initiatives TableLayout and defines that to the variable.
        initTrays = (TableLayout) findViewById(R.id.Initiatives);
        //Finds the Top Row TableRow and defines that to the variable.
        initTopTray = (TableRow) findViewById(R.id.tableTopRow);
        //Finds the Add Init FloatingActionButton and defines that to the variable.
        addInit = (FloatingActionButton) findViewById(R.id.addInit);
        //Finds the Next Init FloatingActionButton and defines that to the variable.
        nextInit = (FloatingActionButton) findViewById(R.id.nextInit);

        //region Creates the Popup Layouts.
        addInitPopupLayout = createPopupMenuLayout();
        changeInitPopupLayout = createPopupMenuLayout();
        //endregion

        //region Creates the Popups.
        addInitPopup = new PopupWindow(addInitPopupLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        changeInitPopup = new PopupWindow(changeInitPopupLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //endregion

        //region Sets the content to be viewed as the Popup Layouts previously defined.
        addInitPopup.setContentView(addInitPopupLayout);
        changeInitPopup.setContentView(changeInitPopupLayout);
        //endregion

        //region Defines the IDs by generating a new one.
        addInitAcceptButtonID = View.generateViewId();
        addInitCancelButtonID = View.generateViewId();
        changeInitAcceptButtonID = View.generateViewId();
        changeInitCancelButtonID = View.generateViewId();
        //endregion

        //region Creates the fields to type in for the Add Init Popup and adds them to the Layout.
        final EditText addInitInitField = createPopupField("Init", InputType.TYPE_CLASS_NUMBER); addInitPopupLayout.addView(addInitInitField);
        final EditText addInitNameField = createPopupField("Player Name", InputType.TYPE_CLASS_TEXT); addInitPopupLayout.addView(addInitNameField);
        final EditText addInitAcField = createPopupField("AC", InputType.TYPE_CLASS_NUMBER); addInitPopupLayout.addView(addInitAcField);
        final EditText addInitDexField = createPopupField("Dex Mod", InputType.TYPE_CLASS_NUMBER); addInitPopupLayout.addView(addInitDexField);
        final EditText addInitFortField = createPopupField("Fort", InputType.TYPE_CLASS_NUMBER); addInitPopupLayout.addView(addInitFortField);
        final EditText addInitReflexField = createPopupField("Reflex", InputType.TYPE_CLASS_NUMBER); addInitPopupLayout.addView(addInitReflexField);
        final EditText addInitWillField = createPopupField("Will", InputType.TYPE_CLASS_NUMBER); addInitPopupLayout.addView(addInitWillField);
        //endregion

        //region Creates the fields to type in for the Change Init Popup and adds them to the Layout.
        final EditText changeInitInitField = createPopupField("Init (leave blank to unchange)", InputType.TYPE_CLASS_NUMBER);
        changeInitPopupLayout.addView(changeInitInitField);
        final EditText changeInitPlaceField = createPopupField("Place (50 default; leave blank for deault)", InputType.TYPE_CLASS_NUMBER);
        changeInitPopupLayout.addView(changeInitPlaceField);
        //endregion

        //region Creates the layout for the buttons area and adds it to the Popup Layouts.
        LinearLayout addInitButtonsLayout = createPopupButtonsLayout(addInitAcceptButtonID, addInitCancelButtonID);
        addInitPopupLayout.addView(addInitButtonsLayout);
        LinearLayout buttonsLayout2 = createPopupButtonsLayout(changeInitAcceptButtonID, changeInitCancelButtonID);
        changeInitPopupLayout.addView(buttonsLayout2);
        //endregion

        //region Sets what the addInit Button does when pressed.

        addInit.setOnClickListener(new Button.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                //Brings up the AddInit Popup.
                addInitPopup.showAsDropDown(addInit, 0, 0);
                addInitPopup.setFocusable(true); addInitPopup.update();
            }
        });
        //endregion

        //region Finds the addInitAcceptButton by its ID and sets what it will do when pressed.
        findViewById(addInitAcceptButtonID).setOnClickListener(new Button.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                //Initiates the variables needed.
                int initText = 0;
                String nameText = "";
                int acText = 0;
                int dexText = 0;
                int save1Text = 0;
                int save2Text = 0;
                int save3Text = 0;

                //Defines the variables, but only if the field isn't blank. (Crash prevention.)
                if (!addInitInitField.getText().toString().matches("")) { initText = Integer.parseInt(addInitInitField.getText().toString()); }
                if (!addInitNameField.getText().toString().matches("")) { nameText = addInitNameField.getText().toString(); }
                if (!addInitAcField.getText().toString().matches("")) { acText = Integer.parseInt(addInitAcField.getText().toString()); }
                if (!addInitDexField.getText().toString().matches("")) { dexText = Integer.parseInt(addInitDexField.getText().toString()); }
                if (!addInitFortField.getText().toString().matches("")) { save1Text = Integer.parseInt(addInitFortField.getText().toString()); }
                if (!addInitReflexField.getText().toString().matches("")) { save2Text = Integer.parseInt(addInitReflexField.getText().toString()); }
                if (!addInitWillField.getText().toString().matches("")) { save3Text = Integer.parseInt(addInitWillField.getText().toString()); }

                //Creates a temporary InitTrays that includes the variables previously defined.
                InitTrays temp = new InitTrays(initText, nameText, acText, dexText, save1Text, save2Text, save3Text);

                //Error prevention. If one of these variables is below 10, it adds in a '0' before it so it doesnt go before other variables.
                //Example: 8 would go before 63, so add a '0' to it and now 63 goes before 08.
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

                //String to identify what initiative order this person goes in.
                //Example: Init:13 Place:50 Dex:4 = 135004 <- init number to be sorted by.
                String tempNum = init0 + String.valueOf(temp.getInitiative()) + place0 + String.valueOf(temp.getPlace()) + dex0 + String.valueOf(temp.getDex());

                //Only puts the person there if there is not already another person in that initiative spot.
                if (!initOrder.containsKey(tempNum)) {
                    //Places the person at that initiative order with all of their stats.
                    initOrder.put(tempNum, temp);
                    //If they are the only person in the initiative order, the program sets it as selected.
                    if (initOrder.size() == 1)
                        initOrder.get(initOrder.firstKey()).setSelected(true);
                    //Refreshes the visuals.
                    refreshTrays();
                    //Resets the fields back to blank.
                    addInitInitField.setText("");
                    addInitNameField.setText("");
                    addInitAcField.setText("");
                    addInitDexField.setText("");
                    addInitFortField.setText("");
                    addInitReflexField.setText("");
                    addInitWillField.setText("");
                    //Ends the Popup.
                    addInitPopup.dismiss();
                }

            }
        }); //endregion

        //region Sets what the addInitCancelButton does when pressed.
        findViewById(addInitCancelButtonID).setOnClickListener(new Button.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                //Does nothing and dismisses the window.
                addInitPopup.dismiss();
            }
        });//endregion

        //Sets what the nextInit Button does when pressed and references
        nextInit.setOnClickListener(new Button.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                nextInit();
            }
        });

        //region Sets what the changeInitAcceptButton does when pressed.
        findViewById(changeInitAcceptButtonID).setOnClickListener(new Button.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                //Gets each of the InitTrays in initOrder.
                for (String i : initOrder.keySet()) {
                    InitTrays currentInit = initOrder.get(i);
                    //Finds the one that is selected.
                    if (currentInit.isSelected()) {
                        //Defines the variables, but only if the field isn't blank. (Crash prevention.)
                        if (!changeInitInitField.getText().toString().matches("")) {
                            currentInit.setInitiative(Integer.parseInt(changeInitInitField.getText().toString())); }
                        if (!changeInitPlaceField.getText().toString().matches("")) {
                            currentInit.setPlace(Integer.parseInt(changeInitPlaceField.getText().toString())); }

                        //Error prevention. If one of these variables is below 10, it adds in a '0' before it so it doesnt go before other variables.
                        String init0 = "";
                        if (currentInit.getInitiative() < 10) { init0 = "0"; }
                        String dex0 = "";
                        if (currentInit.getDex() < 10) { dex0 = "0"; }
                        String place0 = "";
                        if (currentInit.getPlace() < 10) { place0 = "0"; }
                        String tempNum = init0 + String.valueOf(currentInit.getInitiative()) + place0 + String.valueOf(currentInit.getPlace())
                                + dex0 + String.valueOf(currentInit.getDex());
                        //Removes the InitTrays from the list to be added back in at the right number.
                        initOrder.remove(i);
                        initOrder.put(tempNum, currentInit);
                        break;
                    }
                }
                refreshTrays();
                changeInitPopup.dismiss();
            }
        });//endregion

        //region Sets what the changeInitCancelButton does when pressed.
        findViewById(changeInitCancelButtonID).setOnClickListener(new Button.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                changeInitPopup.dismiss();
            }
        });//endregion
    }
    //endregion

    //region
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

    private Button createPopupButton(String text, @IdRes int id) {
        Button popupButton = new Button(this);
        popupButton.setText(text);
        popupButton.setGravity(Gravity.CENTER_HORIZONTAL);
        popupButton.setId(id);
        return popupButton;
    }

    private LinearLayout createPopupButtonsLayout(@IdRes int acceptButtonID, @IdRes int cancelButtonID) {
        LinearLayout buttonsTray = new LinearLayout(this);
        buttonsTray.setOrientation(LinearLayout.HORIZONTAL);
        buttonsTray.setGravity(Gravity.CENTER_HORIZONTAL);
        Button acceptButton = createPopupButton("Submit", acceptButtonID); buttonsTray.addView(acceptButton);
        Button cancelButton = createPopupButton("Cancel", cancelButtonID); buttonsTray.addView(cancelButton);
        return buttonsTray;
    }

    private LinearLayout createPopupMenuLayout() {
        LinearLayout PopupLayout = new LinearLayout(this);
        PopupLayout.setOrientation(LinearLayout.VERTICAL);
        PopupLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        PopupLayout.setBackgroundColor(Color.WHITE);
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
