package com.zero.midas.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "midas")
public class MidasProperties {
    private Integer threshold;

    public String toString() {
        return "MidasProperties(threshold=" + getThreshold() + ")";
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $threshold = getThreshold();
        result = result * 59 + ($threshold == null ? 43 : $threshold.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof MidasProperties;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MidasProperties)) {
            return false;
        }
        MidasProperties other = (MidasProperties) o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$threshold = getThreshold();
        Object other$threshold = other.getThreshold();
        return this$threshold == null ? other$threshold == null : this$threshold.equals(other$threshold);
    }

    public Integer getThreshold() {
        return this.threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }
}
