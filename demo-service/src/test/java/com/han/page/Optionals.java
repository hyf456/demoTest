package com.han.page;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @Author: hanyf
 * @Description:
 * @Date: 2019/3/13 16:44
 */
public final class Optionals<T> {

    private static final Optionals<?> EMPTY = new Optionals<>();

    private final T value;

    private Optionals() {
        this.value = null;
    }


    public static <T> Optionals<T> empty() {
        @SuppressWarnings("unchecked")
        Optionals<T> t = (Optionals<T>) EMPTY;
        return t;
    }

    private Optionals(T value) {
        this.value = Objects.requireNonNull(value);
    }

    public static <T> Optionals<T> of(T value) {
        return new Optionals<>(value);
    }

    public static <T> Optionals<T> of(T value, Optionals<T> result) {
        if (null != value) {
            return new Optionals<>(value);
        } else {
            return result;
        }
    }

    public static <T> Optionals<T> of(T value, T other) {
        if (null != value) {
            return new Optionals<>(value);
        } else {
            return new Optionals<>(other);
        }
    }

    public static <T> Optionals<T> of(T value, Supplier<? extends T> other) {
        if (null != value) {
            return new Optionals<>(value);
        } else {
            return new Optionals<>(other.get());
        }
    }


}
