package com.codepath.iwantbootcamp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText etModifiedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        initEditItemActivity();
    }

    public void onSaveData(View view) {
        String modifiedText = etModifiedText.getText().toString();

        Intent resultIntent = new Intent();

        resultIntent.putExtra(GlobalInfo.KEY_MODIFIED_TEXT, modifiedText);

        setResult(GlobalInfo.MODIFIED_TEXT_OK, resultIntent);

        finish();
    }

    private void initEditItemActivity() {
        etModifiedText = (EditText) findViewById(R.id.etModifyText);
        String editText = (String) getIntent().getCharSequenceExtra(GlobalInfo.KEY_CURR_TEXT);

        etModifiedText.setText(editText);
    }
}
