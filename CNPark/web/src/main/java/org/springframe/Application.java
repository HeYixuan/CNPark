package org.springframe;


import org.springframe.util.DateUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;

import java.util.Date;

@SpringBootApplication
public class Application implements EmbeddedServletContainerCustomizer {

    public static void main(String[] args) {
        Date date = DateUtils.add(1);
        System.err.println(date);
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.setPort(8081);
    }

}
