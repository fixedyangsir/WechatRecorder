package com.jackie.wechatrecorder.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jackie.wechatrecorder.R;

/**
 * Created by Jackie on 2016/1/2.
 */
public class DialogManager {
    private Context mContext;

    private Dialog mDialog;
    private ImageView mRecorderIcon;
    private ImageView mRecorderVolume;
    private TextView mRecorderLabel;

    public DialogManager(Context context) {
        this.mContext = context;

        mDialog = new Dialog(context, R.style.AudioDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_recorder, null);
        mDialog.setContentView(view);

        mRecorderIcon = (ImageView) view.findViewById(R.id.dialog_recorder_icon);
        mRecorderVolume = (ImageView) view.findViewById(R.id.dialog_recorder_volume);
        mRecorderLabel = (TextView) view.findViewById(R.id.dialog_recorder_label);

        mDialog.show();
    }

    public void showRecordingDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mRecorderIcon.setVisibility(View.VISIBLE);
            mRecorderVolume.setVisibility(View.VISIBLE);
            mRecorderLabel.setVisibility(View.VISIBLE);

            mRecorderIcon.setImageResource(R.drawable.recorder);
            mRecorderLabel.setText("手指上滑，取消发送");
        }
    }

    public void showCancelDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mRecorderIcon.setVisibility(View.VISIBLE);
            mRecorderVolume.setVisibility(View.GONE);
            mRecorderLabel.setVisibility(View.VISIBLE);

            mRecorderIcon.setImageResource(R.drawable.cancel);
            mRecorderLabel.setText("松开手指，取消发送");
        }
    }

    public void showTooShortDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mRecorderIcon.setVisibility(View.VISIBLE);
            mRecorderVolume.setVisibility(View.GONE);
            mRecorderLabel.setVisibility(View.VISIBLE);

            mRecorderIcon.setImageResource(R.drawable.voice_too_short);
            mRecorderLabel.setText("录音时间太短");
        }
    }

    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    /**
     * 根据level去更新volume上的图片
     * @param level 1-7
     */
    public void updateVolumeLevel(int level) {
        if (mDialog != null && mDialog.isShowing()) {
            int resId = mContext.getResources().getIdentifier("volume" + level, "drawable", mContext.getPackageName());
            mRecorderVolume.setImageResource(resId);
        }
    }
}
