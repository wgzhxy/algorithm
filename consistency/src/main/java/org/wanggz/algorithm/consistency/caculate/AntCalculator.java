package org.wanggz.algorithm.consistency.caculate;

import java.util.Set;

/**
 * Ant Allocation & Targetting 计算
 *
 * @author guangzhong.wgz
 */
public interface AntCalculator {

    /**
     * Ant Allocation & Targetting 计算
     *
     * @param id              Ant操作的输入id。会决定Ant结果。
     * @param slots        Ant的使用的slot列表
     *                        <ol>
     *                        <li>antExclusiveSlots为空，则为共享实验；不为空，则为互斥实验</li>
     *                        </ol>
     * @param calculatorModel Ａ&T计算的模型, 包括了A&T计算必要参数
     *                        <ol>
     *                        <li>antId: 用于共享Ant做一致性hash随机盐</li>
     *                        <li>greyTime(灰度时长)：用于做分阶段Ant下发用，从开始时间到结束时间分成10个阶段，随着时间推移命中的概率增加一个阶段</li>
     *                        <li>beginTime(生效时间): 与greyTime一起使用，用来做分阶段发布</li>
     *                        <li>antSample(流量阀值): 当前设备hashcode与阀值比较，决定是否命中分组</li>
     *                        <li>sampleStart(流量概念的开始值): 与sampleEnd一起使用，计算AntItem命中范围</li>
     *                        <li>sampleEnd(流量概念的结束值): 与sampleStart一起使用，计算AntItem命中范围</li>
     *                        <li>conditions(条件表达式): 计算AntItem是否合中</li>
     *                        <li>conditionValues(条件值集合): 与条件表达式一起使用，做条件计算</li>
     *                        </ol>
     * @return 计算结果
     * <ol>
     * <li>true, 命中</li>
     * <li>false,不命中</li>
     * </ol>
     */
    boolean allocateAndTarget(String id, Set<Integer> slots, CalculatorModel calculatorModel);
}
