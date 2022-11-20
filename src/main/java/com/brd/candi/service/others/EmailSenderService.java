package com.brd.candi.service.others;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderService {
    final JavaMailSender javaMailSender;

    public void enviarAnalise() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("teste");
    }
}
