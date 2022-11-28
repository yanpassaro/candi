package com.brd.candi.service.others;

import com.brd.candi.domain.dto.EmailDTO;
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

    public void enviarEmail(EmailDTO emailDTO) {
        log.info("Enviando email para {}", emailDTO.getDestino());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(emailDTO.getAssunto());
        message.setTo(emailDTO.getDestino());
        message.setText(emailDTO.getConteudo());
        mailSender.send(message);
    }
}
