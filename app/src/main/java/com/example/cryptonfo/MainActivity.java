package com.example.cryptonfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cryptonfo.Adapters.CryptoRvAdapter;
import com.example.cryptonfo.Modals.CryptoRvModal;
import com.example.cryptonfo.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    RecyclerView rv;
    private ArrayList<CryptoRvModal> list;
    private CryptoRvAdapter ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        rv=findViewById(R.id.RV);
        list= new ArrayList<CryptoRvModal>();
        ad= new CryptoRvAdapter(list,this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(ad);
        getCryptoData();


    }

    private void getCryptoData() {
        binding.progressBar.setVisibility(View.VISIBLE);
        String url="https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                binding.progressBar.setVisibility(View.GONE);

                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for(int i=0;i<dataArray.length();i++)
                    {
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        String name = dataObj.getString("name");
                        String symbol = dataObj.getString("symbol");
                        JSONObject quote = dataObj.getJSONObject("quote");
                        JSONObject USD = quote.getJSONObject("USD");
                        double price = USD.getDouble("price");
                        list.add(new CryptoRvModal(name,symbol,price));
                    }
                    ad.notifyDataSetChanged();
                } catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Fail to extract json data!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Fail to load!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY","59398f09-1f51-4e66-9be4-a71af05d56a4");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}