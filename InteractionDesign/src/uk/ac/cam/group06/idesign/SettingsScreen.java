package uk.ac.cam.group06.idesign;

import sun.security.ssl.Debug;
import uk.ac.cam.relf2.idesign.components.*;

import java.awt.*;

public class SettingsScreen extends StackComponent {

    public static SettingsScreen singleton;

    private GraphicComponent mHomeScreen;

    private Input input;

    private static Image SETTINGS = Utils.loadImage("/settings_button.png");

    public SettingsScreen() {
        this.mHomeScreen = ApplicationFrame.getHomeScreen();
        initialise();

        this.input = ApplicationFrame.getInput();
/*
        ic = new ImageComponent(SETTINGS);
        ic.setSize(100, 100, false);
        ic.setPosition(0, 50, false);
        this.addComponent(ic);*/
    }
    ImageComponent ic;
    @Override
    public void update() {
        //ic.setSize(50, 50, false);

    }

    private void initialise() {
        this.setBackgroundColor(new Color(250, 250, 250));
        this.setSize(100, 100, false);
    }
}
