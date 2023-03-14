import {DayWorklogDebt} from "./DayWorklogDebt";
import {Employee} from "../employee/Employee";

export interface EmployeeWorklogDebts {
    employee: Employee;
    worklogDebts: DayWorklogDebt[];
}
