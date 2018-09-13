package cn.demo.scheduler.task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

public class DemoDataflowJob implements DataflowJob<String> {

	private Logger log = LoggerFactory.getLogger(DemoDataflowJob.class);

	/*
	 * 流式处理数据只有fetchData方法的返回值为null或集合长度为空时，作业才停止抓取，否则作业将一直运行下去；
	 * 非流式处理数据则只会在每次作业执行过程中执行一次fetchData方法和processData方法，随即完成本次作业。
	 * 如果采用流式作业处理方式，建议processData处理数据后更新其状态， 避免fetchData再次抓取到，从而使得作业永不停止。
	 * 流式数据处理参照TbSchedule设计，适用于不间歇的数据处理。
	 */
	@Override
	public List<String> fetchData(final ShardingContext shardingContext) {
		List<String> list = new ArrayList<String>();
		list.add(UUID.randomUUID().toString());
		list.add(UUID.randomUUID().toString());
		return list;
	}

	@Override
	public void processData(final ShardingContext shardingContext, final List<String> data) {
		log.info("收到数据{}", data);
	}
}
