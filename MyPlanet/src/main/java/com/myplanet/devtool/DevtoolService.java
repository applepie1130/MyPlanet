package com.myplanet.devtool;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplanet.comm.CommonService;

@Service("devtoolSvc")
@Transactional
public class DevtoolService extends CommonService {
}
