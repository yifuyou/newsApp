package com.yifuyou.recorder;

import android.annotation.SuppressLint;
import android.media.AudioDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.Date;

public class RecordItemAdapter extends RecyclerView.Adapter<RecordItemAdapter.Holder> {
    private String path;
    private String[] files;

    private boolean showDelete;
    private MediaPlayer player;


    public RecordItemAdapter(String path) {
        if (path==null || path.isEmpty()) {
            this.path = RecorderConfig.getConfig().getDefaultPath();
        } else {
            this.path = path;
        }
        System.out.println("------" + this.path);
        dataInit();
        showDelete=false;
    }

    public boolean dataInit() {

        File file = new File(path);
        if (!file.exists() || file.list()==null) {
            files = new String[]{};
        }else if (file.isDirectory()) {
            if (!Arrays.deepEquals(file.list(), files)) {
                files = file.list();
                System.out.println("list items "+ Arrays.toString(files));
                return true;
            }
        }
        System.out.println((files==null)+" "+file.isFile()+"  "+file.isDirectory());
        return false;
    }

    public void flashDate(){
        dataInit();
        notifyDataSetChanged();
    }

    public void wantDelete(boolean want){
        showDelete=want;
        notifyDataSetChanged();
    }

    public boolean deleting(){
        return this.showDelete;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item_layout, parent, false);
        return new Holder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.textView.setText(files[position]);

            holder.deleteButton.setVisibility(showDelete?View.VISIBLE:View.GONE);
            holder.imageButton.setVisibility(showDelete?View.GONE:View.VISIBLE);
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    assert v instanceof ImageButton;
                    play(files[position], (ImageButton) v);
                }
            });
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteFile(files[position]);
                }
            });

        File file = new File(this.path, files[position]);
        if (file.isFile()) {
            holder.textView_time.setText(DateFormat.getDateTimeInstance().format(new Date(file.lastModified())));
            setFileLength(file.length(), holder.textView_length);
        }

    }

    private void deleteFile(String path){
        File file=new File(this.path+"/"+path);
        if(file.exists()){
            if(file.delete()){
                Log.i("TAG", "deleteFile: "+file.delete());
                flashDate();
            }else{
                Log.i("TAG", "deleteFile: "+file.delete());
            }

        }
    }

    private void setFileLength(long length, TextView view) {
        String size = "";
        double s = length;
        if (s < 1024) {
            size = length + " byte";
        } else {
            String units = " KMGT";
            int i = 0;
            for (; s / 1000 > 1; i++) {
                s /= 1000;
            }
            size = String.valueOf(s);
            if (size.indexOf(".")<size.length()-2) {
                size=size.substring(0,size.indexOf(".")+2);
            }
            size = size.concat(" " + units.charAt(i) + "B");
        }

        view.setText(size.trim());
    }

    private void play(String path, ImageButton btn) {
        System.out.println(path);
        if (player != null && player.isPlaying()) {
            player.stop();
            player.release();
            player = null;
            btn.setImageResource(R.drawable.ic_play);
        } else {
            player = new MediaPlayer();
            try {
                player.setDataSource(this.path + "/" + path);
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        btn.post(() -> {
                            btn.setImageResource(R.drawable.ic_play);
                        });
                    }
                });
                player.prepare();
                player.start();
                btn.setImageResource(R.drawable.ic_pause);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return files.length;
    }


    static class Holder extends RecyclerView.ViewHolder {
        TextView textView, textView_length, textView_time;
        ImageButton imageButton,deleteButton;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text);
            textView_length = itemView.findViewById(R.id.file_length_text);
            textView_time = itemView.findViewById(R.id.file_time_text);
            imageButton = itemView.findViewById(R.id.img_play);
            deleteButton = itemView.findViewById(R.id.img_clear);
        }
    }


}
