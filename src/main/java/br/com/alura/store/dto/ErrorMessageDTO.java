package br.com.alura.store.dto;

public class ErrorMessageDTO {

    private String message;

    public ErrorMessageDTO(String message) {
        this.message = message;
    }

    public ErrorMessageDTO() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


