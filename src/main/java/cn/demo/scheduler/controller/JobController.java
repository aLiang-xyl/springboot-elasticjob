package cn.demo.scheduler.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.demo.scheduler.entity.ElasticJobConfigBean;
import cn.demo.scheduler.entity.ElasticJobConfigLogBean;
import cn.demo.scheduler.service.ElasticJobService;

@Controller
@RequestMapping("job")
public class JobController {

	private Logger log = LoggerFactory.getLogger(JobController.class);

	@Autowired
	private ElasticJobService elasticJobService;

	@GetMapping("getElasticJobConfigList")
	@ResponseBody
	public Map<String, Object> getElasticJobConfigList(String jobName, Integer startIndex, Integer size) {
		List<ElasticJobConfigBean> list = elasticJobService.getElasticJobConfigList(jobName, startIndex, size);
		long count = elasticJobService.getElasticJobConfigCount(jobName);
		Map<String, Object> result = new HashMap<>();
		result.put("rows", list);
		result.put("total", count);
		return result;
	}

	@PostMapping("addOrUpdateElasticJobConfig")
	@ResponseBody
	public int addOrUpdateElasticJobConfig(ElasticJobConfigBean elasticJobConfigBean) {
		log.info("更新或新增 {} ", elasticJobConfigBean.toString());
		if (elasticJobConfigBean.getId() == null) {
			return elasticJobService.insert(elasticJobConfigBean);
		} else {
			return elasticJobService.update(elasticJobConfigBean);
		}
	}

	@GetMapping("getElasticJobConfig")
	@ResponseBody
	public ElasticJobConfigBean getElasticJobConfig(String id) {
		return elasticJobService.getElasticJobConfigById(id);
	}

	@PostMapping("delete")
	@ResponseBody
	public int delete(String id) {
		return elasticJobService.delete(id);
	}

	@GetMapping("getElasticJobConfigLogList")
	@ResponseBody
	public Map<String, Object> getElasticJobConfigLogList(String jobName, Integer startIndex, Integer size) {
		List<ElasticJobConfigLogBean> re = elasticJobService.getElasticJobConfigLogList(jobName, startIndex, size);
		long count = elasticJobService.getElasticJobConfigLogCount(jobName);
		Map<String, Object> result = new HashMap<>();
		result.put("rows", re);
		result.put("total", count);
		return result;
	}

}
