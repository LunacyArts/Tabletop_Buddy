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
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.zip.Inflater;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //region Initializing Variables

    //Variables for the Menu drawer and top toolbar.
    DrawerLayout drawer;
    Toolbar toolbar;

    //Table for the Initiative list.
    TableLayout initiativeList;
    TableRow topRow;
    ScrollView tableScroll;

    //Content for the Menu items.
    LinearLayout initiativeTrackerContent;
    LinearLayout healthTrackerContent;

    //IDs to be used to identify the settings menu items.
    private static final String MENU_ITEM_ADD = "Add Player";
    private static final String MENU_ITEM_REARRANGE = "Hold Selected";
    private static final String MENU_ITEM_DELETE = "Delete Selected";
    private static final String MENU_ITEM_CHANGE_MAX = "Edit Max Health";
    private static final int MENU_LIST_HOLD = View.generateViewId();
    private static final String MENU_ITEM_SETTINGS = "Settings";

    //A sorted list of the initiative trays.
    SortedMap<String, InitTrays> initOrder;

    //A variables for the IDs of the Popup buttons.
    @IdRes int nextButtonID;

    //The placeholder for the content of the main menu.
    RelativeLayout mainMenuContent;

    FloatingActionButton nextButton;

    PopupHelper addPlayerPopup;
    PopupHelper changeMaxHealthPopup;

    List<HealthTray> healthTrays;

    String menuSelected; //endregion

    //region Tells what the program does when made.
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //region Auto generated. Not sure what it does.
		//tells parent processes that we're starting up
        super.onCreate(savedInstanceState);
		//sets the view to the corresponding layout xml
        setContentView(R.layout.activity_main_menu);
		//gets the toolbar object from the layout xml
        toolbar = (Toolbar) findViewById(R.id.toolbar);
		//tells the activity that we're using a toolbar and it's a "supportActionBar" type toolbar
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
        tableScroll = new ScrollView(this); tableScroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        initiativeList = (TableLayout) LayoutInflater.from(this).inflate(R.layout.initiative_table_template, null);

        // endregion

        tableScroll.addView(initiativeList); //endregion

        initiativeTrackerContent.addView(tableScroll); //endregion

        //region Initializes the button used to go to the next player in the Initiative Order, sets what it does when clicked, and adds it to the Content.
		//TODO these types of view objects are usually created in the xml layout file
		//and referenced like like 22 above. this is also OK, but non-standard.
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
		//again, easier to define in xml. ask Fibonacci for more info if you want.
        healthTrackerContent = new LinearLayout(this);
        healthTrackerContent.setOrientation(LinearLayout.VERTICAL);
        healthTrackerContent.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        healthTrays = new ArrayList<>();
        healthTrays.add(new HealthTray(this));
        healthTrackerContent.addView(healthTrays.get(0).getContent());

        addPlayerPopup = new PopupHelper(this);
        addPlayerPopup.addPopupField("Init", InputType.TYPE_CLASS_NUMBER, this);
        addPlayerPopup.addPopupField("Player Name", InputType.TYPE_CLASS_TEXT, this);
        addPlayerPopup.addPopupField("AC", InputType.TYPE_CLASS_NUMBER, this);
        addPlayerPopup.addPopupField("Dex Mod", InputType.TYPE_CLASS_NUMBER, this);
        addPlayerPopup.addPopupField("Fort", InputType.TYPE_CLASS_NUMBER, this);
        addPlayerPopup.addPopupField("Reflex", InputType.TYPE_CLASS_NUMBER, this);
        addPlayerPopup.addPopupField("Will", InputType.TYPE_CLASS_NUMBER, this);

        //region Tells what the accept Button does.
        addPlayerPopup.setOnClickListenerAccept(new Button.OnClickListener()

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
                if (!addPlayerPopup.getField(0).getText().toString().matches("")) {
                    initText = Integer.parseInt(addPlayerPopup.getField(0).getText().toString());
                }
                if (!addPlayerPopup.getField(1).getText().toString().matches("")) {
                    nameText = addPlayerPopup.getField(1).getText().toString();
                }
                if (!addPlayerPopup.getField(2).getText().toString().matches("")) {
                    acText = Integer.parseInt(addPlayerPopup.getField(2).getText().toString());
                }
                if (!addPlayerPopup.getField(3).getText().toString().matches("")) {
                    dexText = Integer.parseInt(addPlayerPopup.getField(3).getText().toString());
                }
                if (!addPlayerPopup.getField(4).getText().toString().matches("")) {
                    save1Text = Integer.parseInt(addPlayerPopup.getField(4).getText().toString());
                }
                if (!addPlayerPopup.getField(5).getText().toString().matches("")) {
                    save2Text = Integer.parseInt(addPlayerPopup.getField(5).getText().toString());
                }
                if (!addPlayerPopup.getField(6).getText().toString().matches("")) {
                    save3Text = Integer.parseInt(addPlayerPopup.getField(6).getText().toString());
                }

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
                    addPlayerPopup.getField(0).setText("");
                    addPlayerPopup.getField(1).setText("");
                    addPlayerPopup.getField(2).setText("");
                    addPlayerPopup.getField(3).setText("");
                    addPlayerPopup.getField(4).setText("");
                    addPlayerPopup.getField(5).setText("");
                    addPlayerPopup.getField(6).setText("");
                    //Ends the Popup.
                    addPlayerPopup.getPopupWindow().dismiss();
                }

            }
        }); //endregion

        changeMaxHealthPopup = new PopupHelper(this);
        changeMaxHealthPopup.addPopupField("Leave blank to keep at last value", InputType.TYPE_CLASS_NUMBER, this);

        //region Tells what the accept Button does.
        changeMaxHealthPopup.setOnClickListenerAccept(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthTrays.get(0).setMaxHealth(Integer.parseInt(changeMaxHealthPopup.getField(0).getText().toString()));

                changeMaxHealthPopup.getPopupWindow().dismiss();
            }
        }); //endregion

        //region Finds the RelativeLayout that the Content will be put into.
        mainMenuContent = (RelativeLayout) findViewById(R.id.content_main_menu); //endregion

        //region Variable defined that is used to tell the Settings Menu what content to have.
        menuSelected = "MAINMENU"; //endregion

        //region Defining the sorted list to have the highest number appear first and the lowest number appear last.
        initOrder = new TreeMap<>(Collections.reverseOrder()); //endregion
    } //endregion

    //region Auto Generated. Not sure what it does.
	//overrides the back button to add a special behavior
	//just opens and closes the drawer when the back button is pressed.
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
	//technically it only tells this activity which menu should be populated into the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    } //endregion

    //region Tells the program what to do right before the Settings Menu is displayed.
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
		//this is also usually xml. what are you using to generate this?
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
        } else if (menuSelected.equals("MAINMENU")) {
            menu.clear();
            menu.add(R.menu.main_menu, View.NO_ID, 0, MENU_ITEM_SETTINGS);
        } else if (menuSelected.equals("HEALTHMENU")) {
            menu.clear();
            menu.add(R.menu.main_menu, View.NO_ID, 0, MENU_ITEM_CHANGE_MAX);
            menu.add(R.menu.main_menu, View.NO_ID, 1, MENU_ITEM_SETTINGS);
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
            addPlayerPopup.showPopupAsDropDown(toolbar);
            return true;
        } else if (item.getTitle() == MENU_ITEM_CHANGE_MAX) {
            changeMaxHealthPopup.showPopupAsDropDown(toolbar);
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

    private void changeToMain() {
        mainMenuContent.removeAllViews();

        menuSelected = "MAINMENU";
    }
    private void changeToInitTracker() {
        mainMenuContent.removeAllViews();
        mainMenuContent.addView(initiativeTrackerContent);


        menuSelected = "INITMENU";
    }
    private void changeToHealthTracker() {
        mainMenuContent.removeAllViews();

        mainMenuContent.addView(healthTrackerContent);

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

    private TextView setTableColumnText(@StringRes int text, @DrawableRes int bgResource) {
        TextView Column = new TextView(this);
        Column.setPadding(5, 0, 5, 0);
        Column.setBackgroundResource(bgResource);
        Column.setGravity(Gravity.START);
        Column.setText(text);
        Column.setTextSize(24);
        return Column;
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
        initiativeList = (TableLayout) LayoutInflater.from(this).inflate(R.layout.initiative_table_template, null);
        for (final String i : initOrder.keySet())
            initiativeList.addView(initOrder.get(i).refresh(this));
        tableScroll.removeAllViews();
        tableScroll.addView(initiativeList);
        initiativeList.requestLayout();
    }
}
