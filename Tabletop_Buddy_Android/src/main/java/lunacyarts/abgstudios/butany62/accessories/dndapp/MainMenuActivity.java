package lunacyarts.abgstudios.butany62.accessories.dndapp;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.view.Gravity;
import android.view.SubMenu;
import android.view.View;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //region Initializing Variables

    //Variables for the Menu drawer and top toolbar.
    DrawerLayout drawer;
    Toolbar toolbar;

    //Table for the Initiative list.
    TableLayout initiativeList;
    TableRow topRow;

    //Content for the Menu items.
    LinearLayout initiativeTrackerContent;
    LinearLayout healthTrackerContent;

    //IDs to be used to identify the settings menu items.
    private static final String MENU_ITEM_DELETE = "Delete Selected";
    private static final String MENU_ITEM_REARRANGE = "Hold Selected";
    private static final String MENU_ITEM_ADD = "Add Player";
    private static final String MENU_ITEM_SETTINGS = "Settings";
    private static final int MENU_LIST_HOLD = View.generateViewId();

    //The layout of the popup that adds in an initiative tray.
    LinearLayout addInitPopupLayout;

    //The popup that adds in an initiative tray.
    PopupWindow addInitPopup;

    //A sorted list of the initiative trays.
    SortedMap<String, InitTrays> initOrder;

    //A variables for the IDs of the Popup buttons.
    @IdRes int addInitAcceptButtonID;
    @IdRes int addInitCancelButtonID;
    @IdRes int nextButtonID;

    //The placeholder for the content of the main menu.
    RelativeLayout mainMenuContent;

    //The fields to be placed in the AddInit popup.
    EditText addInitInitField;
    EditText addInitNameField;
    EditText addInitAcField;
    EditText addInitDexField;
    EditText addInitFortField;
    EditText addInitReflexField;
    EditText addInitWillField;

    FloatingActionButton nextButton;

    String menuSelected; //endregion

    //region Tells what the program does when made.
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //region Auto generated. Not sure what it does.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //endregion

        //region Sets up what the menu drawer does when pressed
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState(); //endregion

        //region Finds the Navigation Menu, sets it to a variable, and sets the listener for the items.
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this); //endregion

        //region The Initiative Tracker Starting Content.
        initiativeTrackerContent = new LinearLayout(this);
        initiativeTrackerContent.setOrientation(LinearLayout.VERTICAL);
        initiativeTrackerContent.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        //region Initializes the main ScrollView and adds it to the Content.
        ScrollView tableScroll = new ScrollView(this); tableScroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        //region Initializes the Table and adds it to the ScrollView.
        initiativeList = new TableLayout(this); initiativeList.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        initiativeList.setColumnStretchable(1, true);

        //region Initializes the Top Row
        topRow = new TableRow(this);

        //region Initializes the Top Row Text and adds it to the Row.
        TextView initColumn = setTableColumnText(R.string.init_text, R.drawable.drawable_cell_shape_top); topRow.addView(initColumn);
        TextView nameColumn = setTableColumnText(R.string.name_text, R.drawable.drawable_cell_shape_top); topRow.addView(nameColumn);
        TextView acColumn = setTableColumnText(R.string.ac_text, R.drawable.drawable_cell_shape_top); topRow.addView(acColumn);
        TextView savesColumn = setTableColumnText(R.string.savesText, R.drawable.drawable_cell_shape_top); topRow.addView(savesColumn);
        initiativeList.addView(topRow); //endregion

        // endregion

        tableScroll.addView(initiativeList); //endregion

        initiativeTrackerContent.addView(tableScroll); //endregion

        //region Initializes the button used to go to the next player in the Initiative Order, sets what it does when clicked, and adds it to the Content.
        nextButton = new FloatingActionButton(this);
        LinearLayout.LayoutParams nextButtonParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        nextButtonParams.setMargins(16, 16, 16, 16);
        nextButtonParams.gravity = Gravity.CENTER_HORIZONTAL;
        nextButton.setLayoutParams(nextButtonParams);
        nextButton.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255, 0, 153, 204)));
        nextButton.setClickable(true);
        nextButton.setImageResource(R.drawable.ic_media_ff);
        nextButtonID = View.generateViewId();
        nextButton.setId(nextButtonID);
        initiativeTrackerContent.addView(nextButton);

        //region Sets what the nextInit Button does when pressed and references
        applyListener(initiativeTrackerContent.findViewById(nextButtonID), new Button.OnClickListener()

        {
            @Override
            public void onClick(View v) {

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
        });//endregion

        //endregion

        //endregion

        //region The Health Tracker Starting Content.
        healthTrackerContent = new LinearLayout(this);
        healthTrackerContent.setOrientation(LinearLayout.VERTICAL);
        healthTrackerContent.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        ImageView heartImage = new ImageView(this); heartImage.setImageResource(R.drawable.heart);
        ImageView nonHeartImage = new ImageView(this); nonHeartImage.setImageResource(R.drawable.nonheart);

        //endregion

        //region Creates the Popup.
        addInitPopup = new PopupWindow(addInitPopupLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //region Creates the Popup Layout.
        addInitPopupLayout = createPopupMenuLayout();

        //region Creates the fields to type in for the Add Init Popup and adds them to the Layout.
        addInitInitField = createPopupField("Init", InputType.TYPE_CLASS_NUMBER); addInitPopupLayout.addView(addInitInitField);
        addInitNameField = createPopupField("Player Name", InputType.TYPE_CLASS_TEXT); addInitPopupLayout.addView(addInitNameField);
        addInitAcField = createPopupField("AC", InputType.TYPE_CLASS_NUMBER); addInitPopupLayout.addView(addInitAcField);
        addInitDexField = createPopupField("Dex Mod", InputType.TYPE_CLASS_NUMBER); addInitPopupLayout.addView(addInitDexField);
        addInitFortField = createPopupField("Fort", InputType.TYPE_CLASS_NUMBER); addInitPopupLayout.addView(addInitFortField);
        addInitReflexField = createPopupField("Reflex", InputType.TYPE_CLASS_NUMBER); addInitPopupLayout.addView(addInitReflexField);
        addInitWillField = createPopupField("Will", InputType.TYPE_CLASS_NUMBER); addInitPopupLayout.addView(addInitWillField);
        //endregion

        //region Sets the content to be viewed as the Popup Layout.
        addInitPopup.setContentView(addInitPopupLayout); //endregion

        //endregion

        //region Defines the Popup Button IDs by generating a new one.
        addInitAcceptButtonID = View.generateViewId();
        addInitCancelButtonID = View.generateViewId();
        //endregion

        //region Creates the layout for the buttons area and adds it to the Popup Layout.
        LinearLayout addInitButtonsLayout = createPopupButtonsLayout(addInitAcceptButtonID, addInitCancelButtonID);
        addInitPopupLayout.addView(addInitButtonsLayout);
        //endregion

        //endregion

        //region Finds the RelativeLayout that the Content will be put into.
        mainMenuContent = (RelativeLayout) findViewById(R.id.content_main_menu); //endregion

        //region Variable defined that is used to tell the Settings Menu what content to have.
        menuSelected = "MAINMENU"; //endregion

        //region Defining the sorted list to have the highest number appear first and the lowest number appear last.
        initOrder = new TreeMap<>(Collections.reverseOrder()); //endregion
    } //endregion

    //region Auto Generated. Not sure what it does.
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    } //endregion

    //region Tells the program what to do for Context Menus. (not used yet)
