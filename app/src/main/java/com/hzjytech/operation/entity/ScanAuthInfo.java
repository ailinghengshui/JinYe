package com.hzjytech.operation.entity;

/**
 * Created by hehongcan on 2017/9/7.
 */

public class ScanAuthInfo {

    /**
     * machineId : string,咖啡机id
     * inspectionRole : boolean,巡检权限
     * concentrationRole : boolean,浓度权限
     * signInRole : boolean,签到权限
     * recipeRole : boolean,配料权限
     */

    private String machineId;
    private boolean inspectionRole;
    private boolean concentrationRole;
    private boolean signInRole;
    private boolean recipeRole;

    public String getMachineId() { return machineId;}

    public void setMachineId(String machineId) { this.machineId = machineId;}

    public boolean isInspectionRole() { return inspectionRole;}

    public void setInspectionRole(boolean inspectionRole) { this.inspectionRole = inspectionRole;}

    public boolean isConcentrationRole() { return concentrationRole;}

    public void setConcentrationRole(boolean concentrationRole) { this.concentrationRole =
            concentrationRole;}

    public boolean isSignInRole() { return signInRole;}

    public void setSignInRole(boolean signInRole) { this.signInRole = signInRole;}

    public boolean isRecipeRole() { return recipeRole;}

    public void setRecipeRole(boolean recipeRole) { this.recipeRole = recipeRole;}
}
