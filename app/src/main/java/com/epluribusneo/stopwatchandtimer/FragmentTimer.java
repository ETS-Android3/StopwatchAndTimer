package com.epluribusneo.stopwatchandtimer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTimer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTimer extends Fragment {

	private TextView tvTimer;
	private Button btnStartPause;
	private Button btnStop;
	private SeekBar sbMinuts;
	private SeekBar sbSeconds;

	private boolean  isStarted = false;
	private int minutes = 0;
	private int seconds = 0;
	private int allSeconds = 1;

	private long timeInMilliseconds = 0L;

	CountDownTimer countDownTimer;


	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	public FragmentTimer() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment FragmentTimer.
	 */
	// TODO: Rename and change types and number of parameters
	public static FragmentTimer newInstance(String param1, String param2) {
		FragmentTimer fragment = new FragmentTimer();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		//return inflater.inflate(R.layout.fragment_timer, container, false);
		final View rootView = inflater.inflate(R.layout.fragment_timer, container, false);

		tvTimer = (TextView)rootView.findViewById(R.id.tvTimer);
		btnStartPause = (Button)rootView.findViewById(R.id.btnStartPause);
		btnStop = (Button)rootView.findViewById(R.id.btnStop);
		sbMinuts = (SeekBar)rootView.findViewById(R.id.sbMinuts);
		sbSeconds = (SeekBar)rootView.findViewById(R.id.sbSeconds);

		sbSeconds.setOnSeekBarChangeListener(seekBarChangeListener);
		sbMinuts.setOnSeekBarChangeListener(seekBarChangeListener);


		btnStartPause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(isStarted == false){
					long lll = allSeconds * 1000;
					countDownTimer = new CountDownTimer(lll, 1000) {
						@Override
						public void onTick(long l) {
							long sec  = l/1000;
							long min = sec / 60;
							sec = sec % 60;

							sbSeconds.setProgress((int) sec);
							sbMinuts.setProgress((int)min);
						}

						@Override
						public void onFinish() {
							//todo
							sbSeconds.setProgress(0);
							sbMinuts.setProgress(0);
							isStarted = false;
							theEnd();
							showToast("FINISH");
						}
					}.start();
					isStarted = true;
					btnStartPause.setText("Pause");
				}else{
					theEnd();
					isStarted = false;
					btnStartPause.setText("Start");
				}
			}
		});


		btnStop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				sbMinuts.setProgress(0);
				sbSeconds.setProgress(0);
				countDownTimer.cancel();

				isStarted = false;
				tvTimer.setText("00:00");
			}
		});

		return rootView;
	}


	SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
		@Override
		public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
			updateTime();
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {

		}
	};

	private void updateTime(){
		seconds = sbSeconds.getProgress();
		minutes = sbMinuts.getProgress();

		allSeconds = (int)((minutes * 60) + seconds);

		tvTimer.setText("" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
	}

	private void showToast(String msg){
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}

	private void theEnd(){
		isStarted = false;
		countDownTimer.cancel();
	}

}