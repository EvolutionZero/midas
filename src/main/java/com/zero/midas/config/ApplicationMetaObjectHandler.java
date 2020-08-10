package com.zero.midas.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ApplicationMetaObjectHandler
        implements MetaObjectHandler {
    private static final Logger log = LoggerFactory.getLogger(ApplicationMetaObjectHandler.class);

    public void insertFill(MetaObject metaObject) {
        setInsertFieldValByName("insertTime", LocalDateTime.now(), metaObject);
    }

    public void updateFill(MetaObject metaObject) {
        setUpdateFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
}
