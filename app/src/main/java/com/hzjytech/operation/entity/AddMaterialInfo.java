package com.hzjytech.operation.entity;

import java.util.List;

/**
 * Created by hehongcan on 2017/5/24.
 */
public class AddMaterialInfo {

    /**
     * records : [{"vmId":272,"vmLocation":"江干区白杨街道27号大街杭州歌江维嘉大酒店","materials":[{"vmId":272,"vmName":"测试10","id":444,"name":"水","type":"Material","addvalue":998987,"operator":"何张和","occurTime":"2016-11-30 12:28:15.0","adder":"[苏莉莉]"}]},{"vmId":273,"vmLocation":"江干区下沙街道翁盘路","materials":[{"vmId":273,"vmName":"测试123","id":445,"name":"雀巢奶粉","type":"Material","addvalue":6.67789E9,"operator":"苏莉莉","occurTime":"2016-11-30 13:35:48.0","adder":"[苏莉莉]"},{"vmId":273,"vmName":"测试123","id":446,"name":"椰子粉","type":"Material","addvalue":3,"operator":"苏莉莉","occurTime":"2016-11-30 13:35:48.0","adder":"[苏莉莉]"},{"vmId":273,"vmName":"测试123","id":447,"name":"杯子","type":"Material","addvalue":4,"operator":"苏莉莉","occurTime":"2016-11-30 13:35:48.0","adder":"[苏莉莉]"}]},{"vmId":52,"vmLocation":"江干区白杨街道4号大街169号","materials":[{"vmId":52,"vmName":"2016-6-13","id":435,"name":"水","type":"Material","addvalue":999999,"operator":"苏莉莉","occurTime":"2016-11-30 09:44:07.0","adder":"[yun]"},{"vmId":52,"vmName":"2016-6-13","id":456,"name":"泡沫咖啡奶","type":"Material","addvalue":100,"operator":"苏莉莉","occurTime":"2016-12-01 11:19:00.0","adder":"[yun]"},{"vmId":52,"vmName":"2016-6-13","id":457,"name":"巧克力粉","type":"Material","addvalue":100,"operator":"苏莉莉","occurTime":"2016-12-01 11:19:00.0","adder":"[yun]"},{"vmId":52,"vmName":"2016-6-13","id":458,"name":"雀巢奶粉","type":"Material","addvalue":100,"operator":"苏莉莉","occurTime":"2016-12-01 11:19:01.0","adder":"[yun]"},{"vmId":52,"vmName":"2016-6-13","id":459,"name":"糖","type":"Material","addvalue":100,"operator":"苏莉莉","occurTime":"2016-12-01 11:19:01.0","adder":"[yun]"},{"vmId":52,"vmName":"2016-6-13","id":460,"name":"红糖姜茶","type":"Material","addvalue":100,"operator":"苏莉莉","occurTime":"2016-12-01 11:19:01.0","adder":"[yun]"},{"vmId":52,"vmName":"2016-6-13","id":461,"name":"咖啡豆","type":"Material","addvalue":100,"operator":"苏莉莉","occurTime":"2016-12-01 11:19:01.0","adder":"[yun]"},{"vmId":52,"vmName":"2016-6-13","id":462,"name":"水","type":"Material","addvalue":100,"operator":"苏莉莉","occurTime":"2016-12-01 11:19:01.0","adder":"[yun]"},{"vmId":52,"vmName":"2016-6-13","id":463,"name":"杯子","type":"Material","addvalue":100,"operator":"苏莉莉","occurTime":"2016-12-01 11:19:01.0","adder":"[yun]"},{"vmId":52,"vmName":"2016-6-13","id":464,"name":"杯子","type":"Material","addvalue":50,"operator":"苏莉莉","occurTime":"2016-12-02 14:35:01.0","adder":"[yun]"},{"vmId":52,"vmName":"2016-6-13","id":465,"name":"杯子","type":"Material","addvalue":50,"operator":"苏莉莉","occurTime":"2016-12-02 14:50:35.0","adder":"[yun]"},{"vmId":52,"vmName":"2016-6-13","id":479,"name":"泡沫咖啡奶","type":"Material","addvalue":99999,"operator":"苏莉莉","occurTime":"2016-12-15 13:52:56.0","adder":"[yun]"},{"vmId":52,"vmName":"2016-6-13","id":480,"name":"巧克力粉","type":"Material","addvalue":999999,"operator":"苏莉莉","occurTime":"2016-12-15 13:52:56.0","adder":"[yun]"},{"vmId":52,"vmName":"2016-6-13","id":481,"name":"雀巢奶粉","type":"Material","addvalue":999999,"operator":"苏莉莉","occurTime":"2016-12-15 13:52:56.0","adder":"[yun]"},{"vmId":52,"vmName":"2016-6-13","id":482,"name":"糖","type":"Material","addvalue":999999,"operator":"苏莉莉","occurTime":"2016-12-15 13:52:56.0","adder":"[yun]"},{"vmId":52,"vmName":"2016-6-13","id":483,"name":"红糖姜茶","type":"Material","addvalue":999999,"operator":"苏莉莉","occurTime":"2016-12-15 13:52:56.0","adder":"[yun]"},{"vmId":52,"vmName":"2016-6-13","id":484,"name":"咖啡豆","type":"Material","addvalue":999999,"operator":"苏莉莉","occurTime":"2016-12-15 13:52:56.0","adder":"[yun]"},{"vmId":52,"vmName":"2016-6-13","id":485,"name":"水","type":"Material","addvalue":9999999,"operator":"苏莉莉","occurTime":"2016-12-15 13:52:56.0","adder":"[yun]"},{"vmId":52,"vmName":"2016-6-13","id":486,"name":"杯子","type":"Material","addvalue":999999,"operator":"苏莉莉","occurTime":"2016-12-15 13:52:56.0","adder":"[yun]"}]},{"vmId":57,"vmLocation":"技术部沈杰测试专用","materials":[{"vmId":57,"vmName":"test0007","id":487,"name":"奶茶","type":"Material","addvalue":1,"operator":"何张和","occurTime":"2016-12-16 15:15:42.0","adder":""},{"vmId":57,"vmName":"test0007","id":488,"name":"巧克力粉","type":"Material","addvalue":555,"operator":"何张和","occurTime":"2016-12-16 15:19:33.0","adder":""},{"vmId":57,"vmName":"test0007","id":489,"name":"奶茶","type":"Material","addvalue":88,"operator":"何张和","occurTime":"2016-12-16 15:19:33.0","adder":""},{"vmId":57,"vmName":"test0007","id":490,"name":"椰子粉","type":"Material","addvalue":65,"operator":"何张和","occurTime":"2016-12-16 15:19:33.0","adder":""},{"vmId":57,"vmName":"test0007","id":491,"name":"雀巢奶粉","type":"Material","addvalue":98,"operator":"何张和","occurTime":"2016-12-16 15:19:33.0","adder":""},{"vmId":57,"vmName":"test0007","id":492,"name":"咖啡豆","type":"Material","addvalue":666,"operator":"何张和","occurTime":"2016-12-16 15:19:33.0","adder":""},{"vmId":57,"vmName":"test0007","id":493,"name":"糖","type":"Material","addvalue":98,"operator":"何张和","occurTime":"2016-12-16 15:19:33.0","adder":""}]}]
     * total : 370
     * count : {"香芋":8477,"不不不":1,"香草":6.6723446E7,"红豆薏米":11589,"雀巢奶粉":7.7853358E9,"奶茶":1.3466853E8,"杯子":9.8088987E7,"核桃玄米":446724,"热情芒果":800,"咖啡豆":1.96306471E8,"糖":9.67132802E8,"阿萨姆":22222,"泡沫咖啡奶":100099,"香柚红茶":9999,"香蕉牛奶":33355,"玄米薏仁":45110,"花生牛奶":4446512,"黑芝麻":444645,"植脂末":284452,"德国奶粉":55565,"水":6.665566035577E14,"玉米汁":56509,"妙趣菠萝":800,"巧克力粉":1.0058831272E10,"红糖姜茶":1000099,"哇咔咔奶粉":555565,"椰子粉":8.8645171E7}
     */

