package risabh_mishra.olaandroidplay;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;

/**
 * Created by Risabh Mishra on 12/16/2017.
 */

public class favadapter extends RecyclerView.Adapter<favadapter.MyViewHolder> {

    public List<Songs> favsongs;
    private Context ctx;
    private SharedPreference sharedPreference;

    public favadapter(List<Songs> favsongs, Context context) {
        this.favsongs = favsongs;
        this.ctx=context;
        sharedPreference = new SharedPreference();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.name.setText(favsongs.get(position).getSong());
        holder.artist.setText("Artists: " + favsongs.get(position).getArtist());
        Glide.with(ctx).load(favsongs.get(position).getCoverImage()).into(holder.imageView);

        holder.play.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    funcplay(favsongs.get(position).getURL(), 1);
                } else {
                    funcplay(favsongs.get(position).getURL(), 0);

                }
            }
        });




        holder.fav.setChecked(true);

        if (checkFavoriteItem(favsongs.get(position))) {
            holder.fav.setChecked(true);
        } else {
            holder.fav.setChecked(false);
        }

        holder.fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    sharedPreference.addFavorite(ctx,favsongs.get(position));

                } else {
                    sharedPreference.removeFavorite(ctx,favsongs.get(position));
                }
            }
        });



    }


    public boolean checkFavoriteItem(Songs songs) {
        boolean check = false;
        List<Songs> favorites = sharedPreference.getFavorites(ctx);
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

        final MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try{
            mediaPlayer.setDataSource(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try{
            mediaPlayer.prepare(); // It might take long time to prepare.
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if(number==1){
            mediaPlayer.start();
        }
        else{mediaPlayer.stop();}

    }


    @Override
    public int getItemCount() {
        return favsongs.size();
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
            fav = (ToggleButton) itemView.findViewById(R.id.switch_fav);
            download = (ImageButton)itemView.findViewById(R.id.but_dwnld);
        }
    }

}
