package com.example.demo.fixture;

import br.com.six2six.fixturefactory.loader.TemplateLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class FixtureLoader {

    @Autowired
    private List<TemplateLoader> templates;

    @PostConstruct
    public void init() {
        templates.forEach(TemplateLoader::load);
    }

}
