package com.atencia.ScreenMatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ChatGPTConsultation {


    private static final Logger logger = LoggerFactory.getLogger(ChatGPTConsultation.class);

    public static String getTranslation(String text, String language, String API_KEY) {


        try {
            // Initialize the OpenAiService with your API key
            OpenAiService service = new OpenAiService(API_KEY);

            // Build the prompt string using String.format
            String prompt = String.format("Translate the following text to %s: %s", language, text);

            // Build the completion request with the specified model, prompt, and settings
            CompletionRequest request = CompletionRequest.builder()
                    .model("gpt-3.5-turbo-instruct")  // Specify the model to use
                    .prompt(prompt)  // Set the prompt to translate text to the specified language
                    .maxTokens(1000)  // Set the maximum number of tokens
                    .temperature(0.7)  // Set the temperature for randomness
                    .build();

            logger.info("Request: {}", request);

            // Make the request to the OpenAI API
            CompletionResult response = service.createCompletion(request);

            // Return the translated text from the response
            return response.getChoices().get(0).getText().trim();
        } catch (Exception e) {
            // Log only the message of the error using SLF4J
            logger.error("Error while translating text: {}", e.getMessage());

            // Return the original text in case of an error
            return text;
        }
    }
}
