package com.zhytnik.bank.web.util;

import com.zhytnik.bank.domain.Banker;
import com.zhytnik.bank.domain.Client;
import com.zhytnik.bank.domain.card.BillCard;

import static java.lang.String.format;

public class Formatter {

    private Formatter() {
    }

    public static String toString(Client c) {
        return format("%s %s", c.getName(), c.getSurname());
    }

    public static String toString(Banker b) {
        return format("%s %s. from %s department", b.getName(), b.getSurname().charAt(0), b.getDepartment().getNumber());
    }

    public static String toString(BillCard c) {
        if(c == null) return "";
        return format("Card[%s]", c.getCode());
    }
}
