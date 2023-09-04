package com.gbossoufolly.assivitoshopbackend.services;

import com.gbossoufolly.assivitoshopbackend.exceptions.EmailFailureException;
import com.gbossoufolly.assivitoshopbackend.models.VerificationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${app.frontend.url}")
    private String url;
    private JavaMailSender javaMailSender;
    @Value("${email.from}")
    private String fromAddress;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private SimpleMailMessage makeMailMessage(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAddress);

        return simpleMailMessage;
    }

    public void sendVerificationEmail(VerificationToken verificationToken) throws EmailFailureException {
        SimpleMailMessage message = makeMailMessage();
        message.setTo(verificationToken.getUser().getEmail());
        message.setSubject("Verify your email to active your account!");
        message.setText("Please, click on the link below to verify your email to active you account.\n"
                + url + "/auth/verify?token=" + verificationToken.getToken());
        try{
            javaMailSender.send(message);
        }catch(MailException exception){
            throw new EmailFailureException();
        }
    }
}
