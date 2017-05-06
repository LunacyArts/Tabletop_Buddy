package lunacyarts.abgstudios.butany62.accessories.dndapp;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

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
    public int getAC() { return ac; }
    private int dex;
    public int getDex() { return dex; }
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
    public @DrawableRes int getBackgroundResource() { return backgroundResource; }

    //Gets the key to put into the List.
    public String getListKey() {
        //Error prevention. If one of these variables is below 10, it adds in a '0' before it so it doesnt go before other variables.
        //Example: 8 would go before 63, so add a '0' to it and now 63 goes before 08.
        String init0 = "";
        if (this.getInitiative() < 10) {
            init0 = "0";
        }
        String dex0 = "";
        if (this.getDex() < 10) {
            dex0 = "0";
        }
        String place0 = "";
        if (this.getPlace() < 10) {
            place0 = "0";
        }

        //String to identify what initiative order this person goes in.
        //Example: Init:13 Place:50 Dex:4 = 135004 <- init number to be sorted by.
        return init0 + String.valueOf(this.getInitiative()) + place0 + String.valueOf(this.getPlace()) + dex0 + String.valueOf(this.getDex());
    }

    public InitTrays(int Initiative, String Name, int AC, int Dex, int save1, int save2, int save3) {
        initiative = Initiative;
        name = Name;
        ac = AC;
        dex = Dex;
        saves = new ArrayList<Integer>();
        saves.add(save1);
        saves.add(save2);
        saves.add(save3);
        place = 50;
        selected = false;
        held = false;
        backgroundResource = R.drawable.drawable_cell_shape;
    }

}
