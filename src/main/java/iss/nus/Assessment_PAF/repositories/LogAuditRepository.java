package iss.nus.Assessment_PAF.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class LogAuditRepository {
    
    @Autowired
    RedisTemplate<String, String> template;

    public void insertRecord(String key, String value) {
        template.opsForValue().set(key, value);
        return;
    }

    // public Optional<String> getRecordById(String id) {
    //     Optional<String> result = Optional.ofNullable((String) template.opsForValue().get(id));
    //     return result;
    // }

}
