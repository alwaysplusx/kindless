package com.harmony.kindless.core.history.controller;

import com.harmony.kindless.apis.domain.history.VisitorRecord;
import com.harmony.kindless.core.history.service.VisitorRecordService;
import com.harmony.kindless.apis.WorkRunner;
import com.harmony.umbrella.data.JpaQueryBuilder;
import com.harmony.umbrella.data.QueryBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.harmony.kindless.apis.util.RandomUtils.randomId;

/**
 * @author wuxii
 */
@RestController
@RequestMapping("/visitor")
public class VisitorController {

    private static final Map<Integer, String> resourceTypes = new HashMap<>();

    static {
        resourceTypes.put(1, "主页");
        resourceTypes.put(2, "日志");
        resourceTypes.put(3, "电台");
        resourceTypes.put(4, "游戏");
        resourceTypes.put(5, "房间");
    }

    private final Random random = new Random();
    @Autowired
    private VisitorRecordService visitorRecordService;
    private WorkRunner worker;

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
    public synchronized String start(@RequestParam(defaultValue = "50") int size) {
        if (worker == null) {
            this.worker = new WorkRunner(size, this::visit);
            this.worker.start();
        }
        return "ok";
    }

    @GetMapping("/stop")
    public synchronized String stop() {
        if (worker != null) {
            this.worker.stop();
            this.worker = null;
        }
        return "ok";
    }

}
