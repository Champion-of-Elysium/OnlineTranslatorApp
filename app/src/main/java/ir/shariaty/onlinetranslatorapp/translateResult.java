package ir.shariaty.onlinetranslatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class translateResult extends AppCompatActivity {

    private TextView sourcetxt , translatedtext , textSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_result);

        init();

    }

    private void init(){
        sourcetxt = findViewById(R.id.sourcetxt);
        translatedtext = findViewById(R.id.translatedtext);
        String title =getIntent().getStringExtra("title");
        String text =getIntent().getStringExtra("text");
        String titleEn =getIntent().getStringExtra("titleEn");
        sourcetxt.setText(title);
        translatedtext.setText(titleEn +"\n"+text);
    }
}