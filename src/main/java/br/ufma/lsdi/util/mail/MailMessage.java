package br.ufma.lsdi.util.mail;

public class MailMessage {

    private String sender;

    private String receiver;

    private String subject;

    private String body;

    public MailMessage(String remetente, String destinatario, String assunto, String corpo) {
        this.sender = remetente;
        this.receiver = destinatario;
        this.subject = assunto;
        this.body = corpo;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
