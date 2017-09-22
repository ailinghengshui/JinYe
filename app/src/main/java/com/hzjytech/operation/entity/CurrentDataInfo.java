package com.hzjytech.operation.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hehongcan on 2017/5/16.
 */
public class CurrentDataInfo implements Parcelable {

    /**
     * topTen : [{"machineId":288,"machineName":"浆液型机器测试","vmTypeName":"jijia-3","location":"江干区白杨街道保利·江语海","groupName":"明炜浆液型","longitude":"120.377805","latitude":"30.310303","time":"2017-02-21 15:07:08.0","sum":0.25,"num":28},{"machineId":57,"machineName":"test0007","vmTypeName":"jijia-3","location":"技术部沈杰测试专用","groupName":"技术部测试","longitude":"120.377768","latitude":"30.310294","time":"2016-07-07 14:45:07.0","sum":0.04,"num":4},{"machineId":290,"machineName":"2017-2-27","vmTypeName":"jijia-3","location":"江干区白杨街道野风·海天城","groupName":"2017-1-20","longitude":"120.377774","latitude":"30.310292","time":"2017-02-27 13:50:47.0","sum":0.02,"num":2},{"machineId":3,"machineName":"jijia02","vmTypeName":"jijia-1","location":"江干区下沙街道中心路c102号","groupName":"techGrohttps","longitude":"120.3192481871","latitude":"30.313200912091","time":"2016-03-14 15:09:25.0","sum":0,"num":0},{"machineId":4,"machineName":"jijia0101","vmTypeName":"jijia-1","location":"海宁市长安镇杭州九桥高尔夫俱乐部","groupName":"techGrohttps","longitude":"120.40348327344","latitude":"30.338543252482","time":"2016-03-15 10:07:35.0","sum":0,"num":0},{"machineId":7,"machineName":"jj05710001","vmTypeName":"jijia-1","location":"江干区白杨街道杭州市高科技企业孵化园区","groupName":"techGrohttps","longitude":"120.37811515677","latitude":"30.30970565482","time":"2016-03-15 17:22:16.0","sum":0,"num":0},{"machineId":8,"machineName":"jj05710002","vmTypeName":"jijia-3","location":"江干区白杨街道文海南路(地铁站)","groupName":"美味菜单","longitude":"120.38249905563","latitude":"30.316985051364","time":"2016-03-16 09:51:36.0","sum":0,"num":0},{"machineId":9,"machineName":"jj05710003","vmTypeName":"jijia-3","location":"江干区白杨街道杭州市高科技企业孵化园区","groupName":"yqf-cx","longitude":"120.37811515677","latitude":"30.30970565482","time":"2016-03-16 13:45:22.0","sum":0,"num":0},{"machineId":10,"machineName":"jj05710004","vmTypeName":"jijia-3","location":"俞其峰测试专用","groupName":"testyuqifeng1","longitude":"120.38829073733","latitude":"30.308671161964","time":"2016-03-16 15:18:58.0","sum":0,"num":0},{"machineId":12,"machineName":"jijia234","vmTypeName":"jijia-3","location":"江干区白杨街道科技园路31号","groupName":"nxy","longitude":"120.37527514641","latitude":"30.310844787244","time":"2016-03-24 10:19:18.0","sum":0,"num":0}]
     * bottomTen : [{"machineId":316,"machineName":"test03301","vmTypeName":"jijia-3","location":"孵化器","groupName":"技术部测试","longitude":"120.37811515677","latitude":"30.30970565482","time":"2017-03-30 17:41:11.0","sum":0,"num":0},{"machineId":318,"machineName":"wyj123","vmTypeName":"jijia-3","location":"123","groupName":"测试","longitude":"120.371693","latitude":"30.303411","time":"2017-04-05 13:05:11.0","sum":0,"num":0},{"machineId":319,"machineName":"wyj124","vmTypeName":"jijia-3","location":"qwe","groupName":"测试","longitude":"111","latitude":"120.371693","time":"2017-04-05 13:05:53.0","sum":0,"num":0},{"machineId":321,"machineName":"j检测院","vmTypeName":"jijia-3","location":"萧山","groupName":"聂小云","longitude":"120.371693","latitude":"30.303411","time":"2017-04-07 11:12:52.0","sum":0,"num":0},{"machineId":324,"machineName":"jijia11203","vmTypeName":"jijia-3","location":"庆春路","groupName":"美味菜单","longitude":"120.371693","latitude":"30.303411","time":"2017-04-07 17:14:42.0","sum":0,"num":0},{"machineId":325,"machineName":"妖妖零","vmTypeName":"jijia-3","location":"返回键","groupName":"lltest促销分组","longitude":"120.37779","latitude":"30.310295","time":"2017-04-07 18:46:56.0","sum":0,"num":0},{"machineId":333,"machineName":"567","vmTypeName":"jijia-3","location":"wasa","groupName":"聂小云","longitude":"120.371693","latitude":"30.303411","time":"2017-04-12 12:32:14.0","sum":0,"num":0},{"machineId":340,"machineName":"340","vmTypeName":"jijia-3","location":"江干区白杨街道保利·江语海","groupName":"lltest促销分组","longitude":"120.377767","latitude":"30.310272","time":"2017-04-17 18:12:31.0","sum":0,"num":0},{"machineId":341,"machineName":"111test","vmTypeName":"jijia-3","location":"西雅图","groupName":"菓珍","longitude":"120.371693","latitude":"30.303411","time":"2017-04-18 13:37:35.0","sum":0,"num":0},{"machineId":345,"machineName":"monkey","vmTypeName":"jijia-3","location":"核桃仁和家人假如我是","groupName":"lltest促销分组","longitude":"30","latitude":"120","time":"2017-05-02 15:37:11.0","sum":0,"num":0}]
     * machineNum : 283
     * orderNum : 31
     * goodNum : 34
     * volume : 0.31
     * errors : [{"machineName":"浆液型机器测试","address":"江干区白杨街道保利·江语海","createdAt":"2017-05-15 17:46:43.0","code":621,"recoverAt":"2017-05-15 17:52:00.0","content":"杯子未取走"}]
     * errorTime : 1
     */

