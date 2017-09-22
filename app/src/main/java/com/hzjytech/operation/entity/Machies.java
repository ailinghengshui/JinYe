package com.hzjytech.operation.entity;

import java.util.List;

/**
 * Created by hehongcan on 2017/4/20.
 */
public class Machies {


    /**
     *locked=6.0,
     *offOperation=26.0,
     *sick=221.0,
     *lack=10.0,
     *offline=277.0,
     *operation=282.0,
     *total=282.0,
     */
    private int locked;
    private int offOperation;
    private int sick;
    private int lack;
    private int offline;
    private int operation;
    private int total;
    private List<VendingMachines> vendingMachines;
    private List<List<String>> auth;

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public int getOffOperation() {
        return offOperation;
    }

    public void setOffOperation(int offOperation) {
        this.offOperation = offOperation;
    }

    public int getSick() {
        return sick;
    }

    public void setSick(int sick) {
        this.sick = sick;
    }

    public int getLack() {
        return lack;
    }

    public void setLack(int lack) {
        this.lack = lack;
    }

    public int getOffline() {
        return offline;
    }

    public void setOffline(int offline) {
        this.offline = offline;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<VendingMachines> getVendingMachines() {
        return vendingMachines;
    }

    public void setVendingMachines(List<VendingMachines> vendingMachines) {
        this.vendingMachines = vendingMachines;
    }

    public List<List<String>> getAuth() {
        return auth;
    }

    public void setAuth(List<List<String>> auth) {
        this.auth = auth;
    }

    @Override
    public String toString() {
        return "Machies{" +
                "offOperation=" + offOperation +
                ", sick=" + sick +
                ", lack=" + lack +
                ", offline=" + offline +
                ", total=" + total +
                ", vendingMachines=" + vendingMachines +
                ", auth=" + auth +
                '}';
    }

    public class VendingMachines {

        /**
         * id : 61
         * status : true
         * location : 江干区白杨街道杭州市高科技企业孵化园区
         * is_null : false
         * name : 测试1
         * operation_status : true
         * inventory_flag : false
         * market_group_name : nxy
         * market_group_id : 14
         * menu_name:techGrohttps菜单
         * menu_id：1.0
         * link_status : false
         * is_locked : false
         * lock_auth : true
         * error_auth : true
         * sale_auth : true
         * have_past : false
         */

        private int id;
        private boolean status;
        private String location;
        private boolean is_null;
        private String name;
        private boolean operation_status;
        private boolean inventory_flag;
        private String market_group_name;
        private int market_group_id;
        private String menu_name;
        private int menu_id;
        private boolean link_status;
        private boolean is_locked;
        private boolean lock_auth;
        private boolean error_auth;
        private boolean sale_auth;
        private boolean have_past;

        public String getMenu_name() {
            return menu_name;
        }

        public void setMenu_name(String menu_name) {
            this.menu_name = menu_name;
        }

        public int getMenu_id() {
            return menu_id;
        }

        public void setMenu_id(int menu_id) {
            this.menu_id = menu_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public boolean isIs_null() {
            return is_null;
        }

        public void setIs_null(boolean is_null) {
            this.is_null = is_null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isOperation_status() {
            return operation_status;
        }

        public void setOperation_status(boolean operation_status) {
            this.operation_status = operation_status;
        }

        public boolean isInventory_flag() {
            return inventory_flag;
        }

        public void setInventory_flag(boolean inventory_flag) {
            this.inventory_flag = inventory_flag;
        }

        public String getMarket_group_name() {
            return market_group_name;
        }

        public void setMarket_group_name(String market_group_name) {
            this.market_group_name = market_group_name;
        }

        public int getMarket_group_id() {
            return market_group_id;
        }

        public void setMarket_group_id(int market_group_id) {
            this.market_group_id = market_group_id;
        }

        public boolean isLink_status() {
            return link_status;
        }

        public void setLink_status(boolean link_status) {
            this.link_status = link_status;
        }

        public boolean isIs_locked() {
            return is_locked;
        }

        public void setIs_locked(boolean is_locked) {
            this.is_locked = is_locked;
        }

        public boolean isLock_auth() {
            return lock_auth;
        }

        public void setLock_auth(boolean lock_auth) {
            this.lock_auth = lock_auth;
        }

        public boolean isError_auth() {
            return error_auth;
        }

        public void setError_auth(boolean error_auth) {
            this.error_auth = error_auth;
        }

        public boolean isSale_auth() {
            return sale_auth;
        }

        public void setSale_auth(boolean sale_auth) {
            this.sale_auth = sale_auth;
        }

        public boolean isHave_past() {
            return have_past;
        }

        public void setHave_past(boolean have_past) {
            this.have_past = have_past;
        }

        @Override
        public String toString() {
            return "VendingMachines{" +
                    "id=" + id +
                    ", status=" + status +
                    ", location='" + location + '\'' +
                    ", is_null=" + is_null +
                    ", name='" + name + '\'' +
                    ", operation_status=" + operation_status +
                    ", inventory_flag=" + inventory_flag +
                    ", market_group_name='" + market_group_name + '\'' +
                    ", market_group_id=" + market_group_id +
                    ", link_status=" + link_status +
                    ", is_locked=" + is_locked +
                    ", lock_auth=" + lock_auth +
                    ", error_auth=" + error_auth +
                    ", sale_auth=" + sale_auth +
                    ", have_past=" + have_past +
                    '}';
        }
    }
}