    private int total;
    private Object count;
    private List<RecordsBean> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
        this.count = count;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }


    public static class RecordsBean {
        /**
         * vmId : 272
         * vmLocation : 江干区白杨街道27号大街杭州歌江维嘉大酒店
         * materials : [{"vmId":272,"vmName":"测试10","id":444,"name":"水","type":"Material","addvalue":998987,"operator":"何张和","occurTime":"2016-11-30 12:28:15.0","adder":"[苏莉莉]"}]
         */

        private int vmId;
        private String vmLocation;
        private List<MaterialsBean> materials;

        public int getVmId() {
            return vmId;
        }

        public void setVmId(int vmId) {
            this.vmId = vmId;
        }

        public String getVmLocation() {
            return vmLocation;
        }

        public void setVmLocation(String vmLocation) {
            this.vmLocation = vmLocation;
        }

        public List<MaterialsBean> getMaterials() {
            return materials;
        }

        public void setMaterials(List<MaterialsBean> materials) {
            this.materials = materials;
        }

        public static class MaterialsBean {
            /**
             * vmId : 272
             * vmName : 测试10
             * id : 444
             * name : 水
             * type : Material
             * addvalue : 998987.0
             * operator : 何张和
             * occurTime : 2016-11-30 12:28:15.0
             * adder : [苏莉莉]
             */

            private int vmId;
            private String vmName;
            private int id;
            private String name;
            private String type;
            private double addvalue;
            private String operator;
            private String occurTime;
            private String adder;

            public int getVmId() {
                return vmId;
            }

            public void setVmId(int vmId) {
                this.vmId = vmId;
            }

            public String getVmName() {
                return vmName;
            }

            public void setVmName(String vmName) {
                this.vmName = vmName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public double getAddvalue() {
                return addvalue;
            }

            public void setAddvalue(double addvalue) {
                this.addvalue = addvalue;
            }

            public String getOperator() {
                return operator;
            }

            public void setOperator(String operator) {
                this.operator = operator;
            }

            public String getOccurTime() {
                return occurTime;
            }

            public void setOccurTime(String occurTime) {
                this.occurTime = occurTime;
            }

            public String getAdder() {
                return adder;
            }

            public void setAdder(String adder) {
                this.adder = adder;
            }
        }
    }
}
