package lunacyarts.abgstudios.butany62.accessories.dndapp;

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

    /**
     *
     * @return
     */
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
    public void setSelected(boolean Selected) { selected = Selected; }

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
    }

}
