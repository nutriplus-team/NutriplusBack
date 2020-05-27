package com.nutriplus.NutriPlusBack.services;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.nutriplus.NutriPlusBack.domain.dtos.htmlDtos.MealOptionHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;

@Service
public class PdfRenderService {
    @Autowired
    private TemplateEngine nutriplusTemplateEngine;

    private static final String DIET_TEMPLATE = "html/dietTemplate.html";

    public byte[] renderDiet(List<MealOptionHtml> breakfast, List<MealOptionHtml> morningSnack, List<MealOptionHtml> lunch,
                             List<MealOptionHtml> afternoonSnack, List<MealOptionHtml> workoutSnack, List<MealOptionHtml> dinner)
            throws IOException, InterruptedException
    {
        Locale local = new Locale("pt");
        final Context ctx = new Context(local);
        ctx.setVariable("breakfast", breakfast);
        ctx.setVariable("morningSnack", morningSnack);
        ctx.setVariable("lunch", lunch);
        ctx.setVariable("afternoonSnack", afternoonSnack);
        ctx.setVariable("workoutSnack", workoutSnack);
        ctx.setVariable("dinner", dinner);

        final String htmlContent = this.nutriplusTemplateEngine.process(DIET_TEMPLATE, ctx);

        Pdf pdf = new Pdf();
        pdf.addPageFromString(htmlContent);
        String directory = System.getProperty("user.dir");
        String fileName = directory + "/generatedFiles/" + String.valueOf(System.currentTimeMillis()) + ".pdf";

        pdf.saveAs(fileName);
        File file = new File(fileName);
        byte[] stream = Files.readAllBytes(file.toPath());
        file.delete();

        return stream;
    }
}
