package sptech.elderly.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import sptech.elderly.entity.Email;

@Service @RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public String sendEmail(Email email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setFrom(remetente);
            helper.setTo(email.destinatario());
            helper.setSubject(email.assunto());
            helper.setText(email.mensagem(), true); // true indica que é HTML

            mailSender.send(mimeMessage);
            return "E-mail enviado com sucesso!";
        } catch (Exception e) {
            return "Erro ao enviar e-mail! " + e.getLocalizedMessage();
        }
    }
}
