package com.example.test_gmail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.xpath.XPath;

public class MailSenderActivity extends Activity {
    String path;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sender_activity);

        File f = new File(getCacheDir()+"/asd.jpeg");
        if (!f.exists()) try {

            InputStream is = getAssets().open("asd.jpeg");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();


            FileOutputStream fos = new FileOutputStream(f);
            fos.write(buffer);
            fos.close();
        } catch (Exception e) { throw new RuntimeException(e); }
        path = f.getPath();

        final Button send = (Button) this.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
//                Log.e("ERRoR", getFilesDir().listFiles().toString());
                new SendEmailAsyncTask(path).execute();
                Toast.makeText(getApplicationContext(),"done!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void buttonSendEmail(View view){

        try {
            String stringSenderEmail = "SenderEmail963@gmail.com";
            String stringReceiverEmail = "receiveremail963@gmail.com";
            String stringPasswordSenderEmail = "Test*123";

            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

            mimeMessage.setSubject("Subject: Android App email");
            mimeMessage.setText("Hello Programmer, \n\nProgrammer World has sent you this 2nd email. \n\n Cheers!\nProgrammer World");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show sign up activity
            startActivity(new Intent(MailSenderActivity.this, welcome.class));
//            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
//                    .putBoolean("isFirstRun", false).commit();
        }
    }
}

class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
    GMailSender sender = new GMailSender("tablet.reading.me@gmail.com", "qklimeegevbcgkvm");
    String path;
    public SendEmailAsyncTask(String path) {
        this.path=path;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (BuildConfig.DEBUG) Log.v(SendEmailAsyncTask.class.getName(), "doInBackground()");
        String[] toArr = {"abdallah.omar.ghazaly@gmail.com"};
        sender.setTo(toArr);
        sender.setFrom("tablet.reading.me@gmail.com");
        sender.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
        sender.setBody("Email body.");


        try {
            sender.addAttachment(path);

            if(sender.send()) {
                Log.e("ERRoR", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                return true;

            } else {
                Log.e("ERRoR2", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                return false;
            }
        } catch(Exception e) {
            //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
            Log.e("MailApp", "Could not send email", e);
        }
        return true;
    }
}