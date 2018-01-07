package com.example.marut.dsg_test;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import adapter.VenueAdapter;
import bean.VenueDetail;
import rest.HttpRequestSingleton;
import rest.ServerUtils;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    VenueAdapter madapter;
    LinkedList<VenueDetail> venueDetailList;
    protected ProgressDialog pDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        venueDetailList= new LinkedList<>();

        pDialog = new ProgressDialog(this);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setMessage("loading");
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        madapter= new VenueAdapter(this,venueDetailList);
        recyclerView.setAdapter(madapter);

        if(venueDetailList.size()>0){
        Collections.sort(venueDetailList, new Comparator<VenueDetail>() {
            @Override
            public int compare(VenueDetail lhs, VenueDetail rhs) {
                return lhs.getLatitute().compareTo(rhs.getLatitute());
            }
        });
        }

        if(ServerUtils.isConnectingToInternet(this)){
            venueWebservice();
        }else{
            Toast.makeText(this,getResources().getString(R.string.internet_connection),Toast.LENGTH_SHORT).show();
        }

    }


    private void venueWebservice() {
        pDialog.show();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("", "");

        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, ServerUtils.URL, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        Log.v("venue", " Success ==="+response);

                        try{

                                JSONArray array = response.optJSONArray("venues");
                                if(array!=null && array.length()>0){

                                    for(int i=0;i<array.length(); i++){

                                        JSONObject obj=array.optJSONObject(i);
                                        VenueDetail venueDetails= new VenueDetail();
                                        venueDetails.setId(obj.getInt("id"));
                                        venueDetails.setName(obj.getString("name"));
                                        venueDetails.setVerified(obj.getString("verified"));
                                        venueDetails.setUrl(obj.getString("url"));
                                        venueDetails.setRationColor(obj.getString("ratingColor"));
                                        venueDetails.setRatingSignals(obj.getString("ratingSignals"));
                                        venueDetails.setRating(obj.getString("rating"));
                                        venueDetails.setStoreId(obj.getInt("storeId"));


                                        JSONObject jlocation=obj.getJSONObject("location");
                                        venueDetails.setAddress( jlocation.getString("address"));
                                        venueDetails.setLatitute( jlocation.getString("latitude"));
                                        venueDetails.setLangitute( jlocation.getString("longitude"));
                                        venueDetails.setPostalCode( jlocation.getString("postalCode"));
                                        venueDetails.setCc( jlocation.getString("cc"));
                                        venueDetails.setCity( jlocation.getString("city"));
                                        venueDetails.setState( jlocation.getString("state"));
                                        venueDetails.setCountry( jlocation.getString("country"));


                                        JSONArray arrayContact=obj.getJSONArray("contacts");
                                        for(int j=0;j<arrayContact.length();i++){
                                            JSONObject objcontact=arrayContact.optJSONObject(i);
                                            venueDetails.setPhone( objcontact.getString("phone"));
                                            venueDetails.setTwitter( objcontact.getString("twitter"));
                                            venueDetails.setFacebook( objcontact.getString("facebook"));
                                            venueDetails.setFaceBookName( objcontact.getString("facebookName"));
                                        }


                                        JSONArray arrayPhoto=obj.getJSONArray("photos");
                                        for(int j=0;j<arrayPhoto.length();i++){
                                            JSONObject objphoto=arrayPhoto.optJSONObject(i);
                                            venueDetails.setPhotoId( objphoto.getInt("photoId"));
                                            venueDetails.setCreatedAt( objphoto.getInt("createdAt"));
                                            venueDetails.setPhoto_url( objphoto.getString("url"));

                                        }

                                        venueDetailList.add(venueDetails);

                                    }
                                        madapter.notifyDataSetChanged();

                                }

                        } catch (JSONException e) {
                            pDialog.dismiss();
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        pDialog.dismiss();
                        Log.v("FD", " receiveReport  Error ==="+error.getMessage());
                    }
                });

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        HttpRequestSingleton.getInstance(MainActivity.this).addToRequestQueue(jsObjRequest);


    }

}
