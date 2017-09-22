package com.hzjytech.operation.entity;

/**
 * Created by hehongcan on 2017/6/14.
 */
public class TaskListInfo {

    /**
     * updatedAt : date-time,修改时间
     * region : string,区
     * id : integer,任务编号
     * status : string,状态
     * createdAt : date-time,创建时间
     * machineCode : string,咖啡机编码
     * machineId : 1
     * type : string,任务类型
     * city : string,市
     */

    private Time updatedAt;
    private String region;
    private String id;
    private int status;
    private Time createdAt;
    private String machineCode;
    private int machineId;
    private int type;
    private String city;

    public Time getUpdatedAt() { return updatedAt;}

    public void setUpdatedAt(Time updatedAt) { this.updatedAt = updatedAt;}

    public String getRegion() { return region;}

    public void setRegion(String region) { this.region = region;}

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public int getStatus() { return status;}

    public void setStatus(int status) { this.status = status;}

    public Time getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Time createdAt) {
        this.createdAt = createdAt;
    }

    public String getMachineCode() { return machineCode;}

    public void setMachineCode(String machineCode) { this.machineCode = machineCode;}

    public int getMachineId() { return machineId;}

    public void setMachineId(int machineId) { this.machineId = machineId;}

    public int getType() { return type;}

    public void sepe(int type) { this.type = type;}

    public String getCity() { return city;}

    public void setCity(String city) { this.city = city;}
    public static class Time{

        /**
         * date : 4
         * hours : 13
         * seconds : 10
         * month : 7
         * nanos : 0
         * timezoneOffset : -480
         * year : 117
         * minutes : 31
         * time : 1501824670000
         * day : 5
         */

        private int date;
        private int hours;
        private int seconds;
        private int month;
        private int nanos;
        private int timezoneOffset;
        private int year;
        private int minutes;
        private long time;
        private int day;

        public int getDate() { return date;}

        public void setDate(int date) { this.date = date;}

        public int getHours() { return hours;}

        public void setHours(int hours) { this.hours = hours;}

        public int getSeconds() { return seconds;}

        public void setSeconds(int seconds) { this.seconds = seconds;}

        public int getMonth() { return month;}

        public void setMonth(int month) { this.month = month;}

        public int getNanos() { return nanos;}

        public void setNanos(int nanos) { this.nanos = nanos;}

        public int getTimezoneOffset() { return timezoneOffset;}

        public void setTimezoneOffset(int timezoneOffset) { this.timezoneOffset = timezoneOffset;}

        public int getYear() { return year;}

        public void setYear(int year) { this.year = year;}

        public int getMinutes() { return minutes;}

        public void setMinutes(int minutes) { this.minutes = minutes;}

        public long getTime() { return time;}

        public void setTime(long time) { this.time = time;}

        public int getDay() { return day;}

        public void setDay(int day) { this.day = day;}
    }
}
