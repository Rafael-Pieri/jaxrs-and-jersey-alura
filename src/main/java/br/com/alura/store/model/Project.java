package br.com.alura.store.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.XStream;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Project {

	private Long id;
	private String name;
	private Integer year;

	public Project(Long id, String name, Integer year) {
		this.id = id;
		this.name = name;
		this.year = year;
	}

	public Project() {
	}

	public String toXML() {
		return new XStream().toXML(this);
	}

	public void setId(Long id2) {
		this.id = id2;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getYear() {
		return year;
	}

}
