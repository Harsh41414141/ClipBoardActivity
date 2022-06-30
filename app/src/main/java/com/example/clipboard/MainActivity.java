package com.example.clipboard;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageButton pastetextbtn,copytextbtn;
    Button charcountbtn,wordcountbtn,numbercountbtn;
    TextView txt1,txt2,txt3,out;
    EditText in;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pastetextbtn=(ImageButton) findViewById(R.id.pastetext);
        copytextbtn=(ImageButton) findViewById(R.id.copytext);
        charcountbtn=(Button) findViewById(R.id.charcount);
        wordcountbtn=(Button) findViewById(R.id.wordcount);
        numbercountbtn=(Button) findViewById(R.id.numcount);

        txt1=(TextView) findViewById(R.id.value1);
        txt2=(TextView) findViewById(R.id.value2);
        txt3=(TextView) findViewById(R.id.value3);
        out=(TextView) findViewById(R.id.outputtext);
        in=(EditText) findViewById(R.id.inputtext);

        charcountbtn.setEnabled(false);
        wordcountbtn.setEnabled(false);
        numbercountbtn.setEnabled(false);

        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        copytextbtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V){
                String text;
                text=in.getText().toString();

                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(getApplicationContext(), "Text Copied", Toast.LENGTH_SHORT).show();

                //setting the not clickable button to clickable after copy is done
                charcountbtn.setEnabled(true);
                wordcountbtn.setEnabled(true);
                numbercountbtn.setEnabled(true);
            }
        });

        pastetextbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ClipData abc = myClipboard.getPrimaryClip();
                ClipData.Item item = abc.getItemAt(0);
                String text = item.getText().toString();
                if(text!=null||text.length()!=0){
                    //not clickable to clickable
                    charcountbtn.setEnabled(true);
                    wordcountbtn.setEnabled(true);
                    numbercountbtn.setEnabled(true);
                    out.setText(text);
                    out.setPaintFlags(out.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                    Toast.makeText(getApplicationContext(), "Text Pasted", Toast.LENGTH_SHORT).show();
                }else{
                    //do nothing
                }
                }
        });

        charcountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count;
                ClipData abc=myClipboard.getPrimaryClip();
                ClipData.Item item=abc.getItemAt(0);
                String text=item.getText().toString();
                count=text.length();
                String chrcount = String.valueOf(count);//number of characters
                txt1.setText("= "+chrcount);
            }
        });

        wordcountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipData abc=myClipboard.getPrimaryClip();
                ClipData.Item item=abc.getItemAt(0);
                String text =item.getText().toString();
                String wrdcount=String.valueOf(countWords(text));
                txt2.setText("= "+wrdcount);
            }
        });

        numbercountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData abc=myClipboard.getPrimaryClip();
                ClipData.Item item =abc.getItemAt(0);
                String text=item.getText().toString();
                int count=0;//count of numbers in string
                for(int i=0;i<text.length();i++){
                    if(text.charAt(i)>=48 && text.charAt(i)<=57){
                        count++;
                    }
                }
                String nmcount=String.valueOf(count);
                txt3.setText("= "+nmcount);
            }
        });

    }
    public static int countWords(String str)
    {
        if (str == null || str.isEmpty())
            return 0;
        String[] words = str.split("\\s+");
        return words.length;
    }
}