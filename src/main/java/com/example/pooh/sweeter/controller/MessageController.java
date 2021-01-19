package com.example.pooh.sweeter.controller;

import com.example.pooh.sweeter.model.Message;
import com.example.pooh.sweeter.repository.MessageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Map;

@Controller
public class MessageController {

    private static final String MESSAGES_KEY = "messages";

    @Resource
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String home(Map<String, Object> model) {
        populateAllMessages(model);
        return "main";
    }

    @PostMapping("/main")
    public String addMessage(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Message message = new Message(text, tag);
        messageRepository.save(message);
        populateAllMessages(model);
        return "main";
    }

    @PostMapping("/filter")
    public String filterMessages(@RequestParam String tag, Map<String, Object> model) {
        Object messages = StringUtils.isBlank(tag)
                ? messageRepository.findAll()
                : messageRepository.findByTag(tag);
        model.put(MESSAGES_KEY, messages);
        return "main";
    }

    private void populateAllMessages(Map<String, Object> model) {
        Iterable<Message> messages = messageRepository.findAll();
        model.put(MESSAGES_KEY, messages);
    }
}
