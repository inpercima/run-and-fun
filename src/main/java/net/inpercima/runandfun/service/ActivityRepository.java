package net.inpercima.runandfun.service;

import net.inpercima.runandfun.model.Activity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends ElasticsearchRepository<Activity, String> {

    Page<Activity> findAllByTypeOrderByDateDesc(String type, Pageable pageable);

    int countByType(String type);

}