    private int machineNum;
    private int orderNum;
    private int goodNum;
    private double volume;
    private int errorTime;
    private List<TenBean> topTen;
    private List<TenBean> bottomTen;
    private List<ErrorsBean> errors;


    public int getMachineNum() {
        return machineNum;
    }

    public void setMachineNum(int machineNum) {
        this.machineNum = machineNum;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public int getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(int errorTime) {
        this.errorTime = errorTime;
    }

    public List<TenBean> getTopTen() {
        return topTen;
    }

    public void setTopTen(List<TenBean> topTen) {
        this.topTen = topTen;
    }

    public List<TenBean> getBottomTen() {
        return bottomTen;
    }

    public void setBottomTen(List<TenBean> bottomTen) {
        this.bottomTen = bottomTen;
    }

    public List<ErrorsBean> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorsBean> errors) {
        this.errors = errors;
    }




    public  static class ErrorsBean implements Parcelable {
        /**
         * machineName : 浆液型机器测试
         * address : 江干区白杨街道保利·江语海
         * createdAt : 2017-05-15 17:46:43.0
         * code : 621
         * Note:sss
         * recoverAt : 2017-05-15 17:52:00.0
         * content : 杯子未取走
         */

        private String machineName;
        private String address;
        private String createdAt;
        private int code;
        private String recoverAt;
        private String content;
        private String note;


        public String getMachineName() {
            return machineName;
        }

        public void setMachineName(String machineName) {
            this.machineName = machineName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getRecoverAt() {
            return recoverAt;
        }

        public void setRecoverAt(String recoverAt) {
            this.recoverAt = recoverAt;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }


        public ErrorsBean() {
        }


        @Override
        public int describeContents() { return 0; }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.machineName);
            dest.writeString(this.address);
            dest.writeString(this.createdAt);
            dest.writeInt(this.code);
            dest.writeString(this.recoverAt);
            dest.writeString(this.content);
            dest.writeString(this.note);
        }

        protected ErrorsBean(Parcel in) {
            this.machineName = in.readString();
            this.address = in.readString();
            this.createdAt = in.readString();
            this.code = in.readInt();
            this.recoverAt = in.readString();
            this.content = in.readString();
            this.note = in.readString();
        }

        public static final Creator<ErrorsBean> CREATOR = new Creator<ErrorsBean>() {
            @Override
            public ErrorsBean createFromParcel(Parcel source) {return new ErrorsBean(source);}

            @Override
            public ErrorsBean[] newArray(int size) {return new ErrorsBean[size];}
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.machineNum);
        dest.writeInt(this.orderNum);
        dest.writeInt(this.goodNum);
        dest.writeDouble(this.volume);
        dest.writeInt(this.errorTime);
        dest.writeTypedList(this.topTen);
        dest.writeTypedList(this.bottomTen);
        dest.writeList(this.errors);
    }

    public CurrentDataInfo() {
    }

    protected CurrentDataInfo(Parcel in) {
        this.machineNum = in.readInt();
        this.orderNum = in.readInt();
        this.goodNum = in.readInt();
        this.volume = in.readDouble();
        this.errorTime = in.readInt();
        this.topTen = in.createTypedArrayList(TenBean.CREATOR);
        this.bottomTen = in.createTypedArrayList(TenBean.CREATOR);
        this.errors = new ArrayList<ErrorsBean>();
        in.readList(this.errors, ErrorsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<CurrentDataInfo> CREATOR = new Parcelable.Creator<CurrentDataInfo>() {
        @Override
        public CurrentDataInfo createFromParcel(Parcel source) {
            return new CurrentDataInfo(source);
        }

        @Override
        public CurrentDataInfo[] newArray(int size) {
            return new CurrentDataInfo[size];
        }
    };
}
