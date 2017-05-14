package lunacyarts.abgstudios.butany62.accessories.dndapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Alien on 5/12/2017.
 */

public class HealthTray {

    private LinearLayout content; public LinearLayout getContent() { return content; }

    private int health;
    private int maxHealth; public void setMaxHealth(int max) { maxHealth = max; if (health > max) { health = max; }
        refreshHealth(healthText, health); refreshHealth(nonLethalHealthText, nonLethalHealth);}
    private int minHealth;

    private int nonLethalHealth;

    private TextView healthText;
    private TextView nonLethalHealthText;

    public HealthTray(Context context) {

        content = new LinearLayout(context);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        RelativeLayout lethalTray = new RelativeLayout(context);
        RelativeLayout nonLethalTray = new RelativeLayout(context);

        ImageView heartImage = setHeart(context, R.drawable.heart);
        lethalTray.addView(heartImage);
        ImageView nonHeartImage = setHeart(context, R.drawable.nonheart);
        nonLethalTray.addView(nonHeartImage);
        lethalTray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), CommsTester.class);
                view.getContext().startActivity(i);
            }
        });

        health = 100;
        maxHealth = 100;
        minHealth = -100;

        nonLethalHealth = 0;

        healthText = setText(context, String.valueOf(health) + "/" + String.valueOf(maxHealth));
        lethalTray.addView(healthText);
        nonLethalHealthText = setText(context, String.valueOf(nonLethalHealth + "/" + String.valueOf(maxHealth)));
        nonLethalTray.addView(nonLethalHealthText);

        FloatingActionButton addHealth = setFAB(context, R.drawable.dialog_full_holo_dark_plus, false);
        lethalTray.addView(addHealth);
        FloatingActionButton subtractHealth = setFAB(context, R.drawable.dialog_full_holo_dark_9, true);
        lethalTray.addView(subtractHealth);
        FloatingActionButton addNonLethalHealth = setFAB(context, R.drawable.dialog_full_holo_dark_plus, false);
        nonLethalTray.addView(addNonLethalHealth);
        FloatingActionButton subtractNonLethalHealth = setFAB(context, R.drawable.dialog_full_holo_dark_9, true);
        nonLethalTray.addView(subtractNonLethalHealth);

        lethalTray.findViewById(addHealth.getId()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (health < maxHealth) {
                    health += 1;
                    refreshHealth(healthText, health);
                }
            }
        });

        lethalTray.findViewById(subtractHealth.getId()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (health > minHealth) {
                    health -= 1;
                    refreshHealth(healthText, health);
                }
            }
        });

        nonLethalTray.findViewById(addNonLethalHealth.getId()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nonLethalHealth < maxHealth) {
                    nonLethalHealth += 1;
                    refreshHealth(nonLethalHealthText, nonLethalHealth);
                }
            }
        });

        nonLethalTray.findViewById(subtractNonLethalHealth.getId()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nonLethalHealth > 0) {
                    nonLethalHealth -= 1;
                    refreshHealth(nonLethalHealthText, nonLethalHealth);
                }
            }
        });

        content.addView(lethalTray);
        content.addView(nonLethalTray);
    }

    private ImageView setHeart(Context context, @DrawableRes int image) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(image);
        RelativeLayout.LayoutParams heartParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        heartParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        imageView.setLayoutParams(heartParams);
        return imageView;
    }

    private TextView setText(Context context, String value) {
        TextView text = new TextView(context);
        text.setText(value);
        text.setTextSize(30);
        RelativeLayout.LayoutParams healthTrackerParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        healthTrackerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        healthTrackerParams.addRule(RelativeLayout.CENTER_VERTICAL);
        text.setLayoutParams(healthTrackerParams);
        return text;
    }

    private FloatingActionButton setFAB(Context context, @DrawableRes int resource, boolean start) {
        FloatingActionButton button = new FloatingActionButton(context);
        button.setSize(FloatingActionButton.SIZE_NORMAL);
        button.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255, 0, 153, 204)));
        button.setClickable(true);
        button.setImageResource(resource);
        button.setId(View.generateViewId());
        RelativeLayout.LayoutParams ButtonParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (start) { ButtonParams.addRule(RelativeLayout.ALIGN_PARENT_START); }
        else { ButtonParams.addRule(RelativeLayout.ALIGN_PARENT_END); }
        ButtonParams.addRule(RelativeLayout.CENTER_VERTICAL);
        button.setLayoutParams(ButtonParams);
        return button;
    }

    private void refreshHealth(TextView tracker, int health) {
        tracker.setText(String.valueOf(health) + "/" + String.valueOf(maxHealth));
    }

}
