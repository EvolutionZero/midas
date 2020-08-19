package com.zero.midas.remote.facade;

import com.zero.midas.exception.MidasException;
import com.zero.midas.remote.AKQJ10Remote;
import com.zero.midas.utils.JsonUtils;
import lombok.Data;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AKQJ10Facade {

    private AKQJ10Remote delegate;

    public AKQJ10Facade(AKQJ10Remote delegate) {
        this.delegate = delegate;
    }

    public Optional<List<String>> listFollow(String cookie) {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Cookie", cookie);
            String result = delegate.listFollow(new URI("http://pop.10jqka.com.cn"), headers);
            if (result.contains("callback(")) {
                String json = result.substring("callback(".length(), result.lastIndexOf(")"));
                List<Follow> follows = JsonUtils.parseList(json, Follow.class);
                return Optional.of(follows.stream().map(Follow::getCode).collect(Collectors.toList()));
            }
            return Optional.empty();
        } catch (URISyntaxException e) {
            throw new MidasException(e);
        }
    }

    public Optional<String> follow(String cookie, String code) {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Cookie", cookie);
            return Optional.ofNullable(delegate.follow(new URI("http://stockpage.10jqka.com.cn"), headers, code));
        } catch (URISyntaxException e) {
            throw new MidasException(e);
        }
    }

//    public Optional<String> unfollow(String cookie, String code) {
//        try {
//            Map<String, String> headers = new HashMap<>();
//            headers.put("Cookie", cookie);
//            return Optional.ofNullable(delegate.unfollow(new URI("http://stockpage.10jqka.com.cn"), headers, code));
//        } catch (URISyntaxException e) {
//            throw new MidasException(e);
//        }
//    }

    @Data
    public static class Follow {
        private String code;
    }

}
