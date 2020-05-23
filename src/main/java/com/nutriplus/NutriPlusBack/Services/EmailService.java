package com.nutriplus.NutriPlusBack.Services;


import com.nutriplus.NutriPlusBack.Domain.DTOs.DietDTO;
import com.nutriplus.NutriPlusBack.Domain.DTOs.htmlDtos.MealOptionHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailService {

    private static String DIET_EMAIL_TEMPLATE = "html/dietEmailTemplate.html";


    @Autowired
    private TemplateEngine nutriplusTemplateEngine;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PdfRenderService pdfRenderService;

    @Async
    public CompletableFuture<Void> sendDietEmail(String nutritionistName, String recipientEmail, String recipientName,
                                                 List<MealOptionHtml> breakfast, List<MealOptionHtml> morningSnack, List<MealOptionHtml> lunch,
                                                 List<MealOptionHtml> afternoonSnack, List<MealOptionHtml> workoutSnack, List<MealOptionHtml> dinner, DietDTO diet)
            throws MessagingException, IOException, InterruptedException
    {
        Locale locale = new Locale("pt");
        final Context ctx = new Context(locale);
        ctx.setVariable("nutritionist", nutritionistName);
        ctx.setVariable("patient", recipientName);

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setSubject("Sua dieta por email - Nutriplus");
        message.setTo(recipientEmail);

        final String htmlContent = this.nutriplusTemplateEngine.process(DIET_EMAIL_TEMPLATE, ctx);
        message.setText(htmlContent, true);

        byte[] file = pdfRenderService.renderDiet(breakfast, morningSnack, lunch, afternoonSnack, workoutSnack, dinner);

        final InputStreamSource attachmentSource = new ByteArrayResource(file);
        message.addAttachment("dieta.pdf", attachmentSource,"pdf");

        this.mailSender.send(mimeMessage);

        return CompletableFuture.completedFuture(null);
    }
}
