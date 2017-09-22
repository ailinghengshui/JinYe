package com.hzjytech.operation.utils;

import rx.Subscription;

/**
 * Created by hehongcan on 2017/4/28.
 */
public class CommonUtil {
    /**
     * 注销subscription
     *
     * @param subs
     */
    public static void unSubscribeSubs(Subscription... subs) {
        for (Subscription sub : subs) {
            if (sub != null && !sub.isUnsubscribed()) {
                sub.unsubscribe();
            }
        }
    }
}
