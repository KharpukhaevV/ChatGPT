package org.example;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;

public class OpenAIChat {
    static String generateResponse(String text) {

        OpenAiService service = new OpenAiService("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", 60);

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
            return "An error occurred while generating the response. " + e.getMessage();
        }
    }
}
