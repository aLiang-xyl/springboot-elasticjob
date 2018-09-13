package cn.demo.scheduler.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * 从spring容器中获取bean
 * 
 * @author xing
 *
 */
@Configuration
public class BeanTools implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}

	/**
	 * 根据class类型获取bean
	 * 
	 * @param classname
	 * @return
	 */
	public static <T> T getBean(Class<T> classname) {
		try {
			T bean = applicationContext.getBean(classname);
			return bean;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据class类型和名称获取bean
	 * 
	 * @param classname
	 * @param name
	 * @return
	 */
	public static <T> T getBean(Class<T> classname, String name) {
		try {
			T bean = applicationContext.getBean(name, classname);
			return bean;
		} catch (Exception e) {
			return null;
		}
	}

}