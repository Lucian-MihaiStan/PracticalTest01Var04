package ro.pub.cs.systems.eim.practicaltest01var04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01Var04SecondaryActivity extends AppCompatActivity {

    EditText secondaryUsernameEditText;
    EditText secondaryGroupEditText;

    Button okButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_secondary);

        secondaryUsernameEditText = findViewById(R.id.second_username_edit_text);
        secondaryGroupEditText = findViewById(R.id.second_group_edit_text);

        okButton = findViewById(R.id.ok_button);
        cancelButton = findViewById(R.id.cancel_button);

        okButton.setOnClickListener(new SecondaryButtonClick());
        cancelButton.setOnClickListener(new SecondaryButtonClick());

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("name") && intent.getExtras().containsKey("group")) {
            String name = intent.getStringExtra("name");
            String group = intent.getStringExtra("group");
            secondaryUsernameEditText.setText(name);
            secondaryGroupEditText.setText(group);
        }

    }

    class SecondaryButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.ok_button:
                    setResult(RESULT_OK, new Intent().putExtra(Constants.OK_BUTTON, "OK"));
                    finish();
                    break;
                case R.id.cancel_button:
                    setResult(RESULT_CANCELED, new Intent().putExtra(Constants.CANCEL_BUTTON, "CANCEL"));
                    finish();
                    break;
            }
        }
    }
}