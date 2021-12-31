package ir.shariaty.onlinetranslatorapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ir.shariaty.onlinetranslatorapp.model.Model;
import ir.shariaty.onlinetranslatorapp.request.ServiceGenerator;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {

    private PowerSpinnerView sourcel;
    private PowerSpinnerView destinationl;
    private Button translatebtn;
    private String fromLanguage = "", toLanguage = "";
    private EditText editsource;
    private List<String> iconSpinnerItems = new ArrayList<>();
    private ImageView Switch;
    private ConstraintLayout translatelayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();
        setListeners();
    }

    private void init() {
        iconSpinnerItems.add("English");
        iconSpinnerItems.add("Arabic");
        iconSpinnerItems.add("Persian");
        sourcel = findViewById(R.id.sourcel);
        destinationl = findViewById(R.id.destinationl);
        editsource = findViewById(R.id.editsource);

        sourcel.setItems(iconSpinnerItems);
        destinationl.setItems(iconSpinnerItems);
        translatebtn = findViewById(R.id.translatebtn);
        Switch = findViewById(R.id.Switch);
        translatelayout = findViewById(R.id.translatelayout);

    }

    private void setListeners() {
        sourcel.setOnClickListener(view -> sourcel.show());
        destinationl.setOnClickListener(view -> destinationl.show());
        sourcel.setOnSpinnerItemSelectedListener(
                (OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
                    fromLanguage = newItem;

                });

        destinationl.setOnSpinnerItemSelectedListener(
                (OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
                    toLanguage = newItem;
                    if (fromLanguage.equals("Persian") && toLanguage.equals("English")){
                        makeToast("dehkhoda");
                    }
                });

        Switch.setOnClickListener(view -> {
            sourcel.dismiss();
            destinationl.dismiss();

            String temp;
            temp = fromLanguage;
            fromLanguage = toLanguage;
            toLanguage = temp;

            System.out.println(fromLanguage +"__1111111__"+toLanguage);

            //     powerSpinnerView1.setPreferenceName(fromLanguage);
            //     powerSpinnerView2.setPreferenceName(toLanguage);
            sourcel.notifyItemSelected(iconSpinnerItems.indexOf(fromLanguage) , fromLanguage);
            destinationl.notifyItemSelected(iconSpinnerItems.indexOf(toLanguage) , toLanguage);

            System.out.println(fromLanguage +"__2222222__"+toLanguage);
        });

        editsource.setOnClickListener(view -> {
            sourcel.dismiss();
            destinationl.dismiss();
        });

        translatelayout.setOnClickListener(view -> {
            sourcel.dismiss();
            destinationl.dismiss();
        });

        translatebtn.setOnClickListener(view -> {
            if (fromLanguage == null || toLanguage == null || editsource.getText().toString().trim().equals("")) {
                makeToast("fill the blanks");
                return;
            }


            Intent showWordsDetail = new Intent(Dashboard.this,
                    translateResult.class);
            getDataObservable().enqueue(new Callback<Model>() {
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {
                    int code = response.body().getResponse().getCode();
                    if (code == 200 && response.body().getData().getResults() != null && response.body().getData().getResults().size() != 0) {
                        String text = response.body().getData().getResults().get(0).getText();
                        String titleEn = response.body().getData().getResults().get(0).getTitle_en();
                        String source = response.body().getData().getResults().get(0).getSource();
                        showWordsDetail.putExtra("text", text);
                        showWordsDetail.putExtra("title", editsource.getText().toString());
                        showWordsDetail.putExtra("titleEn", titleEn);
                        showWordsDetail.putExtra("source", source);
                        startActivity(showWordsDetail);
                    } else {
                        makeToast("wrong request");
                    }
                }

                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    makeToast("wrong request");
                    call.cancel();
                }
            });
        });
    }

    private String findDB() {
        switch (fromLanguage) {
            case "Persian": {
                return setToLanguage("fa");
            }
            case "Arabic": {
                return setToLanguage("ar");
            }
            case "English": {
                return setToLanguage("en");
            }
        }
        return "";
    }

    private String setToLanguage(String from) {
        switch (toLanguage) {
            case "Persian": {
                return from+"2fa";
            }
            case "Arabic": {
                return from + "2ar";
            }
            case "English": {
                return  "dehkhoda";
            }
        }
        return null;
    }

    private Call<Model> getDataObservable() {
        return ServiceGenerator.getRequestApi().getTranslation("68522.FGMaxOVSqxGtZBdEO7xuZfxQ4IjCjCUnIYcS5me2", editsource.getText().toString().trim(), "exact", findDB());
    }

    private void makeToast(String st) {
        Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
    }
}
