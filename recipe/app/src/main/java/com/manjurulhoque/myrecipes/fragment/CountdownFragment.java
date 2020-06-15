package com.manjurulhoque.myrecipes.fragment;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.manjurulhoque.myrecipes.R;
import com.manjurulhoque.myrecipes.activity.MainActivity;
import com.manjurulhoque.myrecipes.dbhelper.FavouriteDbHelper;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.content.ContextCompat.getSystemService;
import static java.lang.Thread.sleep;

public class CountdownFragment extends Fragment {

    private static long START_TIME_IN_MILLIS = 0;
    //private int START_TIME_IN_MILLIS = 0;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private  NumberPicker mNumberPickerMin;
    private  NumberPicker mNumberPickerSec;
    private CountDownTimer mCountDownTimer;
    private LinearLayout mLinearLayout;
    private TextView mTextViewHint;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private int min = 0;
    private int sec = 0;
    private ProgressBar mProgressBar;
    private boolean mPause = false;

    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_countdown, container, false);
        mTextViewCountDown = v.findViewById(R.id.text_view_countdown);
        mButtonStartPause = v.findViewById(R.id.button_start_pause);
        mLinearLayout = v.findViewById(R.id.linerLayout_setTime);
        mButtonReset = v.findViewById(R.id.button_reset);
        mNumberPickerMin = v.findViewById(R.id.picker_min);
        mNumberPickerSec = v.findViewById(R.id.picker_sec);
        mTextViewHint = v.findViewById(R.id.textView_hint);
        mProgressBar = v.findViewById(R.id.circular_progress_bar);


        mNumberPickerMin.setMinValue(0);
        mNumberPickerMin.setMaxValue(59);
        mNumberPickerMin.setValue(0); // 設定預設位置
        mNumberPickerMin.setWrapSelectorWheel(true); // 是否循環顯示
        mNumberPickerMin.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        mNumberPickerSec.setMinValue(0);
        mNumberPickerSec.setMaxValue(59);
        mNumberPickerSec.setValue(0); // 設定預設位置
        mNumberPickerSec.setWrapSelectorWheel(true); // 是否循環顯示
        mNumberPickerSec.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                    mPause = true;
                } else {
                    if(mPause == false)
                    {
                        min = mNumberPickerMin.getValue();
                        sec = mNumberPickerSec.getValue();
                        if(min == 0 && sec == 0)
                        {
                            Toast.makeText(getActivity(),"Please set the time",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            int time = 60000 * min + 1000 * sec;
                            mTextViewHint.setText("START");
                            mLinearLayout.setVisibility(View.INVISIBLE);
                            mTimeLeftInMillis = time;
                            mProgressBar.setVisibility(View.VISIBLE);
                            mProgressBar.setProgress(0);
                            mProgressBar.setMax(time);
                            startTimer();

                        }
                    }
                    else
                    {
                        startTimer();
                        mPause = false;
                    }
                }
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        updateCountDownText();
        return v;
    }



    private void startTimer() {

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                mPause = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
                mTextViewHint.setText("END");
                mTextViewCountDown.setText("00:00");
                mProgressBar.setProgress(100);
                mProgressBar.setVisibility(View.INVISIBLE);
                mTextViewCountDown.setText("00:00");
            }
        }.start();
        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
        mTextViewHint.setText("PAUSE");
    }
    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mPause = false;
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
        mLinearLayout.setVisibility(View.VISIBLE);
        mTextViewHint.setText("SET TIME");
        mProgressBar.setProgress(0);
    }
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60 + 1;
        mProgressBar.setProgress((int)mTimeLeftInMillis);

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);

    }


    public void startService(int duration)
    {
        int d = duration;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Timer", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("duration", d);
        editor.apply();
        Intent intent = new Intent(getActivity(),MyService.class);
        getActivity().startService(intent);
        if((d / 1000) / 60 == 0)
        {
            Toast.makeText(getActivity(), "COUNTDOWN " + (d / 1000) % 60 + "s",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getActivity(), "COUNTDOWN " + (d / 1000) / 60 + "m " + (d / 1000) % 60 + "s",Toast.LENGTH_SHORT).show();
        }
    }
}
