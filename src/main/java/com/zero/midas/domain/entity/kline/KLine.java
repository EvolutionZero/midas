package com.zero.midas.domain.entity.kline;


import com.zero.midas.domain.strategy.Pattern;
import com.zero.midas.domain.strategy.impl.Venus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class KLine {

    private Map<String, Pattern> patterns;

    private List<KLineNode> klines;

    public KLine(Map<String, Pattern> patterns, List<KLineNode> klines) {
        this.patterns = patterns;
        this.klines = klines;
    }

    public boolean isVenus() {
        return Optional.ofNullable(patterns.get(Venus.class.getSimpleName().toLowerCase())).map(pattern -> pattern.judge(klines)).orElse(false);
    }

}
