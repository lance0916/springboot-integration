package com.example.service;

/**
 * @author WuQinglong
 * @since 2023/2/18 09:42
 */
public class TimestampUuidService implements IUuidService {

    @Override
    public String newUuid() {
        return System.currentTimeMillis() + "";
    }
}
