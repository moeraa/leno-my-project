package com.moer.daydayup.mybatisdemo;

import com.moer.daydayup.mybatisdemo.domian.City;
import com.moer.daydayup.mybatisdemo.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MybatisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisDemoApplication.class, args);
    }

    private final CityMapper cityMapper;

    public MybatisDemoApplication(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @Bean
    CommandLineRunner sampleCommandLineRunner() {
        return args -> {
            System.out.println(cityMapper.selectCityLike(null, "北京", "中国"));
        };
    }
}
