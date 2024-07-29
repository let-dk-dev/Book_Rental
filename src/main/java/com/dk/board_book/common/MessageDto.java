package com.dk.board_book.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class MessageDto {

    private String message;

    private String redirectUri;

    private RequestMethod method;

    private Map<String, Object> data;
}
