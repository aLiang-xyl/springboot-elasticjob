package cn.demo.scheduler.entity;

/**
 * 定时任务配置
 * 
 * @author xing
 *
 */
public class ElasticJobConfigBean {
	private Long id;
	private String jobName;
	private String cron;
	private Integer shardingTotalCount;
	private String shardingItemParameters;
	private String jobParameter;
	private String failover;
	private String misfire;
	private String description;
	private String jobClass;
	private String streamingProcess;
	private String jobConfig;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public Integer getShardingTotalCount() {
		return shardingTotalCount;
	}

	public void setShardingTotalCount(Integer shardingTotalCount) {
		this.shardingTotalCount = shardingTotalCount;
	}

	public String getShardingItemParameters() {
		return shardingItemParameters;
	}

	public void setShardingItemParameters(String shardingItemParameters) {
		this.shardingItemParameters = shardingItemParameters;
	}

	public String getJobParameter() {
		return jobParameter;
	}

	public void setJobParameter(String jobParameter) {
		this.jobParameter = jobParameter;
	}

	public String getFailover() {
		return failover;
	}

	public void setFailover(String failover) {
		this.failover = failover;
	}

	public String getMisfire() {
		return misfire;
	}

	public void setMisfire(String misfire) {
		this.misfire = misfire;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getStreamingProcess() {
		return streamingProcess;
	}

	public void setStreamingProcess(String streamingProcess) {
		this.streamingProcess = streamingProcess;
	}

	public String getJobConfig() {
		return jobConfig;
	}

	public void setJobConfig(String jobConfig) {
		this.jobConfig = jobConfig;
	}

	@Override
	public String toString() {
		return "ElasticJobConfigBean [id=" + id + ", jobName=" + jobName + ", cron=" + cron + ", shardingTotalCount="
				+ shardingTotalCount + ", shardingItemParameters=" + shardingItemParameters + ", jobParameter="
				+ jobParameter + ", failover=" + failover + ", misfire=" + misfire + ", description=" + description
				+ ", jobClass=" + jobClass + ", streamingProcess=" + streamingProcess + ", jobConfig=" + jobConfig
				+ "]";
	}

}
