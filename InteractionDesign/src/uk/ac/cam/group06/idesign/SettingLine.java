package uk.ac.cam.group06.idesign;

import uk.ac.cam.relf2.idesign.components.ComponentListener;
import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.TextComponent;

import java.awt.*;

public class SettingLine extends GraphicComponent implements ComponentListener{
    private static final int mHeight = 100;
    private static final Font mMainFont = new Font("Ariel", Font.PLAIN, 36),
                              mBabyFont = new Font("Ariel", Font.PLAIN, 16);
    private static final Color mBackgroundCol = new Color(250, 250, 250),
                               mMainColor = new Color(140, 140, 140),
                               mBabyColor = new Color(180, 180, 180);

    public SettingLine(String settingText, String settingDescription){
        setSize(100, false, mHeight, true);
        setBackgroundColor(mBackgroundCol);

        // Add line at bottom
        GraphicComponent divide = new GraphicComponent();
        divide.setSize(100, false, 1, true);
        divide.setPosition(0, mHeight-1);
        divide.setBackgroundColor(new Color(180, 180, 180));
        addComponent(divide);

        // Add setting name text
        TextComponent mainSettingText = new TextComponent();
        mainSettingText.setAlign(TextComponent.RIGHT);
        mainSettingText.setBackgroundColor(mMainColor);
        mainSettingText.setFont(mMainFont);
        mainSettingText.setText(settingText);
        mainSettingText.setPosition(20, 33, true);
        addComponent(mainSettingText);

        // Add setting description text
        TextComponent descText = new TextComponent();
        descText.setAlign(TextComponent.RIGHT);
        descText.setBackgroundColor(mBabyColor);
        descText.setFont(mBabyFont);
        descText.setText(settingDescription);
        descText.setPosition(20, 65, true);
        addComponent(descText);

        // Add toggle indicator icon
        toggle = new GraphicComponent();
        toggle.setBackgroundColor(mOn);
        toggle.setSize(20, 20, true);
        toggle.setPosition(85, 50, false);
        addComponent(toggle);
    }

    private static Color mOn = new Color(70, 70, 70),
                         mOff = new Color(180, 180, 180);
    private GraphicComponent toggle;
    public void toggleGraphic() {
        if(toggle.getBackgroundColor() == mOn)
            toggle.setBackgroundColor(mOff);
        else
            toggle.setBackgroundColor(mOn);
    }

    @Override
    public void onClicked(int x, int y) {
    }
}
