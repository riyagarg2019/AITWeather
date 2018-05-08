package com.example.riyagarg.aitweather;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adapter.CityRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.example.riyagarg.aitweather.data.Clouds;
import com.example.riyagarg.aitweather.data.Coord;
import com.example.riyagarg.aitweather.data.Weather;
import com.example.riyagarg.aitweather.data.WeatherResult;
import com.example.riyagarg.aitweather.data.Wind;
import com.example.riyagarg.aitweather.network.WeatherAPI;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.net.Uri.encode;
import static com.adapter.CityRecyclerAdapter.KEY_CITY;

public class DetailsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {



            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_details);

            final String URL_BASE =
                    "http://api.openweathermap.org";


            final TextView tvCity = findViewById(R.id.tvCity);
            final TextView tvCoord = findViewById(R.id.tvCoord);
            final TextView tvDescription = findViewById(R.id.tvDescription);
            final TextView tvTemp = findViewById(R.id.tvTemp);
            final TextView tvPressure = findViewById(R.id.tvPressure);
            final TextView tvHumidity = findViewById(R.id.tvHumidity);
            final TextView tvTempMin = findViewById(R.id.tvTempMin);
            final TextView tvTempMax = findViewById(R.id.tvTempMax);
            final ImageView ivIcon = findViewById(R.id.ivIcon);
            final String[] icon = new String[1];



            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.openweathermap.org/").addConverterFactory(GsonConverterFactory.create()).build();

            final WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
            final String City = getIntent().getStringExtra(KEY_CITY);

            //URLEncoder.encode(City, "UTF-8");


            weatherAPI.getWeatherDetails(City,
                    "metric",
                    "6cc765f13ddc37d83bfaea843413d095").enqueue(new Callback<WeatherResult>() {


                    @Override
                    public void onResponse

                    (Call < WeatherResult > call, Response < WeatherResult > response) {
                        try {
                            URLEncoder.encode(City, "UTF-8");
                            String cityName = response.body().getName();
                            String coordX = response.body().getCoord().getLat().toString();
                            String coordY = response.body().getCoord().getLon().toString();
                            String Temp = response.body().getMain().getTemp().toString();
                            String Pressure = response.body().getMain().getPressure().toString();
                            String Humidity = response.body().getMain().getHumidity().toString();
                            String tempMin = response.body().getMain().getTempMin().toString();
                            String tempMax = response.body().getMain().getTempMax().toString();

                            List<Weather> weather = response.body().getWeather();
                            Weather weath = weather.get(0);
                            icon[0] = weath.getIcon();
                            String Description = weath.getDescription();


                            tvCity.setText("City Name: " + cityName);
                            tvCoord.setText("Coordinates: " + coordX + ", " + coordY);
                            tvDescription.setText("Description: " + Description);
                            tvTemp.setText("Temperature: " + Temp);
                            tvPressure.setText("Pressure: " + Pressure);
                            tvHumidity.setText("Humidity: " + Humidity);
                            tvTempMin.setText("Minimum Temperature: " + tempMin);
                            tvTempMax.setText("Maximum Temperature: " + tempMax);
                        } catch (Exception e) {
                            Toast.makeText(DetailsActivity.this, "Invalid City", Toast.LENGTH_SHORT).show();


                        }
                    }



                @Override
                public void onFailure (Call < WeatherResult > call, Throwable t){
                    Toast.makeText(DetailsActivity.this, "Error: " +
                            t.getMessage(), Toast.LENGTH_SHORT).show();

                }

            });
            
            String iconWeb = "http://openweathermap.org/img/w/" + icon[0] +".png";
            Glide.with(this).load(iconWeb).into(ivIcon);




        //Glide.with(this).load("http://openweathermap.org/img/w/10d.png").into(ivIcon);
    }
}
