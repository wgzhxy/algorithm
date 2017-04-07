package org.wanggz.algorithm.consistency.caculate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by guangzhong.wgz on 2016/8/17.
 */
public class CalculatorModel implements Serializable {

    private static final long serialVersionUID = 5151807129531144957L;
    /**
     * AntID
     */
    private Long antId;
    /**
     * Ant样本
     */
    private Long antSample;
    /**
     * 灰度时间
     */
    private Integer greyTime;
    /**
     * Ant开始时间
     */
    private Date beginTime;
    /**
     * 样本范围开始
     */
    private BigDecimal sampleStart;
    /**
     * 样本范围结束
     */
    private BigDecimal sampleEnd;
    /**
     * 条件表达式
     */
    private String conditions;
    /**
     * 条件值集合
     */
    private Map<String, Object> conditionValues;

    public CalculatorModel() {

    }

    public CalculatorModel(Long antId, Long antSample, Integer greyTime, Date beginTime, BigDecimal sampleStart,
                           BigDecimal sampleEnd, String conditions, Map<String, Object> conditionValues) {
        this.antId = antId;
        this.antSample = antSample;
        this.greyTime = greyTime;
        this.beginTime = beginTime;
        this.sampleStart = sampleStart;
        this.sampleEnd = sampleEnd;
        this.conditions = conditions;
        this.conditionValues = conditionValues;
    }

    public Long getAntId() {
        return antId;
    }

    public void setAntId(Long antId) {
        this.antId = antId;
    }

    public Integer getGreyTime() {
        return greyTime;
    }

    public void setGreyTime(Integer greyTime) {
        this.greyTime = greyTime;
    }

    public BigDecimal getSampleStart() {
        return sampleStart;
    }

    public void setSampleStart(BigDecimal sampleStart) {
        this.sampleStart = sampleStart;
    }

    public BigDecimal getSampleEnd() {
        return sampleEnd;
    }

    public void setSampleEnd(BigDecimal sampleEnd) {
        this.sampleEnd = sampleEnd;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public Map<String, Object> getConditionValues() {
        return conditionValues;
    }

    public void setConditionValues(Map<String, Object> conditionValues) {
        this.conditionValues = conditionValues;
    }

    public Long getAntSample() {
        return antSample;
    }

    public void setAntSample(Long antSample) {
        this.antSample = antSample;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * CalculatorModelBuilder 创建CalculatorModel
     */
    public static class CalculatorModelBuilder {

        private Long antId;

        private Long antSample;

        private Integer greyTime;

        private Date beginTime;

        private BigDecimal sampleStart;

        private BigDecimal sampleEnd;

        private String conditions;

        private Map<String, Object> conditionValues;

        public CalculatorModelBuilder antId(final Long antId) {
            this.antId = antId;
            return this;
        }

        public CalculatorModelBuilder antSample(final Long antSample) {
            this.antSample = antSample;
            return this;
        }


        public CalculatorModelBuilder greyTime(final Integer greyTime) {
            this.greyTime = greyTime;
            return this;
        }

        public CalculatorModelBuilder beginTime(final Date beginTime) {
            this.beginTime = beginTime;
            return this;
        }

        public CalculatorModelBuilder sampleStart(final BigDecimal sampleStart) {
            this.sampleStart = sampleStart;
            return this;
        }

        public CalculatorModelBuilder sampleEnd(final BigDecimal sampleEnd) {
            this.sampleEnd = sampleEnd;
            return this;
        }

        public CalculatorModelBuilder conditions(final String conditions) {
            this.conditions = conditions;
            return this;
        }

        public CalculatorModelBuilder conditionValues(final Map<String, Object> conditionValues) {
            this.conditionValues = conditionValues;
            return this;
        }

        public CalculatorModel build() {
            return new CalculatorModel(antId, antSample, greyTime, beginTime, sampleStart,
                    sampleEnd, conditions, conditionValues);
        }
    }
}
