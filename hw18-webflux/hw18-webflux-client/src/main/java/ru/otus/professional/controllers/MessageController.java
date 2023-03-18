package ru.otus.professional.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.util.HtmlUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.professional.domain.Message;

import java.util.function.Predicate;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private static final String TOPIC_TEMPLATE = "/topic/response.";

    private final WebClient datastoreClient;
    private final SimpMessagingTemplate template;
    private final long specialRoom;

    public MessageController(@Value("${special.room}") long specialRoom, WebClient datastoreClient, SimpMessagingTemplate template) {
        this.datastoreClient = datastoreClient;
        this.template = template;
        this.specialRoom = specialRoom;
        logger.info("special room is \"{}\"", specialRoom);
    }

    @MessageMapping("/message.{roomId}")
    public void getMessage(@DestinationVariable long roomId, Message message) {
        logger.info("get message:{}, roomId:{}", message, roomId);
        Mono.just(roomId)
                .filter(specialRoomFilter())
                .map(rmId -> saveMessage(Long.toString(roomId), message)
                        .subscribe(msgId -> logger.info("message send id:{}", msgId)))
                .subscribe();

        Mono.just(roomId)
                .filter(specialRoomFilter())
                .doOnNext(rmId -> printOnForm(message, rmId))
                .doOnNext(rmId -> printOnForm(message, specialRoom))
                .subscribe(rmId -> logger.info("Messages on form"));
    }

    private void printOnForm(Message message, long roomId) {
        template.convertAndSend(String.format("%s%s", TOPIC_TEMPLATE, roomId),
                new Message(HtmlUtils.htmlEscape(message.messageStr())));
    }

    private Predicate<Long> specialRoomFilter() {
        return rmId -> rmId != specialRoom;
    }


    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        var genericMessage = (GenericMessage<byte[]>) event.getMessage();
        var simpDestination = (String) genericMessage.getHeaders().get("simpDestination");
        if (simpDestination == null) {
            logger.error("Can not get simpDestination header, headers:{}", genericMessage.getHeaders());
            throw new ChatException("Can not get simpDestination header");
        }
        var roomId = parseRoomId(simpDestination);

        getMessagesByRoomId(roomId)
                .doOnError(ex -> logger.error("getting messages for roomId:{} failed", roomId, ex))
                .subscribe(message -> template.convertAndSend(simpDestination, message));
    }

    private long parseRoomId(String simpDestination) {
        try {
            return Long.parseLong(simpDestination.replace(TOPIC_TEMPLATE, ""));
        } catch (Exception ex) {
            logger.error("Can not get roomId", ex);
            throw new ChatException("Can not get roomId");
        }
    }

    private Mono<Long> saveMessage(String roomId, Message message) {
        return datastoreClient.post().uri(String.format("/msg/%s", roomId))
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(message)
                .exchangeToMono(response -> response.bodyToMono(Long.class));
    }

    private Flux<Message> getMessagesByRoomId(long roomId) {

        return Mono.just(roomId)
                .map(rmId -> rmId == specialRoom ? "/msg" : String.format("/msg/%s", rmId))
                .flatMapMany(url -> datastoreClient.get().uri(url)
                        .accept(MediaType.APPLICATION_NDJSON)
                        .exchangeToFlux(response -> {
                            if (response.statusCode().equals(HttpStatus.OK)) {
                                return response.bodyToFlux(Message.class);
                            } else {
                                return response.createException().flatMapMany(Mono::error);
                            }
                        })
                );
    }
}
