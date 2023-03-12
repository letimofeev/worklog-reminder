import {EmployeeDetails} from "../employee/EmployeeDetails";
import {DayWorklogDebt} from "./DayWorklogDebt";

export interface EmployeeDetailsWorklogDebts {
    employeeDetails: EmployeeDetails;
    worklogDebts: DayWorklogDebt[];
}
