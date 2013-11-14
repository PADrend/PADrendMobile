package de.upb.mobilerendering.gui;

import de.upb.mobilerendering.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class BudgetSlider extends LinearLayout implements OnSeekBarChangeListener {
	private static final int BUDGET_STEP = 100000;
	private static final int BUDGET_MAX = 5000000;
	
	private TextView _textView;
	private SeekBar _seekBar;
	
	public BudgetSlider(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(LinearLayout.VERTICAL);

		LayoutInflater.from(context).inflate(R.layout.budgetslider, this, true);
		
		int initialValue = BUDGET_MAX / 10;
		
		_textView = (TextView) findViewById(R.id.budgetSliderTextView);
		_textView.setText(String.format("%,d", initialValue));
		
		_seekBar = (SeekBar) findViewById(R.id.budgetSliderSeekBar);
		_seekBar.setMax(BUDGET_MAX);
		_seekBar.setProgress(initialValue);
		_seekBar.setKeyProgressIncrement(BUDGET_STEP);
		_seekBar.setOnSeekBarChangeListener(this);
	}
	
	public int getBudget() {
		return _seekBar.getProgress();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if(fromUser) {
			// Round progress value to multiples of BUDGET_STEP
			_seekBar.setProgress(progress / BUDGET_STEP * BUDGET_STEP);
		}
		_textView.setText(String.format("%,d", getBudget()));
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}
}
