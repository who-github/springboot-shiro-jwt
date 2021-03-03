package com.woniuxy.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//mybatis-plus的配置类：
@Configuration
public class MybatisPlusConfig {

//    注册组件：分页查询
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
//
////    注册组件：乐观锁
//    @Bean
//    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
//        return new OptimisticLockerInterceptor();
//    }
//
////    注册组件:逻辑删除
//    @Bean
//    public ISqlInjector iSqlInjector(){
//        return new LogicSqlInjector();
//    }

    //    注册一个性能组件
//    @Bean
//    public PerformanceInterceptor performanceInterceptor(){
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
////        sql 语句最大执行时间
//        performanceInterceptor.setMaxTime(10000);
////        格式化sql语句查询
//        performanceInterceptor.setFormat(true);
//        return performanceInterceptor;
//    }

}
