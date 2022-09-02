package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.entity.Project;
import com.example.demo.service.IProjectService;

@RunWith(SpringRunner.class)
@SpringBootTest

public class DemoApplicationTests {

	@Autowired
	private IProjectService projectService;

	@Test
	public void projectList() {
		List<Project> proList = projectService.list();
		proList.forEach(System.out::println);
	}

}
