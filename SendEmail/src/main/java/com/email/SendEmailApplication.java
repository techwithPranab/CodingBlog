package com.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

@SpringBootApplication
public class SendEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(SendEmailApplication.class, args);

		// SMTP configuration
        String smtpHost = "email-smtp.us-east-1.amazonaws.com"; // Replace with your region's SMTP endpoint
        int smtpPort = 587; // Use 587 for TLS encryption
        String smtpUsername = "AKIAW3MD7WXWR4P7UHZE"; // Your SMTP username
        String smtpPassword = "BN06MGEO5FACFP/Nt803RJrMYGBfpqVnC4FJ2MrEarkI"; // Your SMTP password

        // Email details
        String fromEmail = "pranabpiitk2024@gmail.com"; // Sender's verified email address in SES
        String toEmail = "pranabpiitk@gmail.com"; // Recipient's email address
        String subject = "Test Email from AWS SES via SMTP";
        String bodyText = "This is a test email sent using AWS SES via SMTP in Java.";

        // Set up properties for the session
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Get the session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUsername, smtpPassword);
            }
        });

        try {
            // Compose the message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(bodyText);

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email: " + e.getMessage());
        }
	}

}
