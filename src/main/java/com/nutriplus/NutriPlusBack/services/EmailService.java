package com.nutriplus.NutriPlusBack.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nutriplus.NutriPlusBack.domain.UserCredentials;
import com.nutriplus.NutriPlusBack.domain.dtos.DietDTO;
import com.nutriplus.NutriPlusBack.domain.dtos.htmlDtos.MealOptionHtml;
import com.nutriplus.NutriPlusBack.repositories.ApplicationUserRepository;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailService {

    private static final String DIET_EMAIL_TEMPLATE = "html/dietEmailTemplate.html";
    private static final String REGISTRATION_EMAIL_TEMPLATE = "html/registrationEmailTemplate.html";

    @Autowired
    private TemplateEngine nutriplusTemplateEngine;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PdfRenderService pdfRenderService;

    @Async
    public void sendRegistrationEmail(UserCredentials userCredentials, String host, ApplicationUserRepository applicationUserRepository)
            throws MessagingException
    {
        Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.SECRET);
        String jwt = JWT.create()
                .withClaim("id", userCredentials.getUuid())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86_400_000))
                .sign(algorithm);
        String url = "http://" + host + "/user/activate/" + jwt + "/";

        final Context ctx = new Context();
        ctx.setVariable("greeting", "Olá, " + userCredentials.getFirstName() + "!");
        ctx.setVariable("linkPhrase", "Entre no link para confimar seu email.");
        ctx.setVariable("link", url);

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        messageHelper.setSubject("Confirme seu email - Nutriplus");
        messageHelper.setTo(userCredentials.getEmail());

        final String htmlContent = this.nutriplusTemplateEngine.process(REGISTRATION_EMAIL_TEMPLATE, ctx);
        messageHelper.setText(htmlContent, true);
        this.mailSender.send(mimeMessage);

        applicationUserRepository.save(userCredentials);

    }

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

        byte[] stream = pdfRenderService.renderDiet(breakfast, morningSnack, lunch, afternoonSnack, workoutSnack, dinner);

        String path = System.getProperty("user.dir");
        //FileUtils.writeByteArrayToFile(new File(path + "/dita.pdf"), stream);
        final InputStreamSource attachmentSource = new ByteArrayResource(stream);
        message.addAttachment("dieta.pdf", attachmentSource,"application/pdf");

        this.mailSender.send(mimeMessage);

        return CompletableFuture.completedFuture(null);
    }
}
