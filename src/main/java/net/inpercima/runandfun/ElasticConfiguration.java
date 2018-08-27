package net.inpercima.runandfun;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
@EnableElasticsearchRepositories(basePackages = { "net.inpercima.runandfun.service"} )
public class ElasticConfiguration {

    @Bean
    public ElasticsearchOperations elasticsearchTemplate(
            @Value("${spring.data.elasticsearch.cluster-nodes}") String esNode,
            @Value("${spring.data.elasticsearch.cluster-name}") String esClusterName) throws UnknownHostException {

        Settings esSettings = Settings.builder().put("cluster.name", esClusterName).build();

        String host = esNode.split(":")[0];
        Integer port = Integer.valueOf(esNode.split(":")[1]);

        return new ElasticsearchTemplate(new PreBuiltTransportClient(esSettings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port)));
    }
}
