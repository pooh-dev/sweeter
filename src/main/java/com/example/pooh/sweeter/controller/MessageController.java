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

    private static final String HOME_PAGE = "home";
    private static final String MESSAGES_KEY = "messages";
    private static final String FILTER_PATH = "filter";

    @Resource
    private MessageRepository messageRepository;

    @GetMapping
    public String home(Map<String, Object> model) {
        populateAllMessages(model);
        return HOME_PAGE;
    }

    @PostMapping
    public String addMessage(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Message message = new Message(text, tag);
        messageRepository.save(message);
        populateAllMessages(model);
        return HOME_PAGE;
    }

    @PostMapping(FILTER_PATH)
    public String filterMessages(@RequestParam String tag, Map<String, Object> model) {
        Object messages = StringUtils.isBlank(tag)
                ? messageRepository.findAll()
                : messageRepository.findByTag(tag);
        model.put(MESSAGES_KEY, messages);
        return HOME_PAGE;
    }

    private void populateAllMessages(Map<String, Object> model) {
        Iterable<Message> messages = messageRepository.findAll();
        model.put(MESSAGES_KEY, messages);
    }
}
