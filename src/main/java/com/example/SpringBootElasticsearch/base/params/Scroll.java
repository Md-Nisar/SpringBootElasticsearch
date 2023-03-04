package com.example.SpringBootElasticsearch.base.params;

public enum Scroll {

    SIZE(2),
    INTERVAL(1);

    public final int value;

    private Scroll(int value) {
        this.value = value;
    }


}
