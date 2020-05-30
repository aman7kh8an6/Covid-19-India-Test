package com.example.covid_19india;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

     TextView st, cnf, cur, dead, tot;

    //    https://api.covid19india.org/state_district_wise.json
//    http://covid19-india-adhikansh.herokuapp.com/states
    String baseURL = "http://covid19-india-adhikansh.herokuapp.com/states";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        st =  findViewById(R.id.r1c1);
        cnf = findViewById(R.id.r1c2);
        cur = findViewById(R.id.r1c3);
        dead = findViewById(R.id.r1c4);
        tot = findViewById(R.id.r1c5);

        JsonObjectRequest jsonObjectRequest =  new JsonObjectRequest(Request.Method.GET, baseURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String stateobj = jsonObject.getString("state");
                            JSONArray statearray = new JSONArray(stateobj);
                            for (int i = 0 ; i < statearray.length() ; i++) {
                                JSONObject object = statearray.getJSONObject(i);

                                String myState = object.getString("name");
                                int confirm = object.getInt("confirmed");
                                int cured = object.getInt("cured");
                                int deaths = object.getInt("death");
                                int total = object.getInt("total");
                                st.append(myState + "\n" + "\n");
                                cnf.append(confirm + "\n" + "\n");
                                cur.append(cured + "\n"+ "\n");
                                dead.append(deaths + "\n" + '\n');
                                tot.append(total + "\n" + '\n');
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);
    }
}
