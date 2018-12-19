package com.harmony.kindless.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuxii
 */
@RestController
@RequestMapping("/test")
public class TestController implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String KEY_PREFIX = "random:";

    @GetMapping("/redis")
    public List<String> redis(@RequestParam(defaultValue = "1") int index) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        List<String> redisKeys = buildRedisKeys(index);
        long time = System.currentTimeMillis();
        // List<String> values = redisKeys.stream().map(valueOperations::get).collect(Collectors.toList());
        List<String> values = valueOperations.multiGet(redisKeys);
        log.info("从redis中获取数据耗时: {}ms", System.currentTimeMillis() - time);
        return values;
    }

    private List<String> buildRedisKeys(int range) {
        List<String> redisKeys = new ArrayList<>();
        for (int i = 0; i < range; i++) {
            redisKeys.add(KEY_PREFIX + i);
        }
        return redisKeys;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String text = "{\"id\":5044281310,\"screen_name\":\"澎湃新闻\",\"profile_image_url\":\"https://tvax1.sinaimg.cn/crop.11.10.275.275.180/005vnhZYly8ftjmwo0bx4j308c08cq32.jpg\",\"profile_url\":\"https://m.weibo.cn/u/5044281310?uid=5044281310&luicode=20000174\",\"statuses_count\":42335,\"verified\":true,\"verified_type\":3,\"verified_type_ext\":0,\"verified_reason\":\"澎湃新闻，专注时政与思想的媒体开放平台\",\"close_blue_v\":false,\"description\":\"有内涵的时政新媒体\",\"gender\":\"m\",\"mbtype\":12,\"urank\":47,\"mbrank\":6,\"follow_me\":false,\"following\":true,\"followers_count\":14873348,\"follow_count\":459,\"cover_image_phone\":\"https://tva1.sinaimg.cn/crop.0.0.640.640.640/549d0121tw1egm1kjly3jj20hs0hsq4f.jpg\",\"avatar_hd\":\"https://wx1.sinaimg.cn/orj480/005vnhZYly8ftjmwo0bx4j308c08cq32.jpg\",\"like\":false,\"like_me\":false,\"badge\":{\"bind_taobao\":1,\"self_media\":1,\"dzwbqlx_2016\":1,\"follow_whitelist_video\":1,\"user_name_certificate\":1,\"wenchuan_10th\":1,\"qixi_2018\":1,\"memorial_2018\":1}}";
//        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
//        for (int i = 0; i < 100; i++) {
//            valueOperations.set("random:" + i, text);
//        }
    }

}
