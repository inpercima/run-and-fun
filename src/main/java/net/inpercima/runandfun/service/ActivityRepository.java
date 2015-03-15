package net.inpercima.runandfun.service;

import net.inpercima.runandfun.model.Activity;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends ElasticsearchRepository<Activity, String> {

}