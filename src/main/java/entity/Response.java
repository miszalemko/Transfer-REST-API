package entity;

import com.sun.net.httpserver.Headers;
import entity.enums.StatusCode;
import lombok.Value;

@Value
public class Response<T> {

    private final T body;
    private final Headers headers;
    private final StatusCode statusCode;
}
