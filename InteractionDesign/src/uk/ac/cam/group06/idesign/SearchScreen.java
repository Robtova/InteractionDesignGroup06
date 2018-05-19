package uk.ac.cam.group06.idesign;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import uk.ac.cam.group06.api.CityNotFoundException;
import uk.ac.cam.group06.api.ISOCode;
import uk.ac.cam.group06.api.store.DataStore;
import uk.ac.cam.relf2.idesign.components.ComponentListener;
import uk.ac.cam.relf2.idesign.components.DropDownComponent;
import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.ImageComponent;
import uk.ac.cam.relf2.idesign.components.TextComponent;
import uk.ac.cam.relf2.idesign.components.TextFieldComponent;
import uk.ac.cam.relf2.idesign.components.Utils;

public class SearchScreen extends GraphicComponent implements ComponentListener {

	private DropDownComponent<ISOCode> mCountry;
	private GraphicComponent mPanel;
	private ImageComponent mLoading;
	private TextFieldComponent mTextField;
	private TextComponent mError;
	
	private static Image X_IMAGE = Utils.loadImage("/x.png");
	
	private static final Image LOADING = Utils.loadImage("/loading.png");
	private static final Image SEARCH_SYMBOL = Utils.loadImage("/search_symbol.png");

	public SearchScreen() {
		setBackgroundColor(new Color(220, 220, 220, 180));
		setSize(100, 100, false);
		
		mPanel = new GraphicComponent() {
			@Override
			public void paint(Graphics2D g) {
				g.setColor(getBackgroundColor());
				int curve = 50;
				g.fillOval(0, 0, curve, curve);
				g.fillOval(0, getHeight() - curve, curve, curve);
				g.fillOval(getWidth() - curve, getHeight() - curve, curve, curve);
				g.fillOval(getWidth() - curve, 0, curve, curve);
				
				g.fillRect(curve / 2, 0, getWidth() - curve, getHeight());
				g.fillRect(0, curve / 2, getWidth(), getHeight() - curve);
			}
		};
		mPanel.setBackgroundColor(new Color(249, 249, 249));
		mPanel.setOrigin(MIDDLE_CENTRE);
		mPanel.setPosition(50, 50, false);
		mPanel.setSize(420, 255);

		TextComponent text = new TextComponent();
		text.setBackgroundColor(new Color(180, 180, 180));
		text.setText("Choose city to get");
		text.setPosition(50, false, 50, true);
		mPanel.addComponent(text);
		
		TextComponent text2 = new TextComponent();
		text2.setBackgroundColor(new Color(180, 180, 180));
		text2.setText("weather information.");
		text2.setPosition(50, false, 80, true);
		mPanel.addComponent(text2);
		
		ImageComponent x = new ImageComponent(X_IMAGE);
		x.setOrigin(TOP_RIGHT);
		x.setBorder(SCREEN_RIGHT, SCREEN_TOP);
		x.setPosition(-20, 20, true);
		x.setSize(32, 32);
		x.setComponentListener(this);
		mPanel.addComponent(x);
		
		mLoading = new ImageComponent(LOADING) {
			private int mSpin = 0;
			
			@Override
			public void update() {
				mSpin++;
				
				if(mSpin >= 20) {
					setRotation(getRotation() - (Math.PI / 4));
					mSpin = 0;
				}
			}
		};
		mLoading.setOrigin(MIDDLE_CENTRE);
		mLoading.setPosition(50, 50, false);
		mLoading.setSize(30, 30, false);
		mLoading.keepAspect(true);
		
		mCountry = new DropDownComponent<ISOCode>(ApplicationFrame.isoCodeList);
		mCountry.setFont(new Font("Arial", Font.PLAIN, 18));
		mCountry.setSize(70, false, 30, true);
		mCountry.setPosition(10, false, 180, true);
		mCountry.setChosenItem(ApplicationFrame.getISOObject());
		mPanel.addComponent(mCountry);
		
		mTextField = new TextFieldComponent() {
			public void onEnter(String text) {
				search();
			}
		};
		mTextField.setSize(80, false, 30, true);
		mTextField.setPosition(10, false, 140, true);
		mPanel.addComponent(mTextField);
		
		ImageComponent search = new ImageComponent(SEARCH_SYMBOL);
		search.setPosition(83, false, 180, true);
		search.setSize(30, 30);
		search.setComponentListener(new ComponentListener() {
			@Override
			public void onClicked(int x, int y) {
				search();
			}
		});
		mPanel.addComponent(search);
		
		mError = new TextComponent();
		mError.setBackgroundColor(new Color(255, 77, 61));
		mError.setText("", 15);
		mError.setPosition(50, false, 110, true);
		mPanel.addComponent(mError);
	}
	
	public void search() {
		final GraphicComponent screen = this;
		
		this.removeComponent(mPanel);
		this.addComponent(mLoading);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					DataStore.getCurrentInformation(mTextField.getText(), mCountry.getChosenItem().getISOCode());
					DataStore.getFiveDayForecast(mTextField.getText(), mCountry.getChosenItem().getISOCode());
				} catch (CityNotFoundException e) {
					String city = mTextField.getText();
					String country = mCountry.getChosenItem().toString();
					ApplicationFrame.removeComponent(screen);
					ApplicationFrame.addComponent(screen);
					mError.setText("Sorry " + city + " does not exist in " + country + ".");
					mError.setVisible(true);
					return;
				}
				ApplicationFrame.removeComponent(screen);
				ApplicationFrame.setCityAndCountry(mTextField.getText(), mCountry.getChosenItem());
			}
		}).start();
	}

	@Override
	public void onClicked(int x, int y) {
		this.removeComponent(mPanel);
		this.addComponent(mLoading);
		
		ApplicationFrame.removeComponent(this);
	}
	
	@Override
	protected void addedToComponent(GraphicComponent parent) {
		super.addedToComponent(parent);
		ApplicationFrame.getHomeScreen().setClickable(false);
		ApplicationFrame.getBreakdownScreen().setClickable(false);
		
		this.addComponent(mPanel);
		this.removeComponent(mLoading);
		
		mError.setText("");
		mError.setVisible(false);
		
		this.mTextField.setText(ApplicationFrame.getCity());
		this.mCountry.setChosenItem(ApplicationFrame.getISOObject());
	}

	@Override
	protected void removedFromComponent(GraphicComponent parent) {
		super.removedFromComponent(parent);
		ApplicationFrame.getHomeScreen().setClickable(true);
		ApplicationFrame.getBreakdownScreen().setClickable(true);
	}
}
