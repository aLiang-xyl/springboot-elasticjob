package cn.demo.scheduler.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

import cn.demo.scheduler.dao.ElasticJobConfigDao;
import cn.demo.scheduler.entity.ElasticJobConfigBean;
import cn.demo.scheduler.util.BeanTools;

/**
 * 定时任务配置
 * 
 * @author xing
 *
 */
@Component
public class ElasticJobRunner implements CommandLineRunner {

	private Logger log = LoggerFactory.getLogger(ElasticJobRunner.class);

	private static final Map<String, String> JOB_CONFIG_MAP = new HashMap<>();

	@Resource
	private ZookeeperRegistryCenter zookeeperRegistryCenter;

	@Resource
	private JobEventConfiguration jobEventConfiguration;

	@Resource
	private ElasticJobConfigDao elasticJobConfigDao;

	/**
	 * 根据jobName获取配置
	 * 
	 * @param jobName
	 * @return
	 */
	public static String getJobConfig(String jobName) {
		return JOB_CONFIG_MAP.get(jobName);
	}

	/**
	 * 更改jobConfig
	 * 
	 * @param jobName
	 * @param jobConfig
	 */
	public static void setJobConfig(String jobName, String jobConfig) {
		JOB_CONFIG_MAP.put(jobName, jobConfig);
	}

	/**
	 * 注入任务
	 * 
	 * @param elasticJobConfigBean
	 */
	@SuppressWarnings("unchecked")
	private void registryJob(ElasticJobConfigBean elasticJobConfigBean) {
		try {
			Class<? extends ElasticJob> jobClass = (Class<? extends ElasticJob>) Class
					.forName(elasticJobConfigBean.getJobClass());
			ElasticJob elasticJob = getInstance(jobClass);
			SpringJobScheduler jobScheduler = jobScheduler(elasticJob, jobClass, elasticJobConfigBean);
			jobScheduler.init();
			log.info("初始化定时任务 {} ", elasticJobConfigBean.toString());
		} catch (Exception e) {
			log.error("注册Job出错：{} ", elasticJobConfigBean.toString(), e);
		}

	}

	/**
	 * 通过反射对有@Resource和@Autowired的属性赋值
	 * 
	 * @param jobClass
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private ElasticJob getInstance(Class<? extends ElasticJob> jobClass)
			throws InstantiationException, IllegalAccessException {
		Field[] declaredFields = jobClass.getDeclaredFields();
		ElasticJob newInstance = jobClass.newInstance();
		for (Field field : declaredFields) {
			Annotation[] annotations = field.getAnnotations();
			if (annotations == null || annotations.length == 0) {
				continue;
			}
			boolean flag = false;
			for (Annotation annotation : annotations) {
				Class<? extends Annotation> annotationType = annotation.annotationType();
				if (annotationType.equals(Resource.class) || annotationType.equals(Autowired.class)) {
					flag = true;
					break;
				}
			}
			if (flag) {
				field.setAccessible(true);
				field.set(newInstance, BeanTools.getBean(field.getType()));
			}
		}
		return newInstance;
	}

	/**
	 * 注册SpringJobScheduler
	 * 
	 * @param elasticJob
	 * @param jobClass
	 * @param elasticJobConfigBean
	 * @return
	 */
	private SpringJobScheduler jobScheduler(ElasticJob elasticJob, Class<? extends ElasticJob> jobClass,
			ElasticJobConfigBean elasticJobConfigBean) {
		LiteJobConfiguration build = LiteJobConfiguration.newBuilder(jobConfiguration(elasticJob, elasticJobConfigBean))
				.overwrite(true).build();
		SpringJobScheduler springJobScheduler = new SpringJobScheduler(elasticJob, zookeeperRegistryCenter, build,
				jobEventConfiguration);
		return springJobScheduler;
	}

	/**
	 * job配置
	 * 
	 * @param elasticJob
	 * @param elasticJobConfigBean
	 * @return
	 */
	private JobTypeConfiguration jobConfiguration(final ElasticJob elasticJob,
			ElasticJobConfigBean elasticJobConfigBean) {
		JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration
				.newBuilder(elasticJobConfigBean.getJobName(), elasticJobConfigBean.getCron(),
						elasticJobConfigBean.getShardingTotalCount())
				.shardingItemParameters(elasticJobConfigBean.getShardingItemParameters())
				.misfire(Boolean.valueOf(elasticJobConfigBean.getMisfire()))
				.description(elasticJobConfigBean.getDescription())
				.failover(Boolean.valueOf(elasticJobConfigBean.getFailover()))
				.jobParameter(elasticJobConfigBean.getJobParameter())
				.build();
		if (elasticJob instanceof SimpleJob) {
			return new SimpleJobConfiguration(jobCoreConfiguration, elasticJob.getClass().getCanonicalName());
		}
		if (elasticJob instanceof DataflowJob) {
			return new DataflowJobConfiguration(jobCoreConfiguration, elasticJob.getClass().getCanonicalName(),
					Boolean.valueOf(elasticJobConfigBean.getStreamingProcess()));
		}
		throw new RuntimeException("未知类型定时任务：" + elasticJob.getClass().getName());
	}

	@Override
	public void run(String... args) throws Exception {
		List<ElasticJobConfigBean> elasticJobConfigList = elasticJobConfigDao.getElasticJobConfigList();
		if (elasticJobConfigList == null || elasticJobConfigList.size() == 0) {
			return;
		}
		elasticJobConfigList.forEach(elasticJobConfig -> {
			registryJob(elasticJobConfig);
			JOB_CONFIG_MAP.put(elasticJobConfig.getJobName(), elasticJobConfig.getJobConfig());
		});
	}
}
