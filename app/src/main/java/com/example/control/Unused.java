package com.example.control;

public class Unused {
    /*
    private TextView progressText;
    private SeekBar inBar;
    private EditText inText;
    private TextView serRes;

    progressText = findViewById(R.id.textView3);
    inBar = findViewById(R.id.seekBar);
    inText = findViewById(R.id.editText3);
    serRes = findViewById(R.id.textView3);

        inBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            inText.setText("" + progress + "%");
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

    });

        inText.setOnKeyListener(new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if ((event.getAction() == android.view.KeyEvent.ACTION_DOWN) && (keyCode == android.view.KeyEvent.KEYCODE_ENTER)) {
                inBar.setProgress(Integer.parseInt(inText.getText().toString()));
                inText.setText(inText.getText());
                return true;
            }
            return false;
        }
    });

    public void stopExecution(View view){
        //Kod wysyłany do sterownika do przerwania operacji
        Toast.makeText(getApplicationContext(), "Dziala", Toast.LENGTH_SHORT).show();
    }

    public void startExecution(View view) {
        //Kod wysyłany do sterownika do rozpoczęcia operacji
        ConnectionSend connectionSend = new ConnectionSend();
        connectionSend.execute(inText.getText().toString());

    }

    public void config(View view){
        Intent intent = new Intent(this, ConfigActivity.class);
        startActivity(intent);
    }
    */
}
