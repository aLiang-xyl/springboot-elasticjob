package cn.demo.scheduler.entity;

public class ElasticJobConfigLogBean extends ElasticJobConfigBean {
private String createTime;

public String getCreateTime() {
	return createTime;
}

public void setCreateTime(String createTime) {
	this.createTime = createTime;
}

@Override
public String toString() {
	return "ElasticJobConfigLogBean [createTime=" + createTime + ", getCreateTime()=" + getCreateTime() + ", getId()="
			+ getId() + ", getJobName()=" + getJobName() + ", getCron()=" + getCron() + ", getShardingTotalCount()="
			+ getShardingTotalCount() + ", getShardingItemParameters()=" + getShardingItemParameters()
			+ ", getJobParameter()=" + getJobParameter() + ", getFailover()=" + getFailover() + ", getMisfire()="
			+ getMisfire() + ", getDescription()=" + getDescription() + ", getJobClass()=" + getJobClass()
			+ ", getStreamingProcess()=" + getStreamingProcess() + ", getJobConfig()=" + getJobConfig()
			+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
}
}
