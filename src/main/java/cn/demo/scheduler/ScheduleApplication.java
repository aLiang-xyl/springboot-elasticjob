package cn.demo.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringCloudApplication
@ComponentScan(value = { "cn.demo.scheduler"})
@EnableFeignClients(basePackages = { "cn.demo.api" })
public class ScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScheduleApplication.class, args);
	}
}
