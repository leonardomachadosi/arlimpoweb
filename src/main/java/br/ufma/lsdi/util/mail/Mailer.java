package br.ufma.lsdi.util.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class Mailer {

    private static final String CONTENT_TYPE = "text/html";
    private static final String ENCODING = "utf-8";

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(MailMessage message) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setSubject(message.getSubject());
        mimeMessage.setContent(message.getBody(), CONTENT_TYPE);

        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, ENCODING);
        messageHelper.setTo(message.getReceiver());
        messageHelper.setFrom(message.getSender());


        javaMailSender.send(mimeMessage);
    }
}
