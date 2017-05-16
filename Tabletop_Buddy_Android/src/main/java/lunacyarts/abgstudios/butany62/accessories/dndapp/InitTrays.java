package lunacyarts.abgstudios.butany62.accessories.dndapp;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Alien on 4/16/2017.
 */

public class InitTrays {


    private int initiative;
    public int getInitiative() { return initiative; }
    public void setInitiative(int init) { this.initiative = init; }
    private String name;
    public String getName() { return name; }
    private int ac;
    private int dex;
    private List<Integer> saves;
    public String getSaves() { return saves.get(0) + "/" + saves.get(1) + "/" + saves.get(2); }
    private int place;
    public int getPlace() {  return place; }
    public void setPlace(int Place) { place = Place; }
    private boolean selected;
    public boolean isSelected() { return selected; }
    public void setSelected(boolean Selected) {
        selected = Selected;
        if (Selected) {
            backgroundResource = R.drawable.drawable_cell_shape_darkened;
            held = false;
        } else {
            backgroundResource = R.drawable.drawable_cell_shape;
        }
    }
    private boolean held;
    public boolean isHeld() { return held; }
    public void setHeld(boolean Held) {
        held = Held;
        if (Held) {
            backgroundResource = R.drawable.drawable_cell_shape_held;
            selected = false;
        } else {
            backgroundResource = R.drawable.drawable_cell_shape;
        }
    }
    private @DrawableRes int backgroundResource;

    //Gets the key to put into the List.
    public String getListKey() {
        //Error prevention. If one of these variables is below 10, it adds in a '0' before it so it doesnt go before other variables.
        //Example: 8 would go before 63, so add a '0' to it and now 63 goes before 08.
        String init0 = "";
        if (initiative < 10) {
            init0 = "0";
        }
        String dex0 = "";
        if (dex < 10) {
            dex0 = "0";
        }
        String place0 = "";
        if (place < 10) {
            place0 = "0";
        }

        //String to identify what initiative order this person goes in.
        //Example: Init:13 Place:50 Dex:4 = 135004 <- init number to be sorted by.
        return init0 + String.valueOf(initiative) + place0 + String.valueOf(place) + dex0 + String.valueOf(dex);
    }

    public InitTrays(int Initiative, String Name, int AC, int Dex, int save1, int save2, int save3) {
        initiative = Initiative;
        name = Name;
        ac = AC;
        dex = Dex;
        saves = new ArrayList<>();
        saves.add(save1);
        saves.add(save2);
        saves.add(save3);
        place = 50;
        selected = false;
        held = false;
        backgroundResource = R.drawable.drawable_cell_shape;
    }

    public TableRow refresh(Context context) {

        TableRow initTray = (TableRow) LayoutInflater.from(context).inflate(R.layout.initiative_row_template, null);

        setTableColumnText(R.id.init_text, initTray, String.valueOf(initiative), backgroundResource);
        setTableColumnText(R.id.name_text, initTray, name, backgroundResource);
        setTableColumnText(R.id.AC_text, initTray, String.valueOf(ac), backgroundResource);
        setTableColumnText(R.id.saves_text, initTray, getSaves(), backgroundResource);

        return initTray;
    }

    private void setTableColumnText(@IdRes int id, TableRow initTray, String text, @DrawableRes int bgResource) {
        TextView Column = (TextView) initTray.findViewById(id);
        Column.setBackgroundResource(bgResource);
        Column.setText(text);
    }
}
