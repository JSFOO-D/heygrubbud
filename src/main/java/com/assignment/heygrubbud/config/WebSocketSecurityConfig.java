package com.assignment.heygrubbud.config;

//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
//import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

//@Configuration
//public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
//public class WebSocketSecurityConfig {

//    @Override
//    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
//        // TODO
//        messages
//                // users cannot send to these broker destinations, only the application can
//                .simpMessageDestMatchers("/topic/chat.login", "/topic/chat.logout", "/topic/chat.message").denyAll()
//                .anyMessage().authenticated();
//    }
//
//    @Override
//    protected boolean sameOriginDisabled() {
//        //disable CSRF for websockets for now...
//        return true;
//    }
//}
