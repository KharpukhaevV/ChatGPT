package org.example;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;

public class OpenAIChat {
    static String generateResponse(String text) {

        OpenAiService service = new OpenAiService("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", 120);

        CompletionRequest request =  CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(text)
                .maxTokens(3000)
                .temperature(0.5)
                .build();

        try {
            CompletionResult response = service.createCompletion(request);
            return response.getChoices().get(0).getText();
        } catch (Exception e) {
            e.printStackTrace();
            return "При формировании ответа произошла ошибка. Текст ошибки: " + e.getMessage();
        }
    }
}
