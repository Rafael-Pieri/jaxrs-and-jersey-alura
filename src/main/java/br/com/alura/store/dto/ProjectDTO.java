package br.com.alura.store.dto;

public class ProjectDTO {

    private Long id;
    private String name;
    private Integer year;

    public ProjectDTO(Long id, String name, Integer year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public ProjectDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ProjectDTO withId(Long id) {
        this.id = id;
        return this;
    }

    public ProjectDTO withName(String name) {
        this.name = name;
        return this;
    }

    public ProjectDTO withYear(Integer year) {
        this.year = year;
        return this;
    }
}
