package com.zero.midas.remote.facade;

import com.zero.midas.remote.AKQJ10Remote;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AKQJ10Facade {

    private AKQJ10Remote delegate;

    public AKQJ10Facade(AKQJ10Remote delegate) {
        this.delegate = delegate;
    }

    public Optional<String> listFollow(String cookie) {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Cookie", cookie);
            return Optional.of(delegate.listFollow(new URI("http://pop.10jqka.com.cn"), headers));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
