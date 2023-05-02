import {EmployeeVacationFormErrors} from "./EmployeeVacationFormErrors";
import {EmployeeVacationFormData} from "../components/pages/vacation/AddEmployeeVacationFormContent";

export class EmployeeVacationFormValidator {
    static validateForm(formData: EmployeeVacationFormData, startDate: Date, endDate: Date): [boolean, EmployeeVacationFormErrors] {
        let isValid = true;
        let errors = {} as EmployeeVacationFormErrors;

        if (formData.name === '') {
            isValid = false;
            errors.name = 'Vacation description is required';
        }

        if (formData.name.length > 255) {
            isValid = false;
            errors.name = 'Vacation description must be less than 256 symbols';
        }

        if (formData.employeeId === 0) {
            isValid = false;
            errors.employeeId = 'Employee is required';
        }

        if (this.daysBetween(startDate, endDate) >= 24) {
            isValid = false;
            errors.date = 'Too many days selected';
        }

        return [isValid, errors];
    };

    static daysBetween(startDate: Date, endDate: Date) {
        const millisecondsPerDay = 1000 * 60 * 60 * 24;
        const start = new Date(startDate).setHours(0, 0, 0, 0);
        const end = new Date(endDate).setHours(0, 0, 0, 0);
        const diffInMilliseconds = end - start;

        return diffInMilliseconds / millisecondsPerDay;
    };
}
