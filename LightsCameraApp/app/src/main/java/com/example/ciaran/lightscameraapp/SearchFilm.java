package com.example.ciaran.lightscameraapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ciara on 18/06/2017.
 */

public class SearchFilm extends Activity implements View.OnClickListener {
    private String apiKey="d03bfab0d35dab0521519898789842c5";
    private TextView searchResult;
    private String search;
    private ArrayList<String> filmNames=new ArrayList<String>();
    private ArrayList<TextView> links=new ArrayList<TextView>();
    //private LinearLayout linearlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_film_page);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.search_scroll_layout);
        filmNames= (ArrayList<String>) getIntent().getSerializableExtra("myFilms");
        if(filmNames != null) {
            for (String title : filmNames) {
                TextView textView = new TextView(this);
                textView.setText(title);
                textView.setOnClickListener(this);
                links.add(textView);
                linearLayout.addView(textView);
            }
        }
    }
    //@Override
    public void searchFilms(View view){
        searchResult=(TextView) findViewById(R.id.search_result);
        EditText editTextSearch = (EditText) findViewById(R.id.film_search);
        search = editTextSearch.getText().toString().trim();
        //change white spaces to %20 for the url
        search=search.replaceAll(" ","%20");
        //clears texfield once a button is hit again
        searchResult.setText("");
        new GetFilmSearch().execute();
        //Toast.makeText(this,"test",Toast.LENGTH_SHORT).show();
    }
    public void onClick(View view){
        for(TextView tv: links){
            if(view==tv){
                String film = tv.getText().toString().trim();
                Intent intent = new Intent(this,AddFilm.class);
                intent.putExtra("title", film);
                startActivity(intent);
                finish();
            }
        }
    }
    class GetFilmSearch extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... urls) {
            try {
                URL url = new URL("https://api.themoviedb.org/3/search/movie?query="+search+"&api_key="+apiKey+
                        "&language=en-US&page=1&include_adult=false");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            try {
                filmNames =getFilmsFromJSON(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(SearchFilm.this,SearchFilm.class);
            intent.putExtra("myFilms", filmNames);
            startActivity(intent);
        }

        public ArrayList<String> getFilmsFromJSON(String json) throws JSONException {

            JSONObject jsonObj = new JSONObject(json);
            JSONArray filmList = jsonObj.getJSONArray("results");
            ArrayList<String> filmNames=new ArrayList<String>();
            for(int i = 0; i<filmList.length();i++) {
                JSONObject film = filmList.getJSONObject(i);
                filmNames.add((film.getString("title")));
            }
            return filmNames;
        }
    }
}
