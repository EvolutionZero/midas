package com.zero.midas.model._do;

import com.zero.midas.domain.entity.kline.KLineNode;
import lombok.Data;

import java.util.List;

/**
 * 观察窗口
 *
 * @author: fengzijian
 * @since: 2020/9/3 19:31
 * @Description:
 */
@Data
public class WatchWindow {

    /**
     * 回退周期数
     */

    private static int BACKOFF = 100;

    /**
     * 前进预测周期数
     */
    private int FORWARD = 15;

    private String code;

    private String name;

    private List<KLineNode> nodes;

    private int focus;

    public WatchWindow(int index, List<KLineNode> kline) {
        int startIndex = index - BACKOFF > 0 ? index - BACKOFF : 0;
        int endIndex = index + FORWARD > kline.size() ? kline.size() : index + FORWARD;
        this.focus = index - startIndex - 1 > 0 ? index - startIndex - 1 : 0;
        this.nodes = kline.subList(startIndex, endIndex);
        this.code = nodes.get(0).getCode();
    }


    public KLineNode focusNode() {
        return nodes.get(focus);
    }

    public List<KLineNode> judegeNodes() {
        return nodes.subList(0, focus + 1);
    }
}
