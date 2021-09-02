package com.whj.ct.web.service;

import com.whj.ct.web.bean.Calllog;
import com.whj.ct.web.dao.CalllogDao;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CalllogService {
    List<Calllog> queryMonthDatas(String tel, String calltime);
}
