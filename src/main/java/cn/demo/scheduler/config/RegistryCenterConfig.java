/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package cn.demo.scheduler.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

@Configuration
@ConfigurationProperties(prefix = "demo.elasticjob.zk-config")
public class RegistryCenterConfig {

	/**
	 * 连接Zookeeper服务器的列表. 包括IP地址和端口号. 多个地址用逗号分隔. 如: host1:2181,host2:2181
	 */
	private String serverLists;

	/**
	 * 命名空间.
	 */
	private String namespace;

	/**
	 * 等待重试的间隔时间的初始值. 单位毫秒.
	 */
	private int baseSleepTimeMilliseconds;

	/**
	 * 等待重试的间隔时间的最大值. 单位毫秒.
	 */
	private int maxSleepTimeMilliseconds;

	/**
	 * 最大重试次数.
	 */
	private int maxRetries;

	/**
	 * 登录权限
	 */
	private String digest;

	@Bean(initMethod = "init")
	public ZookeeperRegistryCenter zookeeperRegistryCenter() {
		ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(serverLists, namespace);
		zookeeperConfiguration.setMaxRetries(maxRetries);
		zookeeperConfiguration.setBaseSleepTimeMilliseconds(baseSleepTimeMilliseconds);
		zookeeperConfiguration.setMaxSleepTimeMilliseconds(maxSleepTimeMilliseconds);
		zookeeperConfiguration.setDigest(digest);
		return new ZookeeperRegistryCenter(zookeeperConfiguration);
	}

	public String getServerLists() {
		return serverLists;
	}

	public void setServerLists(String serverLists) {
		this.serverLists = serverLists;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public int getBaseSleepTimeMilliseconds() {
		return baseSleepTimeMilliseconds;
	}

	public void setBaseSleepTimeMilliseconds(int baseSleepTimeMilliseconds) {
		this.baseSleepTimeMilliseconds = baseSleepTimeMilliseconds;
	}

	public int getMaxSleepTimeMilliseconds() {
		return maxSleepTimeMilliseconds;
	}

	public void setMaxSleepTimeMilliseconds(int maxSleepTimeMilliseconds) {
		this.maxSleepTimeMilliseconds = maxSleepTimeMilliseconds;
	}

	public int getMaxRetries() {
		return maxRetries;
	}

	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

}
