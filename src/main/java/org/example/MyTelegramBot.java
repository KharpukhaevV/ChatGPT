package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.example.OpenAIChat.generateResponse;

public class MyTelegramBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        // Получение текста сообщения
        String message = update.getMessage().getText();

        // Создание ответа
        SendMessage response = new SendMessage();
        String chat_id = String.valueOf(update.getMessage().getChatId());
        response.setChatId(chat_id);
        if (message.equals("/start")) {
            response.setText("Здравствуйте! Я - ваш помощник искусственного интеллекта. Чем я могу вам помочь?");
            send(response);
        } else {
            // Получение ответа
            response.setText("Ожидание ответа ИИ...");
            int msg_id = 0;
            try {
                Message temp_msg = execute(response);
                msg_id = temp_msg.getMessageId();
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            String ai_response = generateResponse(message);
            System.out.println(ai_response);
            DeleteMessage deleteMessage = new DeleteMessage(chat_id, msg_id);
            delete(deleteMessage);
            response.setText(ai_response);
            send(response);
        }

    }

    @Override
    public String getBotUsername() {
        return "chat_witch_ai_bot";
    }

    @Override
    public String getBotToken() {
        return "6051738469:AAEAJj-7ZdDfASy3PJavznHeCW1x05PouMI";
    }

    // Отправка ответа
    private void send(SendMessage response) {
        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private void delete(DeleteMessage deleteMessage) {
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
