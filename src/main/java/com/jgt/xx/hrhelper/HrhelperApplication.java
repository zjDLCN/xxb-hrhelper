package com.jgt.xx.hrhelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class  HrhelperApplication {

  public static void main(String[] args) {
    SpringApplication.run(HrhelperApplication.class, args);
  }
}
