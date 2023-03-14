import {EmployeeDetailsWorklogDebts} from "../models/worklogdebt/EmployeeDetailsWorklogDebts";
import {EmployeeWorklogDebts} from "../models/worklogdebt/EmployeeWorklogDebts";
import {EmployeeDetails} from "../models/employee/EmployeeDetails";
import {Employee} from "../models/employee/Employee";

export class EmployeeDetailsMapper {
    static mapToEmployeeDebts(employeeDetailsDebts: EmployeeDetailsWorklogDebts[]): EmployeeWorklogDebts[] {
        return employeeDetailsDebts.map(employeeDetailsDebts => {
            return {
                employee: this.mapToEmployee(employeeDetailsDebts.employeeDetails),
                worklogDebts: employeeDetailsDebts.worklogDebts
            } as EmployeeWorklogDebts
        });
    }

    static mapToEmployee(employeeDetails: EmployeeDetails): Employee {
        const {id, firstName, lastName, jiraKey, skypeLogin} = employeeDetails;
        return {id, firstName, lastName, jiraKey, skypeLogin};
    }
}