/*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            default:
                return super.onContextItemSelected(item);
        }
    }*/ //endregion

    //region Autogenerated. Tells the program to pull up main_menu when the Settings Menu is called.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    } //endregion

    //region Tells the program what to do right before the Settings Menu is displayed.
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menuSelected.equals("INITMENU")) {
            menu.clear();
            menu.add(R.menu.main_menu, View.NO_ID, 0, MENU_ITEM_ADD);
            menu.add(R.menu.main_menu, View.NO_ID, 1, MENU_ITEM_REARRANGE);
            menu.add(R.menu.main_menu, View.NO_ID, 2, MENU_ITEM_DELETE);
            menu.addSubMenu(R.menu.main_menu, MENU_LIST_HOLD, 3, "Unhold Players");
            menu.add(R.menu.main_menu, View.NO_ID, 4, MENU_ITEM_SETTINGS);
            SubMenu unholdPlayers = menu.getItem(3).getSubMenu();
            int j = 0;
            for (String i : initOrder.keySet()) {
                if (initOrder.get(i).isHeld())
                    unholdPlayers.add(MENU_LIST_HOLD, View.NO_ID, j, initOrder.get(i).getName()); j++;
            }
        }
        if (menuSelected.equals("MAINMENU")) {
            menu.clear();
            menu.add(R.menu.main_menu, View.NO_ID, 0, MENU_ITEM_SETTINGS);
        }
        return true;
    } //endregion

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SortedMap<String, InitTrays> initSet = initOrder;
        for (String i : initSet.keySet()) {
            if (item.getTitle() == initOrder.get(i).getName()) {
                unholdPlayer(i);
                return true;
            }
        }

        if (item.getTitle() == MENU_ITEM_DELETE) {
            deleteSelected();
            return true;
        } else if (item.getTitle() == MENU_ITEM_REARRANGE) {
            holdInit();
            return true;
        } else if (item.getTitle() == MENU_ITEM_ADD) {
            addPlayer();
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.MenuHome) {
            changeToMain();
        } else if (id == R.id.MenuInitTracker) {
            changeToInitTracker();
        } else if (id == R.id.MenuHealth) {
            changeToHealthTracker();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeToHealthTracker() {
        mainMenuContent.removeAllViews();



        menuSelected = "HEALTHMENU";
    }

    private static void applyListener(View child, Button.OnClickListener listener) {
        if (child == null)
            return;

        if (child instanceof ViewGroup) {
            applyListener((ViewGroup) child, listener);
        }
        else if (child != null) {
            child.setOnClickListener(listener);
        }
    }

    private static void applyListener(ViewGroup parent, Button.OnClickListener listener) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                applyListener((ViewGroup) child, listener);
            } else {
                applyListener(child, listener);
            }
        }
    }

    private void changeToInitTracker() {
        mainMenuContent.removeAllViews();
        mainMenuContent.addView(initiativeTrackerContent);


        menuSelected = "INITMENU";
    }

    private void changeToMain() {
        mainMenuContent.removeAllViews();

        menuSelected = "MAINMENU";
    }

    private TextView setTableColumnText(@StringRes int text, @DrawableRes int bgResource) {
        TextView Column = new TextView(this);
        Column.setPadding(5, 0, 5, 0);
        Column.setBackgroundResource(bgResource);
        Column.setGravity(Gravity.START);
        Column.setText(text);
        Column.setTextSize(24);
        return Column;
    }
    private TextView setTableColumnText(String text, @DrawableRes int bgResource) {
        TextView Column = new TextView(this);
        Column.setPadding(5, 0, 5, 0);
        Column.setBackgroundResource(bgResource);
        Column.setGravity(Gravity.START);
        Column.setText(text);
        Column.setTextSize(24);
        return Column;
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

    private void holdInit() {

        boolean changeSelected = false;
        SortedMap<String, InitTrays> initTraysHolder = initOrder;
        for (String i : initTraysHolder.keySet()) {
            if (!changeSelected) {
                if (initTraysHolder.get(i).isSelected()) {
                    changeSelected = true;
                    initOrder.get(i).setHeld(true);
                }
            }
            else {
                initTraysHolder.get(i).setSelected(true);
                changeSelected = false;
            }
        }
        if (changeSelected && initOrder.size() > 0) {
            initOrder.get(initOrder.firstKey()).setSelected(true);
        }
        refreshTrays();

/*        changeInitPopup.showAsDropDown(toolbar, 0, 0);
        changeInitPopup.setFocusable(true); changeInitPopup.update();

        //region Sets what the changeInitAcceptButton does when pressed.

        applyListener(changeInitPopupLayout.findViewById(changeInitAcceptButtonID), new Button.OnClickListener()

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
        applyListener(changeInitPopupLayout.findViewById(changeInitCancelButtonID), new Button.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                changeInitPopup.dismiss();
            }
        });//endregion*/
    }

    private void unholdPlayer(String i) {
        InitTrays placeHolder = initOrder.get(i);
        initOrder.remove(i);
        placeHolder.setHeld(false);
        boolean foundSelectedPlayer = false;
        SortedMap<String, InitTrays> initSet2 = initOrder;
        for (String j : initSet2.keySet()) {
            int newPlace = initOrder.get(j).getPlace() - 1;
            if (foundSelectedPlayer) {
                if (initOrder.get(j).getInitiative() == placeHolder.getInitiative()) {
                    InitTrays placeHolder2 = initOrder.get(j);
                    initOrder.remove(j);
                    placeHolder2.setPlace(newPlace);
                    initOrder.put(placeHolder2.getListKey(), placeHolder2);
                }
                else
                    foundSelectedPlayer = false;
            }
            if (initOrder.get(j).isSelected()) {
                foundSelectedPlayer = true;
                placeHolder.setInitiative(initOrder.get(j).getInitiative());
                placeHolder.setPlace(initOrder.get(j).getPlace());
                InitTrays placeHolder2 = initOrder.get(j);
                initOrder.remove(j);
                placeHolder2.setPlace(newPlace);
                initOrder.put(placeHolder2.getListKey(), placeHolder2);
                initOrder.put(placeHolder.getListKey(), placeHolder);
            }
        }
        refreshTrays();
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
        initiativeList.removeAllViews();
        initiativeList.addView(topRow);
        for (final String i : initOrder.keySet()){

            @DrawableRes int bg = initOrder.get(i).getBackgroundResource();

            TableRow initTray = new TableRow(this);
            TextView initView = setTableColumnText(String.valueOf(initOrder.get(i).getInitiative()), bg);
            TextView nameView = setTableColumnText(initOrder.get(i).getName(), bg);
            TextView acView = setTableColumnText(String.valueOf(initOrder.get(i).getAC()), bg);
            TextView savesView = setTableColumnText(initOrder.get(i).getSaves(), bg);

            initTray.addView(initView);
            initTray.addView(nameView);
            initTray.addView(acView);
            initTray.addView(savesView);

            initiativeList.addView(initTray);
        }
    }

    public void addPlayer(){
        //Brings up the AddInit Popup.
        addInitPopup.showAsDropDown(toolbar, 0, 0);
        addInitPopup.setFocusable(true); addInitPopup.update();

        //region Finds the addInitAcceptButton by its ID and sets what it will do when pressed.

        applyListener(addInitPopupLayout.findViewById(addInitAcceptButtonID), new Button.OnClickListener()

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

                //Only puts the person there if there is not already another person in that initiative spot.
                if (!initOrder.containsKey(temp.getListKey())) {
                    //Places the person at that initiative order with all of their stats.
                    initOrder.put(temp.getListKey(), temp);
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
        applyListener(addInitPopupLayout.findViewById(addInitCancelButtonID), new Button.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                //Does nothing and dismisses the window.
                addInitPopup.dismiss();
            }
        });//endregion
    }
}
