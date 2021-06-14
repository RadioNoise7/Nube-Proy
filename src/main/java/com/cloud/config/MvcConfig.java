package com.cloud.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{
    @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
        Path videoUploadDir= Paths.get("./video");
        String videoUploadPath=videoUploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("./video/**").addResourceLocations("file:/"+videoUploadPath +"/");

	}
}
