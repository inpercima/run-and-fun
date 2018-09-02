package net.inpercima.runandfun;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "net.inpercima.runandfun.service")
public class ElasticConfiguration {

    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String esNode;

    @Value("${spring.data.elasticsearch.cluster-name}")
    private String esClusterName;

    @Bean
    public Client client() throws UnknownHostException {
        String host = esNode.split(":")[0];
        Integer port = Integer.valueOf(esNode.split(":")[1]);

        Settings elasticsearchSettings = Settings.builder().put("client.transport.sniff", true)
                .put("cluster.name", esClusterName).build();
        TransportClient client = new PreBuiltTransportClient(elasticsearchSettings);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws UnknownHostException {
        return new ElasticsearchTemplate(client());
    }
}
