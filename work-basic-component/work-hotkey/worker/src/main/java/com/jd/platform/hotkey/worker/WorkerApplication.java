package com.jd.platform.hotkey.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class WorkerApplication {

	/* 注意整个项目最好还是在 jdk8 中进行运行, 不要超过这个版本 */

	public static void main(String[] args) {
		SpringApplication.run(WorkerApplication.class, args);
	}

}
