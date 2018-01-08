package adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.marut.dsg_test.R;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import bean.VenueDetail;


/**
 * Created by marut on 1/7/2018.
 */

public class VenueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context m_context;
    ProgressDialog pDialog;

    private List<VenueDetail> detailsList;


    public VenueAdapter(Context context, LinkedList<VenueDetail> detailsList) {
        this.m_context = context;
        this.detailsList=detailsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(m_context).inflate(R.layout.venue_row, parent, false);

        return new VenueViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof VenueAdapter.VenueViewHolder) {

            VenueAdapter.VenueViewHolder view_holder = (VenueAdapter.VenueViewHolder) holder;
            final VenueDetail details = detailsList.get(position);

            view_holder.txt_name.setText(details.getName());
            view_holder.txt_verified.setText(details.getVerified());
            view_holder.txt_ratingcolor.setText(details.getRationColor());
            view_holder.txt_ratingsignal.setText(details.getRatingSignals());
            view_holder.txt_rating.setText(details.getRating());
            view_holder.txt_storeid.setText(details.getStoreId());

            view_holder.txt_address.setText(details.getAddress());
            view_holder.txt_city.setText(details.getCity());
            view_holder.txt_latitiue.setText(details.getLatitute());
            view_holder.txt_longitute.setText(details.getLangitute());
            view_holder.txt_cc.setText(details.getCc());
            view_holder.txt_state.setText(details.getState());
            view_holder.txt_country.setText(details.getCountry());

            view_holder.txt_phone.setText(details.getPhone());
            view_holder.txt_twitter.setText(details.getTwitter());
            view_holder.txt_fb.setText(details.getFacebook());
            view_holder.txt_fbname.setText(details.getFaceBookName());
            view_holder.txt_photoid.setText(details.getPhotoId());
            view_holder.txt_createrid.setText(String.valueOf(details.getCreatedAt()));


            Picasso.with(m_context).load(details.getPhoto_url()).resize(100, 100).into(view_holder.img_photoUrl);
            Picasso.with(m_context).load(details.getUrl()).resize(100, 100).into(view_holder.img_url);
        }
    }

    @Override
    public int getItemCount() {

        return detailsList.size();
    }

    public static class VenueViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name,txt_verified,txt_ratingcolor,txt_ratingsignal,txt_rating,txt_storeid,
                txt_address,txt_city,txt_latitiue,txt_longitute,txt_cc,txt_state,txt_country,
                txt_phone,txt_twitter,txt_fb,txt_fbname,txt_photoid,txt_createrid;
        ImageView img_photoUrl,img_url;

        public VenueViewHolder(View itemView) {
            super(itemView);

            img_photoUrl = (ImageView) itemView.findViewById(R.id.img_photourl);
            img_url = (ImageView) itemView.findViewById(R.id.img_url);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_verified = (TextView) itemView.findViewById(R.id.txt_verifed);
            txt_ratingcolor = (TextView) itemView.findViewById(R.id.txt_ratingcolor);
            txt_ratingsignal = (TextView) itemView.findViewById(R.id.txt_ratingsignal);
            txt_rating = (TextView) itemView.findViewById(R.id.txt_rating);
            txt_storeid = (TextView) itemView.findViewById(R.id.txt_storeid);

            txt_address = (TextView) itemView.findViewById(R.id.txt_address);
            txt_city = (TextView) itemView.findViewById(R.id.txt_city);
            txt_latitiue = (TextView) itemView.findViewById(R.id.txt_latitute);
            txt_longitute = (TextView) itemView.findViewById(R.id.txt_longitute);
            txt_cc = (TextView) itemView.findViewById(R.id.txt_cc);
            txt_state = (TextView) itemView.findViewById(R.id.txt_state);
            txt_country = (TextView) itemView.findViewById(R.id.txt_country);

            txt_phone = (TextView) itemView.findViewById(R.id.txt_phone);
            txt_twitter = (TextView) itemView.findViewById(R.id.txt_twitter);
            txt_fb = (TextView) itemView.findViewById(R.id.txt_facebook);
            txt_fbname= (TextView) itemView.findViewById(R.id.txt_facebookname);

            txt_photoid = (TextView) itemView.findViewById(R.id.txt_photoid);
            txt_createrid = (TextView) itemView.findViewById(R.id.txt_createdid);

        }

    }

}