package com.self.learning.oneToOne.foreign;

public class Manager {
    private int mgrId;
    private String mgrName;
    private Deptment deptment;

    public int getMgrId() {
        return mgrId;
    }

    public void setMgrId(int mgrId) {
        this.mgrId = mgrId;
    }

    public String getMgrName() {
        return mgrName;
    }

    public void setMgrName(String mgrName) {
        this.mgrName = mgrName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Manager manager = (Manager) o;

        if (mgrId != manager.mgrId) return false;
        if (mgrName != null ? !mgrName.equals(manager.mgrName) : manager.mgrName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mgrId;
        result = 31 * result + (mgrName != null ? mgrName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "mgrId=" + mgrId +
                ", mgrName='" + mgrName + '\'' +
                ", deptment=" + deptment +
                '}';
    }

    public Deptment getDeptment() {
        return deptment;
    }

    public void setDeptment(Deptment deptment) {
        this.deptment = deptment;
    }
}
