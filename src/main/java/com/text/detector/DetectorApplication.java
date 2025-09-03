package com.text.detector;

import com.text.detector.controller.TextController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DetectorApplication {
    

	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DetectorApplication.class, args);

        TextController textController = context.getBean(TextController.class);

        // Call method from the controller
        textController.readTextFile();

	}

}
