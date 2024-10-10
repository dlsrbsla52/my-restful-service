package hig.myrestfulservice.controller;

import hig.myrestfulservice.bean.HelloWorldBean;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class HelloWorldController {

    private final MessageSource messageSource;


    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");
    }

    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBeanPathVariable(@PathVariable(name = "name") String name){
        return new HelloWorldBean(String.format("Hello World %s", name));
    }

    @GetMapping(path = "/hello-world-bean-internationalized")
    public String helloWorldBeanInternalized(){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("greeting.message", null, locale);
    }
}
