package com.zr.service;

import com.zr.domain.SysLog;

import java.util.List;

public interface ISysLogService {

    void save(SysLog log) throws Exception;

    List<SysLog> findAll() throws Exception;

}
