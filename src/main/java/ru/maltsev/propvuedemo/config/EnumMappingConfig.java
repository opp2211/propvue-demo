package ru.maltsev.propvuedemo.config;

import jakarta.annotation.Nonnull;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class EnumMappingConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(@Nonnull FormatterRegistry registry) {
        ApplicationConversionService.configure(registry);
    }
}
