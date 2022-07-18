/*
 * Copyright 2012-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.autoconfigure.aop;

import org.aspectj.weaver.Advice;

import org.springframework.aop.config.AopConfigUtils;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * {@link org.springframework.boot.autoconfigure.EnableAutoConfiguration
 * Auto-configuration} for Spring's AOP support. Equivalent to enabling
 * {@link EnableAspectJAutoProxy @EnableAspectJAutoProxy} in your configuration.
 * <p>
 * The configuration will not be activated if {@literal spring.aop.auto=false}. The
 * {@literal proxyTargetClass} attribute will be {@literal true}, by default, but can be
 * overridden by specifying {@literal spring.aop.proxy-target-class=false}.
 *
 * @author Dave Syer
 * @author Josh Long
 * @since 1.0.0
 * @see EnableAspectJAutoProxy
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "spring.aop", name = "auto", havingValue = "true", matchIfMissing = true)
public class AopAutoConfiguration {

	// 如果spring.aop.auto=false，表示关闭AOP

	// 如果spring.aop.auto=true或没有配置：
		// 如果依赖中有aspject，那么
		// spring.aop.proxy-target-class = false，那就用JDK动态代理
		// spring.aop.proxy-target-class = true，那就用CGLIB动态代理
		// spring.aop.proxy-target-class 没有配置，也用CGLIB动态代理

		// 如果依赖中没有aspject，那么
		// spring.aop.proxy-target-class = false，那么不会开启AOP
		// spring.aop.proxy-target-class = true，那就用CGLIB动态代理
		// spring.aop.proxy-target-class 没有配置，也用CGLIB动态代理
	    // 在这种情况下只有role为ROLE_INFRASTRUCTURE的Advisor才生效

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(Advice.class)
	// 支持AspectJ
	static class AspectJAutoProxyingConfiguration {

		@Configuration(proxyBeanMethods = false)
		@EnableAspectJAutoProxy(proxyTargetClass = false)
		@ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "false")
		// 如果配置了spring.aop.proxy-target-class = false，那就用JDK动态代理
		static class JdkDynamicAutoProxyConfiguration {

		}

		@Configuration(proxyBeanMethods = false)
		@EnableAspectJAutoProxy(proxyTargetClass = true)
		@ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "true",
				matchIfMissing = true)
		// 如果配置了spring.aop.proxy-target-class = true，那就用CGLIB动态代理
		// 如果没有配置spring.aop.proxy-target-class，那也用CGLIB动态代理
		static class CglibAutoProxyConfiguration {

		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnMissingClass("org.aspectj.weaver.Advice")
	@ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "true",
			matchIfMissing = true)
	// 如果没有aspectj的依赖，默认也会开启AOP，但是只有role为ROLE_INFRASTRUCTURE的Advisor才生效，并且会走cglib进行动态代理
	// spring.aop.proxy-target-class如果为false，那就不会开启AOP功能，为false表示要走jdk动态代理
	static class ClassProxyingConfiguration {

		@Bean
		static BeanFactoryPostProcessor forceAutoProxyCreatorToUseClassProxying() {
			return (beanFactory) -> {
				if (beanFactory instanceof BeanDefinitionRegistry) {
					BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
					// 向Spring容器中添加一个InfrastructureAdvisorAutoProxyCreator，一个BeanPostProcessor
					AopConfigUtils.registerAutoProxyCreatorIfNecessary(registry);
					AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);
				}
			};
		}

	}

}
