package com.example.demo.fixture;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import org.springframework.stereotype.Component;


import com.example.demo.model.request.ClientCreateRequest;

@Component
public class ClientCreateTemplateLoader implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(ClientCreateRequest.class).addTemplate("valid", new Rule() {{
            add("name", "douglas");
            add("phone", "1199999999");
        }});

        Fixture.of(ClientCreateRequest.class).addTemplate("valid_TestUser", new Rule() {{
            add("name", "TestUser");
            add("phone", "1199999999");
        }});

        Fixture.of(ClientCreateRequest.class).addTemplate("invalid_phone", new Rule() {{
            add("name", "douglas");
            add("phone", "textas1234");
        }});

        Fixture.of(ClientCreateRequest.class).addTemplate("invalid_phone_2", new Rule() {{
            add("name", "douglas");
            add("phone", "1234");
        }});

        Fixture.of(ClientCreateRequest.class).addTemplate("invalid_empty", new Rule() {{
            add("name", "");
            add("phone", "");
        }});
    }
}
