package org.wanggz.algorithm.consistency.caculate.impl;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wanggz.algorithm.consistency.HashAlgorithm;
import org.wanggz.algorithm.consistency.caculate.AntCalculator;
import org.wanggz.algorithm.consistency.caculate.CalculatorModel;
import org.wanggz.algorithm.consistency.hash.HashAlgorithms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author guangzhong.wgz
 */
public class AntCalculatorImpl implements AntCalculator {

    private static final Logger LOG = LoggerFactory.getLogger(AntCalculatorImpl.class);

    private static final int ANT_SLOT_SIZE = 100;
    private static final BigDecimal ANT_SAMPLE_PRECISION = new BigDecimal(1000000);
    private static final int ANT_GREY_LEVEL = 10;
    private static final String EXCLUSIVE_SALT = "";

    public AntCalculatorImpl() {
    }

    @Override
    public boolean allocateAndTarget(String id, Set<Integer> slots, CalculatorModel calculatorModel) {
        // 如果唯一标识为空，或计算模型为null, 则不做计算
        if (StringUtils.isBlank(id) || calculatorModel == null) {
            return false;
        }
        /**
         Ant分流量计算，灰度时长计算，条件计算
         * Ant命中:
         * 1）互斥实验， exclusiveHashCode 在antExclusiveSlots存在, 设备命中
         * 2）共享实验, hashCode小于calculatorModel.antSample流量上限, 设备命中
         **/
        long shareHashCode = getBelongedSlotNum(id, String.valueOf(calculatorModel.getAntId()), ANT_SLOT_SIZE);
        long exclusiveHashCode = getBelongedSlotNum(id, EXCLUSIVE_SALT, ANT_SLOT_SIZE);

        long hashCode;
        if (slots != null && slots.contains(exclusiveHashCode)) { // 互斥实验
            hashCode = exclusiveHashCode;

        } else if (shareHashCode < calculatorModel.getAntSample()) { // 共享实验
            hashCode = shareHashCode;

        } else {
            return false;
        }
        /**
         * 灰度时长大于零，则进行灰度概率计算
         **/
        if (calculatorModel.getGreyTime() != null && calculatorModel.getGreyTime() > 0) {
            if (!grey(calculatorModel.getBeginTime(), calculatorModel.getGreyTime(), ANT_GREY_LEVEL, hashCode)) {
                return false;
            }
        }

        /**
         * Ant 分组命中计算
         * 比较hashCode对流量进行计算, minSampleRange，maxSampleRange为百分数，故要除100
         **/
        int minSampleRange = calculatorModel.getSampleStart().multiply(ANT_SAMPLE_PRECISION.divide(BigDecimal.valueOf(100))).intValue();
        int maxSampleRange = calculatorModel.getSampleEnd().multiply(ANT_SAMPLE_PRECISION.divide(BigDecimal.valueOf(100))).intValue();
        if (hashCode < minSampleRange && hashCode >= maxSampleRange) {
            return false;
        }
        /**
         Ant 条件计算
         **/
        return target(calculatorModel.getConditions(), calculatorModel.getConditionValues());
    }

    static long getBelongedSlotNum(String key, String salt, int slotSize) {
        long keyHashCode = generateMurmurHashCode(key, salt);
        return (keyHashCode % slotSize);
    }

    /**
     * 通过antKey产生hashCode
     */
    static long generateMurmurHashCode(String key, String salt) {
        return Math.abs(HashAlgorithms.murmurHash((key + salt).getBytes()));
    }

    /**
     * 通过antKey产生hashCode
     */
    static long generateHashCode(String key, String salt) {
        HashAlgorithm func = HashAlgorithm.KETAMA_HASH;
        Long hashCode = func.hash(func.computeMd5(key, salt), 0);
        return hashCode;
    }

    /**
     * 返回1到10的灰度
     *
     * @param beginTime
     * @param greyTime  greyTime单位为分钟
     * @return
     */
    private int getCurrentGreyLevel(Date beginTime, Integer greyTime, Integer maxLevel) {
        if (beginTime == null) {
            return maxLevel;
        }
        if (greyTime == null || greyTime == 0) {
            return maxLevel;
        }
        long begin = beginTime.getTime();
        long now = System.currentTimeMillis();
        double greyFactor = (now - begin) / ((greyTime * 60 * 1000) * 1.0); //转换成毫秒进行计算
        greyFactor = Math.round(greyFactor) * maxLevel;
        int greyLevel = (int) Math.round(greyFactor);
        // 确保在1到maxLevel之间
        greyLevel = Math.max(1, greyLevel);
        greyLevel = Math.min(maxLevel, greyLevel);
        return greyLevel;
    }

    private boolean grey(Date beginTime, Integer greyTime, Integer maxLevel, long hashCode) {
        int greyMod = (int) (hashCode % maxLevel);
        return getCurrentGreyLevel(beginTime, greyTime, maxLevel) >= greyMod;
    }

    private boolean target(String expression, Map<String, Object> context) {
        // ConditionUtils.excutePreFilter(expression, context);
        // Boolean ret = TargetingExecutor.execute(expression, context);
        // 表达式计算
        return Boolean.FALSE;
    }
}
