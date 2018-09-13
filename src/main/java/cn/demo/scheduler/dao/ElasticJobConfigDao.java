package cn.demo.scheduler.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.kelaile.common.enums.DateFormatEnum;
import com.kelaile.common.utils.DateUtil;

import cn.demo.scheduler.entity.ElasticJobConfigBean;
import cn.demo.scheduler.entity.ElasticJobConfigLogBean;

/**
 * 查询配置
 * 
 * @author xing
 *
 */
@Repository
public class ElasticJobConfigDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 查询配置信息
	 * 
	 * @return
	 */
	public List<ElasticJobConfigBean> getElasticJobConfigList() {
		String sql = "SELECT * FROM `elastic_job_config`";
		return jdbcTemplate.query(sql.toString(),
				new BeanPropertyRowMapper<ElasticJobConfigBean>(ElasticJobConfigBean.class));
	}

	/**
	 * 分页获取
	 * 
	 * @param jobName
	 * @param startIndex
	 * @param size
	 * @return
	 */
	public List<ElasticJobConfigBean> getElasticJobConfigList(String jobName, Integer startIndex, Integer size) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT                  ");
		sql.append(" 	*                    ");
		sql.append(" FROM                    ");
		sql.append(" 	`elastic_job_config` ");
		if (!StringUtils.isEmpty(jobName)) {
			sql.append(" WHERE                   ");
			sql.append(" 	job_name = ?         ");
			params.add(jobName);
		}
		params.add(startIndex);
		params.add(size);
		sql.append(" LIMIT ?,?               ");
		return jdbcTemplate.query(sql.toString(), params.toArray(),
				new BeanPropertyRowMapper<ElasticJobConfigBean>(ElasticJobConfigBean.class));
	}

	/**
	 * 查询数量
	 * 
	 * @param jobName
	 * @return
	 */
	public long getElasticJobConfigCount(String jobName) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT                  ");
		sql.append(" 	COUNT(1)             ");
		sql.append(" FROM                    ");
		sql.append(" 	`elastic_job_config` ");
		if (!StringUtils.isEmpty(jobName)) {
			sql.append(" WHERE                   ");
			sql.append(" 	job_name = ?         ");
			params.add(jobName);
		}
		return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Long.class);
	}

	/**
	 * 新增
	 * 
	 * @param config
	 * @return
	 */
	public int insert(ElasticJobConfigBean config) {
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO `elastic_job_config` (       ");
		sql.append(" 	`job_name`,                           ");
		sql.append(" 	`cron`,                               ");
		sql.append(" 	`sharding_total_count`,               ");
		sql.append(" 	`sharding_item_parameters`,           ");
		sql.append(" 	`job_parameter`,                      ");
		sql.append(" 	`failover`,                           ");
		sql.append(" 	`misfire`,                            ");
		sql.append(" 	`description`,                        ");
		sql.append(" 	`job_class`,                          ");
		sql.append(" 	`streaming_process`,                  ");
		sql.append(" 	`job_config`                          ");
		sql.append(" )                                        ");
		sql.append(" VALUES                                   ");
		sql.append(" 	(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)     ");
		List<Object> params = getParams(config);
		return jdbcTemplate.update(sql.toString(), params.toArray());
	}

	/**
	 * 更新
	 * 
	 * @param config
	 * @return
	 */
	public int update(ElasticJobConfigBean config) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE `elastic_job_config`      ");
		sql.append(" SET                              ");
		sql.append("  `job_name` = ?,                 ");
		sql.append("  `cron` = ?,                     ");
		sql.append("  `sharding_total_count` = ?,     ");
		sql.append("  `sharding_item_parameters` = ?, ");
		sql.append("  `job_parameter` = ?,            ");
		sql.append("  `failover` = ?,                 ");
		sql.append("  `misfire` = ?,                  ");
		sql.append("  `description` = ?,              ");
		sql.append("  `job_class` = ?,                ");
		sql.append("  `streaming_process` = ?,        ");
		sql.append("  `job_config` = ?                ");
		sql.append(" WHERE                            ");
		sql.append(" 	`id` = ?                      ");
		List<Object> params = getParams(config);
		params.add(config.getId());
		return jdbcTemplate.update(sql.toString(), params.toArray());
	}

	/**
	 * 数据备份
	 * 
	 * @param config
	 * @return
	 */
	public int backups(ElasticJobConfigBean config) {
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO `elastic_job_config_log` (   ");
		sql.append(" 	`job_name`,                           ");
		sql.append(" 	`cron`,                               ");
		sql.append(" 	`sharding_total_count`,               ");
		sql.append(" 	`sharding_item_parameters`,           ");
		sql.append(" 	`job_parameter`,                      ");
		sql.append(" 	`failover`,                           ");
		sql.append(" 	`misfire`,                            ");
		sql.append(" 	`description`,                        ");
		sql.append(" 	`job_class`,                          ");
		sql.append(" 	`streaming_process`,                  ");
		sql.append(" 	`job_config`,                         ");
		sql.append(" 	`create_time`                         ");
		sql.append(" )                                        ");
		sql.append(" VALUES                                   ");
		sql.append(" 	(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?  )");
		List<Object> params = getParams(config);
		params.add(DateUtil.formatter(LocalDateTime.now(), DateFormatEnum.YYYY_MM_DD_HH_MM_SS));
		return jdbcTemplate.update(sql.toString(), params.toArray());
	}

	private List<Object> getParams(ElasticJobConfigBean config) {
		List<Object> params = new LinkedList<Object>();
		params.add(config.getJobName());
		params.add(config.getCron());
		params.add(config.getShardingTotalCount());
		params.add(config.getShardingItemParameters());
		params.add(config.getJobParameter());
		params.add(config.getFailover());
		params.add(config.getMisfire());
		params.add(config.getDescription());
		params.add(config.getJobClass());
		params.add(config.getStreamingProcess());
		params.add(config.getJobConfig());
		return params;
	}

	public ElasticJobConfigBean getElasticJobConfigById(String id) {
		String sql = "SELECT * FROM `elastic_job_config` WHERE id=?";
		return jdbcTemplate.queryForObject(sql.toLowerCase(), new Object[] {id},
				new BeanPropertyRowMapper<ElasticJobConfigBean>(ElasticJobConfigBean.class));
	}
	
	public int delete(String id) {
		String sql = "DELETE FROM `elastic_job_config` where id=?";
		return jdbcTemplate.update(sql.toString(), new Object[] {id});
	}

	public List<ElasticJobConfigLogBean> getElasticJobConfigLogList(String jobName, Integer startIndex, Integer size) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT                  ");
		sql.append(" 	*                    ");
		sql.append(" FROM                    ");
		sql.append(" 	`elastic_job_config_log` ");
		if (!StringUtils.isEmpty(jobName)) {
			sql.append(" WHERE                   ");
			sql.append(" 	job_name = ?         ");
			params.add(jobName);
		}
		params.add(startIndex);
		params.add(size);
		sql.append(" LIMIT ?,?               ");
		return jdbcTemplate.query(sql.toString(), params.toArray(),
				new BeanPropertyRowMapper<ElasticJobConfigLogBean>(ElasticJobConfigLogBean.class));
	}

	public long getElasticJobConfigLogCount(String jobName) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT                  ");
		sql.append(" 	COUNT(1)             ");
		sql.append(" FROM                    ");
		sql.append(" 	`elastic_job_config_log` ");
		if (!StringUtils.isEmpty(jobName)) {
			sql.append(" WHERE                   ");
			sql.append(" 	job_name = ?         ");
			params.add(jobName);
		}
		return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Long.class);
	}
}
