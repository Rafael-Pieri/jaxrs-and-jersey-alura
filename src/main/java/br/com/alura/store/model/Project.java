package br.com.alura.store.model;

import br.com.alura.store.dto.ProjectDTO;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.beans.BeanUtils;

@Entity
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer year;

    public Project() {}

    public Project(ProjectDTO projectDTO) {
        BeanUtils.copyProperties(projectDTO, this);
        this.name = projectDTO.getName();
        this.year = projectDTO.getYear();
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
}