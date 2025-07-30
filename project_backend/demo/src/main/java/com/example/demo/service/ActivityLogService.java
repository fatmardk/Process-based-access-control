package com.example.demo.service;

import com.example.demo.entity.ActivityLog;
import com.example.demo.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    public void logChange(
            String userName,
            String tableName,
            String columnName,
            String oldValue,
            String newValue,
            String activityType // örnek: UPDATE, CREATE, DELETE
    ) {
        ActivityLog log = new ActivityLog();
        log.setUserName(userName);
        log.setTableName(tableName);
        log.setColumnName(columnName);
        log.setOldValue(oldValue);
        log.setNewValue(newValue);
        log.setActivity(activityType);

        activityLogRepository.save(log);
    }
}
