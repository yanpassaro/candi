package com.brd.candi.service.others;

import com.brd.candi.domain.model.EmailModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderService {
    final JavaMailSender mailSender;

    public void enviarEmail(EmailModel emailModel) {
        log.info("Enviando email para {}", emailModel.getDestino());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(emailModel.getAssunto());
        message.setTo(emailModel.getDestino());
        message.setText(emailModel.getConteudo());
        mailSender.send(message);
    }
}
