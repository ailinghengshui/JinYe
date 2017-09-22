package com.hzjytech.operation.constants;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.hzjytech.operation.constants.Constants.EditType.EDIT_GROUP_FROM_GROUP;
import static com.hzjytech.operation.constants.Constants.EditType.EDIT_MACHINE_FROM_GROUP;
import static com.hzjytech.operation.constants.Constants.GroupClick.ADSSETTING;
import static com.hzjytech.operation.constants.Constants.GroupClick.GROUPADDMORE;
import static com.hzjytech.operation.constants.Constants.GroupClick.GROUPSETTING;
import static com.hzjytech.operation.constants.Constants.GroupClick.MACHINESETTING;
import static com.hzjytech.operation.constants.Constants.GroupClick.MONNEYDISCONUT;
import static com.hzjytech.operation.constants.Constants.GroupClick.MONNEYOFF;
import static com.hzjytech.operation.constants.Constants.GroupClick.PROMOTIONTEXT;
import static com.hzjytech.operation.constants.Constants.GroupRefresh.GROUPREFRESH;
import static com.hzjytech.operation.constants.Constants.InfoClick.DRINKTYPE;
import static com.hzjytech.operation.constants.Constants.InfoClick.GROUP;
import static com.hzjytech.operation.constants.Constants.InfoClick.MENU;
import static com.hzjytech.operation.constants.Constants.InfoClick.ROLE;
import static com.hzjytech.operation.constants.Constants.InfoClick.TIMESET;
import static com.hzjytech.operation.constants.Constants.InfoSb.CLOSE;
import static com.hzjytech.operation.constants.Constants.InfoSb.COFFEEME;
import static com.hzjytech.operation.constants.Constants.InfoSb.LOCK;
import static com.hzjytech.operation.constants.Constants.InfoSb.STATUS;

/**
 * Created by hehongcan on 2017/4/10.
 */
public class Constants {
    public static final int state_error=0;
    public static final int state_lack=1;
    public static final int state_offline=2;
    public static final int state_lock=3;
    public static final int state_unoperation=4;
    public static final int state_opteration=5;
    public static  final int state_single_machine=6;
    public static   final int state_single_group=7;
    public static final int state_single_menu=8;
    public static final int SELECT_MERCHANT_FEED_MARK = 111;
    public static final  int state_my_realse_task=9;
    public static final  int state_remain_task=10;
    public static final  int state_finished_task=11;
    public static final int order_sale_table=1;
    public static final int order_daily=2;
    public static final int orser_daily_money=3;
    public static final int order_source=4;
    public static final int order_tweenty_four_hours=5;
    public static final int order_week_sale=6;
    public static final int sugar_ratio=7;
    public static final int order_repeat_buy=8;
    public static final int drink_buy_raio=9;
    public static final int feed_overview=10;
    public static final int material_waste=11;
    public static final int today_error=12;
    public static final int error_count=13;
    public static final  int task=14;
    public static final int machine_comment=15;
    //普通 浆液
    @StringDef({MachieType.NORMAL,MachieType.SERIFLUX})
    @Retention(RetentionPolicy.SOURCE)
    public  @interface MachieType{
        String NORMAL="normal";
        String SERIFLUX="seriflux";

    }
    @IntDef({GROUP,MENU,TIMESET,DRINKTYPE,ROLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface InfoClick{
        int GROUP=0;
        int MENU=1;
        int TIMESET=2;
        int DRINKTYPE=3;
        int ROLE=4;
    }
    @IntDef({STATUS,LOCK,CLOSE,COFFEEME})
    @Retention(RetentionPolicy.SOURCE)
    public @interface InfoSb{
        int STATUS=0;
        int LOCK=1;
        int CLOSE=2;
        int COFFEEME=3;
    }
    @IntDef({PROMOTIONTEXT,MONNEYOFF,MONNEYDISCONUT,ADSSETTING,GROUPSETTING,MACHINESETTING,GROUPADDMORE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GroupClick{
        int PROMOTIONTEXT=0;
        int MONNEYOFF=1;
        int MONNEYDISCONUT=2;
        int ADSSETTING=3;
        int GROUPSETTING=4;
        int MACHINESETTING=5;
        int GROUPADDMORE=6;
        int MACHINEADDMORE=7;
    }
    @StringDef({GROUPREFRESH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GroupRefresh{
        String GROUPREFRESH ="grouprefresh";
    }
    @StringDef({EDIT_GROUP_FROM_GROUP,EDIT_MACHINE_FROM_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EditType{
        String EDIT_GROUP_FROM_GROUP ="edit_group_from_group";
        String EDIT_MACHINE_FROM_GROUP ="edit_machine_from_group";
    }
}
