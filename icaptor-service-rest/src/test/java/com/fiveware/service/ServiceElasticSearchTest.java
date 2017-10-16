package com.fiveware.service;

import com.fiveware.config.ApiUrlPersistence;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ServiceElasticSearchTest {

    @Mock
    ServiceElasticSearch serviceElasticSearch;

    @Mock
    private ApiUrlPersistence apiUrlPersistence;

    @Mock
    private RestTemplate restTemplate;


    String id;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        restTemplate = new RestTemplate();

       when(apiUrlPersistence.endPointElasticSearch("icaptor-automation","Test"))
               .thenReturn("http://54.232.96.63:9200/icaptor-automation/Test");
        serviceElasticSearch = new ServiceElasticSearch(apiUrlPersistence,restTemplate);
    }

    @Test
    public void log() throws Exception {
        class Test {
            private String nome;
            private String email;

            public String getNome() {
                return nome;
            }

            public void setNome(String nome) {
                this.nome = nome;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }
        }
        Test test = new Test();
        test.setNome("Tester");
        test.setEmail("test@icaptor.com");

        id = serviceElasticSearch.log(test);
        assertTrue(!Objects.isNull(id));

        when(apiUrlPersistence.endPointElasticSearch("icaptor-automation","Test/"+id))
                .thenReturn("http://54.232.96.63:9200/icaptor-automation/Test/"+id);
        serviceElasticSearch.delete(test,id);

    }





}