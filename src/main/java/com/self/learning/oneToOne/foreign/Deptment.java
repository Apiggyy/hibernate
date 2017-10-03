package com.self.learning.oneToOne.foreign;

public class Deptment {
    private int deptId;
    private String deptName;
    private Manager manager;

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deptment deptment = (Deptment) o;

        if (deptId != deptment.deptId) return false;
        if (deptName != null ? !deptName.equals(deptment.deptName) : deptment.deptName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = deptId;
        result = 31 * result + (deptName != null ? deptName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Deptment{" +
                "deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", manager=" + manager +
                '}';
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
