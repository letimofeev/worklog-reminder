import {EmployeeDetails} from "./EmployeeDetails";
import {DayWorklogDebt} from "./DayWorklogDebt";

export class EmployeeDetailsWorklogDebts {
    employeeDetails: EmployeeDetails;
    worklogDebts: DayWorklogDebt[];

    constructor(employeeDetails: EmployeeDetails, worklogDebts: DayWorklogDebt[]) {
        this.employeeDetails = employeeDetails;
        this.worklogDebts = worklogDebts;
    }
}
