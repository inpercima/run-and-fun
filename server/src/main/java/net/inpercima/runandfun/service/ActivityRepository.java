package net.inpercima.runandfun.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import net.inpercima.runandfun.app.model.AppActivity;

@Repository
public interface ActivityRepository extends ElasticsearchRepository<AppActivity, String> {

    Page<AppActivity> findAllByTypeOrderByDateDesc(String type, Pageable pageable);

    int countByType(String type);
}
