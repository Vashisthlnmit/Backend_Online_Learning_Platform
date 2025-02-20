package com.onlineLearningPlatform.demo.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class EmailSender {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @Async
    public void sendEmail(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String activationCode,
            String subject
    ) throws MessagingException {
        String templateName;
        if(emailTemplate==null){
            templateName="confirm-email";
        }
        else {
            templateName=emailTemplate.getName();
        }
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        //System.out.println(templateName);
        HashMap<String, Object> properties=new HashMap<>();
        properties.put("username", username);
        properties.put("activationCode", activationCode);
        Context context=new Context();
        context.setVariables(properties);
        mimeMessageHelper.setFrom("online_learning@entripise.com");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        String template=templateEngine.process(templateName,context);
        mimeMessageHelper.setText(template,true);
        mailSender.send(mimeMessage);
    }
}
