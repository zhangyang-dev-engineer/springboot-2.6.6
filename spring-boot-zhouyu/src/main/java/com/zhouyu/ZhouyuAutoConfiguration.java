package com.zhouyu;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(MyApplication.class)
public class ZhouyuAutoConfiguration {
}
