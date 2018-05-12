package com.minimall.boilerplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement(proxyTargetClass = true)
public class BoilerplateApplication extends SpringBootServletInitializer {

  private static Properties DYNAMIC_PROPERTIES = new Properties();

  @Value("{SpringBootApplication}")
  private String appRoot;

  static {
    String classesPath = BoilerplateApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    final File classFile = new File(classesPath);
    Path rootPath = Paths.get(
        Optional.ofNullable(classFile).map(File::getAbsolutePath)
            .orElse("files/app/WEB-INF/classes")
    ).getParent().getParent();
    DYNAMIC_PROPERTIES.setProperty("app.root", rootPath.toString());
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    if(!StringUtils.hasText(appRoot))
      application.properties(DYNAMIC_PROPERTIES);
    return application.sources(BoilerplateApplication.class);
  }

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(BoilerplateApplication.class);
    application.setDefaultProperties(DYNAMIC_PROPERTIES);
    application.run(args);
  }
}
