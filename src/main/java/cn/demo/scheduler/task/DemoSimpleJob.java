package cn.demo.scheduler.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import cn.demo.api.TestBean;
import cn.demo.api.TestService;

public class DemoSimpleJob implements SimpleJob {

	private Logger log = LoggerFactory.getLogger(DemoSimpleJob.class);

	@Autowired
	private TestService testService;

	@Override
	public void execute(ShardingContext shardingContext) {
		String jobParameter = shardingContext.getJobParameter();
		log.info("test--------------------{}", jobParameter);
		TestBean testBean = new TestBean();
		testBean.setId("123");
		testBean.setName("Hello World!");
		TestBean test = testService.test(testBean);
		log.info(test.toString());
	}

}
