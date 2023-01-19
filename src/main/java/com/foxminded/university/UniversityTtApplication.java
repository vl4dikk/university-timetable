package com.foxminded.university;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.foxminded.university.configs.DaoConfig;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.models.Group;
import com.foxminded.university.services.GroupService;


@SpringBootApplication
@Import(DaoConfig.class)
public class UniversityTtApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniversityTtApplication.class, args);
		GroupService service = new GroupService();
		service.test();
	}

}
