package com.cxs.code.generator.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/3/20 10:04
 */
@Configuration
public class Config {
  @Value("${server.port}")
  private String port;

  @Value("${spring.velocity.properties.file.resource.loader.class}")
  private String classLoader;

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public String getClassLoader() {
    return classLoader;
  }

  public void setClassLoader(String classLoader) {
    this.classLoader = classLoader;
  }

}
