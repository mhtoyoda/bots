package com.fiveware.service.bot;

import com.fiveware.model.Bot;
import com.fiveware.model.BotFormatter;
import com.fiveware.repository.BotFormatterRepository;
import com.fiveware.repository.BotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class ServiceBotImpl {

    @Autowired
    private final BotRepository botRepository;
    @Autowired
    private final BotFormatterRepository botFormatterRepository;

    public ServiceBotImpl(BotRepository botRepository, BotFormatterRepository botFormatterRepository) {
        this.botRepository = botRepository;
        this.botFormatterRepository = botFormatterRepository;
    }

    public Bot save(Bot bot){
        Optional<Bot> optional = botRepository.findByNameBot(bot.getNameBot());
        return optional.orElseGet(new Supplier<Bot>() {
            @Override
            public Bot get() {
                return botRepository.save(bot);
            }
        });
    }

    public Iterable<Bot> findAll() {
        return botRepository.findAll();
    }

    public Optional<Bot> findByNameBot(String name) {
        return botRepository.findByNameBot(name);
    }

    public List<BotFormatter> findByBot(String nameBot) {
        return botFormatterRepository.findByBot(nameBot);
    }

    public BotFormatter save(BotFormatter botFormatter) {
        return botFormatterRepository.save(botFormatter);
    }

    public void deleteBotFormatter(@RequestBody List<BotFormatter> botFormatters) {
        botFormatters.forEach(botFormat -> {
            BotFormatter botFormatter = botFormatterRepository.findOne(botFormat.getId());
            botFormatterRepository.delete(botFormatter);
        });
    }
}
