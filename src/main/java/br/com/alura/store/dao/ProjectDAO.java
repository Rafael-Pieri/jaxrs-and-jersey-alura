package br.com.alura.store.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import br.com.alura.store.model.Project;

public class ProjectDAO {

	private static Map<Long, Project> bank = new HashMap<>();
	private static AtomicLong conter = new AtomicLong(1);

	static {
		bank.put(1l, new Project(1l, "My store", 2014));
		bank.put(2l, new Project(2l, "Alura", 2012));
	}

	public void add(Project project) {
		Long id = conter.incrementAndGet();
		project.setId(id);
		bank.put(id, project);
	}

	public Project find(Long id) {
		return bank.get(id);
	}

	public Project remove(Long id) {
		return bank.remove(id);
	}

}