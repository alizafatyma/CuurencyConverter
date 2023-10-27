package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText sc;
    EditText tc;
    EditText amount;
    TextView t1;
    Button b1;

    String amount_string;
    Double amount_f;
    String target;
    String source;
    Button clearb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sc = findViewById(R.id.scurrency);
        tc = findViewById(R.id.tcurrency);
        amount = findViewById(R.id.amount);
        t1 = findViewById(R.id.t6);
        b1 = findViewById(R.id.button);
        clearb=findViewById(R.id.button3);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                target = tc.getText().toString();
                source = sc.getText().toString();
                amount_string = amount.getText().toString();
                if (!target.isEmpty() || !source.isEmpty() || !amount_string.isEmpty()) {
                    amount_f = Double.parseDouble(amount_string);
                } else {
                    Toast.makeText(MainActivity.this, "The fields can not be empty", Toast.LENGTH_SHORT).show();
                }

                Retrofit retrofit= new Retrofit.Builder().baseUrl("https://openexchangerates.org/api/").addConverterFactory(GsonConverterFactory.create()).build();

                RateApi rt = retrofit.create(RateApi.class);
                Call<ExchangeRate> call=rt.getExchangeRates("228ff44e4be1437cbf91d76fb4668680");
                call.enqueue(new Callback<ExchangeRate>() {
                    @Override
                    public void onResponse(Call<ExchangeRate> call, Response<ExchangeRate> response) {
                        if(response.isSuccessful() || response.body() !=null)
                        {
                            ExchangeRate ex=response.body();
                            double scurrencyvalue= ex.getRates().get(source);
                            double targetvalue=ex.getRates().get(target);
                            double result= (amount_f/scurrencyvalue) * targetvalue;
                            t1.setText((int) result);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Failed to call the api", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ExchangeRate> call, Throwable t)
                    {
                        Toast.makeText(MainActivity.this, "Failed to make a call to api", Toast.LENGTH_SHORT).show();

                    }


                });

            }
        });

        clearb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                t1.setText("");
                tc.setText("");
                sc.setText("");
            }
        });


    }
}