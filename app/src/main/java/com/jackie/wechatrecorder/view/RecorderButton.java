package com.jackie.wechatrecorder.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.jackie.wechatrecorder.R;

/**
 * Created by Jackie on 2016/1/2.
 * 录音按钮
 */
public class RecorderButton extends Button {
    private static final int DISTANCE_Y_CANCEL = 50;

    private static final int STATE_NORMAL = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_CANCEL = 3;
    private int mCurrentState = STATE_NORMAL;

    private boolean mIsRecording = false;  //是否开始录音

    private DialogManager mDialogManager;

    public RecorderButton(Context context) {
        this(context, null);
    }

    public RecorderButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //TODO 真正录音开始是在audio prepare之后
                mDialogManager = new DialogManager(getContext());
                mIsRecording = true;
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                changeState(STATE_RECORDING);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsRecording) {
                    if (wantToCancel(x, y)) {
                        changeState(STATE_CANCEL);
                    } else {
                        changeState(STATE_RECORDING);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mCurrentState == STATE_RECORDING) {
                    //正常结束
                    //release -> callback保存录音
                    mDialogManager.dismissDialog();;
                } else if (mCurrentState == STATE_CANCEL) {
                    //cancel
                    mDialogManager.dismissDialog();
                }
                reset();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void changeState(int state) {
        if (mCurrentState != state) {
            mCurrentState = state;
            switch (state) {
                case STATE_NORMAL:
                    setBackgroundResource(R.drawable.bg_recorder_normal);
                    setText(R.string.state_normal);
                    break;
                case STATE_RECORDING:
                    setBackgroundResource(R.drawable.bg_recorder_recording);
                    setText(R.string.state_recording);

                    if (mIsRecording) {
                        mDialogManager.showRecordingDialog();
                    }
                    break;
                case STATE_CANCEL:
                    setBackgroundResource(R.drawable.bg_recorder_recording);
                    setText(R.string.state_cancel);

                    mDialogManager.showCancelDialog();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 根据x,y的坐标判断是否需要取消
     * @param x    x轴坐标
     * @param y    y轴坐标
     * @return     是否需要取消
     */
    private boolean wantToCancel(int x, int y) {
        if (x < 0 || x > getWidth()) {
            return true;
        } else {
            if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
                return true;
            }
        }

        return false;
    }

    /**
     * 恢复状态及标志位
     */
    private void reset() {
        mIsRecording = false;
        changeState(STATE_NORMAL);
    }
}
