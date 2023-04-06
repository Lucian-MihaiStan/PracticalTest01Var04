package ro.pub.cs.systems.eim.practicaltest01var04;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var04MainActivity extends AppCompatActivity {

    Button navigateToSecondaryActivityButton;
    Button displayButton;

    CheckBox nameCheckBox;
    EditText nameEditText;

    CheckBox groupCheckBox;
    EditText groupEditText;

    EditText displayEditText;

    private PracticalBroadcastReceiver broadcastReceiver = new PracticalBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_main);

        navigateToSecondaryActivityButton = findViewById(R.id.navigate_to_secondary_activity_button);
        displayButton = findViewById(R.id.display_information_button);
        nameCheckBox = findViewById(R.id.name_checkbox);
        nameEditText = findViewById(R.id.username_edit_text);
        groupCheckBox = findViewById(R.id.group_checkbox);
        groupEditText = findViewById(R.id.group_edit_text);

        displayEditText = findViewById(R.id.display_information_edit_text);

        navigateToSecondaryActivityButton.setOnClickListener(new PracticalButtonListener());
        displayButton.setOnClickListener(new PracticalButtonListener());

        intentFilter.addAction(Constants.BROADCAST_ACTION);
    }

    class PracticalButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.navigate_to_secondary_activity_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04SecondaryActivity.class);
                    intent.putExtra("name", nameEditText.getText().toString());
                    intent.putExtra("group", groupEditText.getText().toString());
                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
                case R.id.display_information_button:
                    String message = "";
                    if (nameCheckBox.isChecked()) {
                        if (nameEditText.getText().toString().equals(""))
                            Toast.makeText(getApplicationContext(), "Empty student name", Toast.LENGTH_LONG).show();
                        message += nameEditText.getText().toString();
                    }
                    if (groupCheckBox.isChecked()) {
                        if (groupEditText.getText().toString().equals(""))
                            Toast.makeText(getApplicationContext(), "Empty group name", Toast.LENGTH_LONG).show();
                        message += groupEditText.getText().toString();
                    }
                    displayEditText.setText(message);

                    if (!nameEditText.getText().toString().equals("") && !groupEditText.getText().toString().equals("")) {
                        Intent serviceIntent = new Intent(getApplicationContext(), PracticalTest01Var04Service.class);
                        serviceIntent.putExtra("name", nameEditText.getText().toString());
                        serviceIntent.putExtra("group", groupEditText.getText().toString());
                        getApplicationContext().startService(serviceIntent);
                    }

                    break;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", nameEditText.getText().toString());
        outState.putString("group", groupEditText.getText().toString());
        outState.putString("display", displayEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("name")) {
            nameEditText.setText(savedInstanceState.getString("name"));
        }
        if (savedInstanceState.containsKey("group")) {
            groupEditText.setText(savedInstanceState.getString("group"));
        }
        if (savedInstanceState.containsKey("display")) {
            displayEditText.setText(savedInstanceState.getString("display"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "CANCEL", Toast.LENGTH_LONG).show();
            }
        }
    }

    class PracticalBroadcastReceiver extends android.content.BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.MAIN_ACTIVITY, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getApplicationContext().stopService(new Intent(getApplicationContext(), PracticalTest01Var04Service.class));
    }
}