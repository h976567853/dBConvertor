package com.hhh.dbconvertor;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {

    private String formatStr(double x) {
        String s;
        if (x == 0) {
            s = getResources().getString(R.string.zero);
        } else if (Math.abs(x) > 1e-4 && Math.abs(x) < 1e4) {
            s = String.format(getString(R.string.output_format_f), x);
        } else {
            s = String.format(getString(R.string.output_format_e), x);
        }
        return s;
    }

    private boolean validateStr(String s) {
        return !s.equals("-") && !s.isEmpty() && !s.startsWith(".");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove Title Bar
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        final EditText db_text = findViewById(R.id.editText1);
        final EditText ra_text = findViewById(R.id.editText2);
        db_text.setText(getResources().getString(R.string.zero));
        ra_text.setText(getResources().getString(R.string.one));

        db_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    db_text.setText(getResources().getString(R.string.zero));
                    ra_text.setText(getResources().getString(R.string.one));
                    db_text.selectAll();
                }
            }
        });

        ra_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    db_text.setText(getResources().getString(R.string.zero));
                    ra_text.setText(getResources().getString(R.string.one));
                    ra_text.selectAll();
                }
            }
        });

        db_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String db_str = db_text.getText().toString();
                if (db_text.hasFocus()) {
                    if (validateStr(db_str)) {
                        double dB_num = Double.parseDouble(db_str);
                        double ra_num = Math.pow(10.0, dB_num / 10.0);
                        String ra_str = formatStr(ra_num);
                        ra_text.setText(ra_str);
                        ra_text.selectAll();
                    } else {
                        ra_text.setText(getResources().getString(R.string.error));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ra_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ra_str = ra_text.getText().toString();
                if (ra_text.hasFocus()) {
                    if (validateStr(ra_str)) {
                        double ra_num = Double.parseDouble(ra_str);
                        if (ra_num > 0) {
                            double db_num = 10.0 * Math.log10(ra_num);
                            String db_str = formatStr(db_num);
                            db_text.setText(db_str);
                        }
                    } else {
                        db_text.setText(getResources().getString(R.string.error));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
