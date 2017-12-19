package risabh_mishra.olaandroidplay;

import android.app.DownloadManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Risabh Mishra on 12/16/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.MyViewHolder> implements Filterable{

    private ArrayList<Songs> songs;
    private ArrayList<Songs> filteredsongs;
    private Context ctx;
    SharedPreference sharedPreference;
    MediaPlayer mediaPlayer;



    public RecyclerViewAdapter(ArrayList<Songs> songs, Context ctx) {
        this.songs = songs;
        this.ctx = ctx;
        this.filteredsongs=songs;
        sharedPreference=new SharedPreference();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_view,parent,false);



       mediaPlayer  = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.name.setText(filteredsongs.get(position).getSong());
        holder.artist.setText("Artists: " + filteredsongs.get(position).getArtist());
        Glide.with(ctx).load(filteredsongs.get(position).getCoverImage()).into(holder.imageView);

        holder.play.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    funcplay(filteredsongs.get(position).getURL(), 1);
                } else {
                    funcplay(filteredsongs.get(position).getURL(), 0);

                }
            }
        });


         /*If a product exists in shared preferences then set heart_red drawable
         * and set a tag*/

        if (checkFavoriteItem(filteredsongs.get(position))) {
            holder.fav.setChecked(true);
        } else {
            holder.fav.setChecked(false);
        }

        holder.fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    sharedPreference.addFavorite(ctx,filteredsongs.get(position));

                } else {
                       sharedPreference.removeFavorite(ctx,filteredsongs.get(position));
                }
            }
        });



        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DownloadManager downloadmanager = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(filteredsongs.get(position).getURL());
                File destination = new File(ctx.getExternalFilesDir(null), "olaplay");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle(filteredsongs.get(position).getSong()+".mp3");
                request.setDescription("Downloading");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationUri(Uri.fromFile(destination));
                downloadmanager.enqueue(request);


            }});

    }

    /*Checks whether a particular product exists in SharedPreferences*/
    public boolean checkFavoriteItem(Songs songs) {
        boolean check = false;
        ArrayList<Songs> favorites = sharedPreference.getFavorites(ctx);
        if (favorites != null) {
            for (Songs songsi : favorites) {
                if (songsi.equals(songs)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    private void funcplay(String url,int number) {

        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }

        try{
            mediaPlayer.setDataSource(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try{
            mediaPlayer.prepare();
            // It might take long time to prepare.
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(number==1){
            mediaPlayer.start();
        }
        else{mediaPlayer.stop();}

    }

    @Override
    public int getItemCount() {
        return filteredsongs.size();
    }



    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if(charString.isEmpty()){
                    filteredsongs = songs;
                }
                 else{
                    ArrayList<Songs> filteredlist = new ArrayList<Songs>();

                    for(Songs songs:filteredsongs){
                        if(songs.getSong().toLowerCase().contains(charString)||songs.getArtist().toLowerCase().contains(charString))
                        {
                            filteredlist.add(songs);
                        }
                    }
                    filteredsongs = filteredlist;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredsongs;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
               filteredsongs = (ArrayList<Songs>)filterResults.values;
               notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

       ImageView imageView;
       TextView artist,name;
       ImageButton download;
       ToggleButton play,fav;


        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.cover_image);
            name = (TextView)itemView.findViewById(R.id.song_name);
            artist = (TextView)itemView.findViewById(R.id.song_artist);
            play = (ToggleButton) itemView.findViewById(R.id.but_play);
            play.setTextOff(null);
            play.setText(null);
            play.setTextOn(null);
            fav = (ToggleButton) itemView.findViewById(R.id.switch_fav);
            fav.setTextOn(null);
            fav.setTextOff(null);
            fav.setText(null);
            download = (ImageButton)itemView.findViewById(R.id.but_dwnld);
        }
    }



}
