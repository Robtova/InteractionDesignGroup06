package uk.ac.cam.group06.idesign;

import uk.ac.cam.relf2.idesign.components.*;
import uk.ac.cam.relf2.idesign.components.TextComponent;

import java.awt.*;

public class SettingsScreen extends GraphicComponent {
    private static Image UP_ARROW_IMAGE = Utils.loadImage("/up_arrow.png");
    
    private static SettingLine units, asthmatic, notifications;

    public SettingsScreen() {
        setBackgroundColor(new Color(250, 250, 250, 255));
        setSize(100, 100, false);

        // Add the SETTINGS text on top
        TextComponent text = new TextComponent();
        text.setBackgroundColor(new Color(70, 70, 70));
        text.setFont(new Font("Ariel", Font.PLAIN, 36));
        text.setText("SETTINGS");
        text.setPosition(150, 55, true);
        addComponent(text);

        // Add return arrow to get to main screen
        ImageComponent mArrow = new ImageComponent(UP_ARROW_IMAGE);
        mArrow.setSize(25, 25);
        mArrow.setPosition(15, 47,  true);
        mArrow.setRotation(3*Math.PI/2);
        GraphicComponent toDisable = this;
        mArrow.setComponentListener(new ComponentListener() {
            @Override
            public void onClicked(int x, int y) {
                ApplicationFrame.removeComponent(toDisable);
            }
        });
        addComponent(mArrow);

        // Add line
        GraphicComponent divide = new GraphicComponent();
        divide.setSize(100, false, 1, true);
        divide.setPosition(0, 111);
        divide.setBackgroundColor(new Color(180, 180, 180));
        addComponent(divide);

        // Now add a list to hold and organise individual settings lines
        StackComponent settingsList = new StackComponent();
        settingsList.setSize(100, 86, false);
        settingsList.setPosition(0, 112);
        addComponent(settingsList);

        // Add setting for user to specify whether they suffer from asthma
        asthmatic = new SettingLine("Asthma sufferer", "Also use pollutants only hazardous to asthmatics");
        settingsList.addComponent(asthmatic);

        // Setting to allow user to change units used in the app
        units = new SettingLine("Use metric", "Select whether to use metric or imperial units");
        settingsList.addComponent(units);

        // Setting to allow user to (theoretically) dis/enable phone alerts of bad conditions
        notifications = new SettingLine("Display notifications", "If conditions are hazardous, display a notification?");
        settingsList.addComponent(notifications);
    }

    public static boolean getForAsthmatics() {
        return asthmatic.getOn();
    }

    public static boolean getUseMetric() {
        return units.getOn();
    }

    @Override
    protected void addedToComponent(GraphicComponent parent) {
        super.addedToComponent(parent);
        ApplicationFrame.getHomeScreen().setClickable(false);
        ApplicationFrame.getBreakdownScreen().setClickable(false);
    }

    @Override
    protected void removedFromComponent(GraphicComponent parent) {
        super.removedFromComponent(parent);
        ApplicationFrame.getHomeScreen().setClickable(true);
        ApplicationFrame.getBreakdownScreen().setClickable(true);
        ApplicationFrame.reloadAll();
    }
}
