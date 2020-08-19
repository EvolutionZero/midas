package com.zero.midas.remote;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * @author: fengzijian
 * @since: 2020/8/19 11:37
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AKQJ10RemoteTest {

    private String cookie = "log=; Hm_lvt_78c58f01938e4d85eaf619eae71b4ed1=1597765550; user=MDpFdm9sdXRpb25f6Pc6Ok5vbmU6NTAwOjM1ODU4MTY1OTo3LDExMTExMTExMTExLDQwOzQ0LDExLDQwOzYsMSw0MDs1LDEsNDA7MSwxMDEsNDA7MiwxLDQwOzMsMSw0MDs1LDEsNDA7OCwwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMSw0MDsxMDIsMSw0MDoyNDo6OjM0ODU4MTY1OToxNTk3NzY1Nzc4Ojo6MTQ3MDc1NDE0MDo2MDQ4MDA6MDoxYmE1NTk2YzI3NjFkOTc4Zjg5YWVkNTU4MThiOTlmNzc6ZGVmYXVsdF80OjE%3D; userid=348581659; u_name=Evolution_%E8%F7; escapename=Evolution_%25u6893; ticket=8260e787b21589cb3ab4e31e71310f99; spversion=20130314; historystock=300235%7C*%7C300624%7C*%7C300377%7C*%7C300033; Hm_lpvt_78c58f01938e4d85eaf619eae71b4ed1=1597768497; v=AnOtQ7EYKDOXYORyFsuJThJRAnyeqAcowT5LmCUQzxLJJJ1qrXiXutEM2_w2";

    @Autowired
    private AKQJ10Remote akqj10Remote;

    @Test
    public void listFollow() throws URISyntaxException {
        System.out.println(akqj10Remote.listFollow(new URI("http://pop.10jqka.com.cn"), new HashMap<>()));
    }
}