package com.zero.midas.domain.entity.kline;


import com.zero.midas.domain.specification.KLineShape;
import com.zero.midas.domain.specification.impl.Venus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class KLine {

    @Autowired
    private Map<String, KLineShape> specifications;

    private List<KLineNode> klines;

    public KLine(List<KLineNode> klines) {
        this.klines = klines;
    }

    public boolean isVenus() {
        return Optional.ofNullable(specifications.get(Venus.class.getSimpleName().toLowerCase())).map(pattern -> pattern.judge(klines)).orElse(false);
    }

}
