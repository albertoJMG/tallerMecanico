/*
 * Bean Email.
 * Representa un mensaje de texto
 */
package librerias;

import java.io.Serializable;

/**
 *
 * @author jose
 */
public class Email implements Serializable {
    
    private String from = "";
    private String to = "";
    private String subject = "";
    private String text = "";

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
