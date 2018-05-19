package uk.ac.cam.group06.idesign;

import uk.ac.cam.relf2.idesign.components.*;
import uk.ac.cam.relf2.idesign.components.TextComponent;

import java.awt.*;

public class SettingsScreen extends GraphicComponent implements ComponentListener {
    private static Image UP_ARROW_IMAGE = Utils.loadImage("/up_arrow.png");

    public SettingsScreen() {
        setBackgroundColor(new Color(220, 220, 220, 255));
        setSize(100, 100, false);

        TextComponent text = new TextComponent();
        text.setBackgroundColor(new Color(180, 180, 180));
        text.setFont(new Font("Ariel", Font.PLAIN, 60));
        text.setText("Settings");
        text.setPosition(190, 55, true);
        addComponent(text);

        ImageComponent mArrow = new ImageComponent(UP_ARROW_IMAGE);
        //mArrow.setBorder(GraphicComponent.SCREEN_RIGHT, GraphicComponent.SCREEN_TOP);
        mArrow.setSize(35, 35);
        mArrow.setPosition(15, 45,  true);
        //mArrow.setOrigin(GraphicComponent.MIDDLE_LEFT);
        mArrow.setRotation(3*Math.PI/2);
        GraphicComponent toDisable = this;
        mArrow.setComponentListener(new ComponentListener() {
            @Override
            public void onClicked(int x, int y) {
                ApplicationFrame.removeComponent(toDisable);
            }
        });
        addComponent(mArrow);
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
    }

    @Override
    public void onClicked(int x, int y) {
        ApplicationFrame.removeComponent(this);
    }
}
