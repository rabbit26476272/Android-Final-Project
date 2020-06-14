package com.manjurulhoque.myrecipes.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.manjurulhoque.myrecipes.R;
import com.manjurulhoque.myrecipes.dbhelper.FavouriteDbHelper;

import org.w3c.dom.Text;

import java.util.Locale;

public class CountdownFragment extends Fragment {

    private static long START_TIME_IN_MILLIS = 0;
    //private int START_TIME_IN_MILLIS = 0;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private EditText mEditTextMin;
    private EditText mEditTextSec;
    private TextView mTextViewDot;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private int min = 0;
    private int sec = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_countdown, container, false);
        mTextViewCountDown = v.findViewById(R.id.text_view_countdown);
        mButtonStartPause = v.findViewById(R.id.button_start_pause);
        mButtonReset = v.findViewById(R.id.button_reset);
        mEditTextMin = v.findViewById(R.id.editText_min);
        mEditTextSec = v.findViewById(R.id.editText_sec);
        mTextViewDot = v.findViewById(R.id.textView_dot);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    //Toast.makeText(getActivity(),Integer.toString(mEditTextMin.getText().toString().length()),Toast.LENGTH_SHORT).show();

                    if(mEditTextMin.getText().toString().length() == 0 && mEditTextSec.getText().toString().length() == 0)
                    {
                        Toast.makeText(getActivity(),"請設定時間",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(mEditTextMin.getText().toString().length() == 0)
                        {
                            min = 0;
                        }
                        else{
                            min = Integer.valueOf(mEditTextMin.getText().toString());
                        }
                        if(mEditTextSec.getText().toString().length() == 0)
                        {
                            sec = 0;
                        }
                        else
                        {
                            sec = Integer.valueOf(mEditTextSec.getText().toString());
                        }
                        int time = 60000 * min + 1000 * sec;
                        Toast.makeText(getActivity(),"開始倒數",Toast.LENGTH_SHORT).show();

                        mEditTextMin.setVisibility(View.INVISIBLE);
                        mEditTextSec.setVisibility(View.INVISIBLE);
                        mTextViewDot.setVisibility(View.INVISIBLE);
                        mTimeLeftInMillis = time;
                        startTimer();
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
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),"結束倒數",Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getActivity(),"暫停倒數",Toast.LENGTH_SHORT).show();
    }
    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
        mEditTextMin.setVisibility(View.VISIBLE);
        mEditTextSec.setVisibility(View.VISIBLE);
        mTextViewDot.setVisibility(View.VISIBLE);
    }
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

}
