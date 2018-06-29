package br.com.alura.store.dto;

public class ProjectPostDTO {

    private String name;
    private Integer year;

    public ProjectPostDTO() {

    }

    public ProjectPostDTO(String name, Integer year) {
        this.name = name;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}