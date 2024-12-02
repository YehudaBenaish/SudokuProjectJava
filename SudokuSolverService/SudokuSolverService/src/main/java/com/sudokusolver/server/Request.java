package main.java.com.sudokusolver.server;

import java.util.Map;

public class Request {
    private String action;
    private Map<String, Object> body;

    public Request(String action, Map<String, Object> body) {
        this.action = action;
        this.body = body;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }
}
