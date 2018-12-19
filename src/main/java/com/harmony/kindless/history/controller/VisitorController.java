package com.harmony.kindless.history.controller;

import com.harmony.kindless.apis.domain.history.VisitorRecord;
import com.harmony.kindless.history.service.VisitorRecordService;
import com.harmony.umbrella.data.JpaQueryBuilder;
import com.harmony.umbrella.data.QueryBundle;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wuxii
 */
@RestController
@RequestMapping("/visitor")
public class VisitorController {

    private final Random random = new Random();
    private static final Map<Integer, String> resourceTypes = new HashMap<>();

    private static int poolSize = 50;
    private static boolean started = false;
    private static final ExecutorService executor = Executors.newFixedThreadPool(poolSize);

    @Autowired
    private VisitorRecordService visitorRecordService;

    static {
        resourceTypes.put(1, "主页");
        resourceTypes.put(2, "日志");
        resourceTypes.put(3, "电台");
        resourceTypes.put(4, "游戏");
        resourceTypes.put(5, "房间");
    }

    @GetMapping("/visit")
    public String visit() {
        Long userId = randomId();
        Long visitorId = randomId();
        Long resourceId = randomId();
        int type = random.nextInt(5) + 1;
        String remark = resourceTypes.get(type);
        //
        VisitorRecord record = new VisitorRecord();
        record.setRemark("用户访问了你的" + remark + ": " + resourceId);
        record.setResourceId(resourceId);
        record.setType(type);
        record.setUserId(userId);
        record.setVisitorId(visitorId);
        record.setVisitAt(new Date());
        visitorRecordService.saveOrUpdate(record);
        return "ok";
    }

    @GetMapping("/records")
    public List<VisitorRecord> records(Long userId) {
        QueryBundle<VisitorRecord> bundle = JpaQueryBuilder
                .newBuilder(VisitorRecord.class)
                .equal("userId", userId)
                .desc("visitAt")
                .bundle();
        return visitorRecordService.findList(bundle);
    }

    @GetMapping("start")
    public synchronized String start() {
        if (!started) {
            started = true;
            for (int i = 0; i < poolSize; i++) {
                executor.execute(workRunner);
            }
        }
        return "ok";
    }

    @GetMapping("/stop")
    public synchronized String stop() {
        if (started) {
            started = false;
        }
        return "ok";
    }

    private Long randomId() {
        int first = random.nextInt(9) + 1;
        return Long.valueOf(first + RandomStringUtils.randomNumeric(5));
    }

    private final Runnable workRunner = () -> {
        while (started) {
            visit();
        }
    };

}
