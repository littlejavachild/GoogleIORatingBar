package com.fasih.googleioratingbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GoogleIORatingBar extends LinearLayout{
	
	// TextViews to hold the different ratings
	private TextView one = null;
	private TextView two = null;
	private TextView three = null;
	private TextView four = null;
	private TextView five = null;
	
	// This will be used to change colors
	private TextView currentRating = null;
	
	// Used to inflater a layout
	private static final String INF_SERVICE = Context.LAYOUT_INFLATER_SERVICE;
	private LayoutInflater inflater;
	
	// Used to listen for user clicks
	private View.OnClickListener clickListener = null;
	
	// Used to convey rating changes to the outside world
	private RatingChangeListener rcListener  = null;
	
	//------------------------------------------------------------------------------
	public GoogleIORatingBar(Context context, AttributeSet attr){
		super(context,attr);
		init();
	}
	//------------------------------------------------------------------------------
	public GoogleIORatingBar(Context context){
		super(context);
		init();
	}
	//------------------------------------------------------------------------------
	/**
	 * 
	 * @param color The color of the text
	 * @param v View that was clicked
	 */
	private void setTextColor(int color, TextView v){
		v.setTextColor(color);
	}
	//------------------------------------------------------------------------------
	/**
	 * 
	 * @param color The color of the background
	 * @param v View that was clicked
	 */
	private void setBackgroundDrawable(Drawable background, TextView v){
		v.setBackground(background);
	}
	//------------------------------------------------------------------------------
	/**
	 * 
	 * @return
	 */
	public int getRating(){
		// If user has not clicked anything, the rating is 0
		if(currentRating == null){
			return 0;
		}
		// Else we derive rating based on what view was last clicked
		int viewId = currentRating.getId();
		switch(viewId){
		case R.id.one:
			return 1;
		case R.id.two:
			return 2;
		case R.id.three:
			return 3;
		case R.id.four:
			return 4;
		case R.id.five:
			return 5;
		default:
			return 0;
		}
	}
	//------------------------------------------------------------------------------
	private void init(){
		inflater = (LayoutInflater) getContext().getSystemService(INF_SERVICE);
		inflater.inflate(R.layout.google_rating_bar, this, true);
		
		one = (TextView) findViewById(R.id.one);
		two = (TextView) findViewById(R.id.two);
		three = (TextView) findViewById(R.id.three);
		four = (TextView) findViewById(R.id.four);
		five = (TextView) findViewById(R.id.five);
		
		clickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// If this is the first time the user is
				// clicking on a rating
				if(currentRating == null){
					// We set the text color to white
					setTextColor(getResources().getColor(android.R.color.white), (TextView)v);
					// and the background to blue
					setBackgroundDrawable(getResources().getDrawable(R.drawable.filled_circle),(TextView)v);
					// and we keep a track of the rating that has been clicked
					currentRating = (TextView) v;
					// If we have a listener attached, we should notify it, too.
					if(rcListener != null)
						rcListener.onRatingChanged(getRating());
				}else{ // If this is not the first time the user is clicking the rating
					// We first reset the currentRating
					// Set text color to blue
					setTextColor(getResources().getColor(R.color.blue),currentRating);
					// and the background to transparent
					setBackgroundDrawable(getResources().getDrawable(R.drawable.stroked_circle), currentRating);
					// We then set the colors for the new TextView, same as above
					setTextColor(getResources().getColor(android.R.color.white), (TextView)v);
					setBackgroundDrawable(getResources().getDrawable(R.drawable.filled_circle),(TextView)v);
					currentRating = (TextView) v;
					if(rcListener != null)
						rcListener.onRatingChanged(getRating());
				}
			}
		};
		
		// We assign the listener to the 
		one.setOnClickListener(clickListener);
		two.setOnClickListener(clickListener);
		three.setOnClickListener(clickListener);
		four.setOnClickListener(clickListener);
		five.setOnClickListener(clickListener);
	}
	//------------------------------------------------------------------------------
	/**
	 * 
	 * @param listener
	 */
	public void setRatingChangeListener(GoogleIORatingBar.RatingChangeListener listener){
		rcListener = listener;
	}
	//------------------------------------------------------------------------------
	/**
	 * 
	 * @author Fasih
	 *
	 */
	public static interface RatingChangeListener{
		void onRatingChanged(int rating);
	}
	//------------------------------------------------------------------------------
}
