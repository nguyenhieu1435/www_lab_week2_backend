package vn.edu.iuh.fit.backend.enums;

public enum EmployeeStatus {
    WORKING(1),
    OnABreak(0),
    NoLongerWorking(-1);

    private final int status;

    private EmployeeStatus(int status){
        this.status = status;
    }
    public int getNumberStatus(){
        return status;
    }

    public static EmployeeStatus fromStatusNumber(int statusNumber){
        return switch (statusNumber) {
            case 1 -> EmployeeStatus.WORKING;
            case 0 -> EmployeeStatus.OnABreak;
            case -1 -> EmployeeStatus.NoLongerWorking;
            default -> EmployeeStatus.OnABreak;
        };
    }


}
