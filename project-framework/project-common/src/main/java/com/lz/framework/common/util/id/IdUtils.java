package com.lz.framework.common.util.id;

import cn.hutool.core.util.IdUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 * id生成器
 *
 * @Project: project
 * @Author: YY
 * @CreateTime: 2026-03-12  21:32
 * @Version: 1.0
 */
public class IdUtils extends IdUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 生成基于时间戳和随机数的 ID
     * 格式：年月日时分秒 (14 位) + 6 位随机数字，共 20 位
     * 示例：2024010111105912345621
     *
     * @return 22 位随机 ID
     */
    public static String generateTimeRandomId() {
        // 获取当前时间字符串（14 位）
        String timeStr = LocalDateTime.now().format(DATE_TIME_FORMATTER);

        // 生成 8 位随机数字
        StringBuilder randomStr = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 8; i++) {
            randomStr.append(random.nextInt(10));
        }
        return timeStr + randomStr.toString();
    }

    public static void main(String[] args) {
        HashSet<String> hashSet = new HashSet<>();
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            hashSet.add(generateTimeRandomId());
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - start) / 1000 + "秒");
        System.out.println(hashSet.size());
    }
}
