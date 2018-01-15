package br.com.alura.store.model;

import br.com.alura.store.dto.ProjectDTO;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name = "PROJECT")
public class Project {

    @GeneratedValue
    @Id
    private Long id;
    private String name;
    private Integer year;

    public Project(ProjectDTO projectDTO) {
        BeanUtils.copyProperties(projectDTO, this);
        this.name = projectDTO.getName();
        this.year = projectDTO.getYear();
    }

    public Project() {

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
