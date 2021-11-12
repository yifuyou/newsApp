package com.yifuyou.recorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import java.io.File;
import java.io.IOException;


/**
 * <uses-permission android:name="android.permission.RECORD_AUDIO"/>
 * <p>
 * <p>
 * <androidx.fragment.app.FragmentContainerView
 * android:id="@+id/fragment_recorder"
 * android:layout_width="match_parent"
 * android:layout_height="wrap_content"
 * />
 * <p>
 * getSupportFragmentManager().beginTransaction().add(R.id.fragment_recorder,new RecorderFragment(),"recorder").commitNow();
 * <p>
 * for (String s : new String[]{Manifest.permission.RECORD_AUDIO}) {
 * if (ContextCompat.checkSelfPermission(this, s) != PackageManager.PERMISSION_GRANTED) {
 * System.out.println("request 2022");
 * requestPermissions(new String[]{s}, 2022);
 * }
 * }
 */
enum MediaType {
    PLAY,
    NON,
    RECORDER,
};

public class RecorderFragment extends Fragment {
    private final String TAG = "RecorderFragment";
    private String filePath;

    ImageButton footerBtn1, footerBtn2;
    MediaType type = MediaType.NON;

    File dataSourceFile;
    MediaRecorder recorder = new MediaRecorder();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recorder_fragment, container, false);

        filePath = RecorderConfig.getConfig().getDefaultPath();
        footerBtn1 = view.findViewById(R.id.button_footer_1);
        footerBtn2 = view.findViewById(R.id.button_footer_2);
        footerBtn1.setOnClickListener((v) -> {

            if (type != MediaType.NON) {
                return;
            }
            try {
                mediaRecorderInit();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "0-0--00---00; 录音开始---");
            recorder.start();
            Toast.makeText(getContext(),"录音开始---",Toast.LENGTH_SHORT).show();
            type = MediaType.RECORDER;
        });
        footerBtn2.setOnClickListener((v) -> {
            if (type != MediaType.RECORDER) {
                return;
            }
            recorder.stop();
            type = MediaType.NON;
            Toast.makeText(getContext(),"录音结束---",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "0-0--00---00; 录音结束---");
        });

        return view;
    }

    void mediaRecorderInit() throws IOException {
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        File parent = new File( filePath);
        if (!parent.exists() && !parent.mkdirs()) {
            boolean mkdirs = parent.mkdir();
            Log.i(TAG, "mediaRecorderInit: dir create " + mkdirs + "  " + parent.isDirectory());
        }
        Log.i(TAG, "mediaRecorderInit: dir exists?  -" + (parent.exists() ? parent.getAbsolutePath() : "false"));
        dataSourceFile = File.createTempFile("media_", ".amr", parent);

        recorder.setOutputFile(dataSourceFile);
        recorder.prepare();
    }


    @Override
    public void onDestroyView() {
        recorder.release();
        recorder = null;
        super.onDestroyView();
    }
}
