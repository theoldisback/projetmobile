package com.example.projetmobile.View.Userview;

import android.os.AsyncTask;
import android.util.Log;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender  extends AsyncTask<Void, Void, Void> {

    private String toEmail;
    private String subject;
    private String body;

    public EmailSender(String toEmail, String subject, String body) {
        this.toEmail = toEmail;
        this.subject = subject;
        this.body = body;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // Set the email configurations
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com"); // Change to your SMTP provider
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            // Create a session with the email server
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("mohamedabdelkebir15@gmail.com", "oxzb uibi uuin bbnl"); // Use your email credentials
                }
            });

            // Create a message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mohamedabdelkebir15@gmail.com")); // Change to your email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // Recipient email
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);
            Log.d("Email", "Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
            Log.e("Email", "Failed to send email.");
        }
        return null;
    }
}