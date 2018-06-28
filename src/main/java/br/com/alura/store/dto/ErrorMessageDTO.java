package br.com.alura.store.dto;

public class ErrorMessageDTO {

    private String errorMessage;

    public ErrorMessageDTO() {}

    public ErrorMessageDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}