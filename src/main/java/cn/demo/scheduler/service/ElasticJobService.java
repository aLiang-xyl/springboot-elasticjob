package cn.demo.scheduler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.demo.scheduler.dao.ElasticJobConfigDao;
import cn.demo.scheduler.entity.ElasticJobConfigBean;
import cn.demo.scheduler.entity.ElasticJobConfigLogBean;
import cn.demo.scheduler.runner.ElasticJobRunner;

/**
 * job service
 * 
 * @author xing
 *
 */
@Service
public class ElasticJobService {

	@Autowired
	private ElasticJobConfigDao elasticJobConfigDao;
	
	public List<ElasticJobConfigBean> getElasticJobConfigList(String jobName, Integer startIndex, Integer size) {
		return elasticJobConfigDao.getElasticJobConfigList(jobName, startIndex, size);
	}
	
	public long getElasticJobConfigCount(String jobName) {
		return elasticJobConfigDao.getElasticJobConfigCount(jobName);
	}
	
	public int insert(ElasticJobConfigBean config) {
		return elasticJobConfigDao.insert(config);
	}
	
	/**
	 * 更新
	 * 
	 * @param config
	 * @return
	 */
	public int update(ElasticJobConfigBean config) {
		int status = elasticJobConfigDao.update(config);
		if (status == 1) {
			elasticJobConfigDao.backups(config);
			ElasticJobRunner.setJobConfig(config.getJobName(), config.getJobConfig());
		}
		return status;
	}
	
	public ElasticJobConfigBean getElasticJobConfigById(String id) {
		return elasticJobConfigDao.getElasticJobConfigById(id);
	}
	
	public int delete(String id) {
		ElasticJobConfigBean elasticJobConfigById = getElasticJobConfigById(id);
		elasticJobConfigDao.backups(elasticJobConfigById);
		return elasticJobConfigDao.delete(id);
	}

	public List<ElasticJobConfigLogBean> getElasticJobConfigLogList(String jobName, Integer startIndex, Integer size) {
		return elasticJobConfigDao.getElasticJobConfigLogList(jobName,startIndex,size);
	}

	public long getElasticJobConfigLogCount(String jobName) {
		return elasticJobConfigDao.getElasticJobConfigLogCount(jobName);
	}
}
