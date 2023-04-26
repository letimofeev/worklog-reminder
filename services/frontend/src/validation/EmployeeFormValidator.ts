import {CreateEmployeeData} from "../models/employee/CreateEmployeeData";
import {EmployeeFormErrors} from "./EmployeeFormErrors";
import {UpdateEmployeeData} from "../models/employee/UpdateEmployeeData";

export class EmployeeFormValidator {
    static validateForm(formData: CreateEmployeeData | UpdateEmployeeData): [boolean, EmployeeFormErrors] {
        let isValid = true;
        let errors = {} as EmployeeFormErrors;

        if (formData.firstName === '') {
            isValid = false;
            errors.firstName = 'First name is required';
        }

        if (formData.firstName.length > 255) {
            isValid = false;
            errors.firstName = 'First name must be less than 256 symbols';
        }

        if (formData.lastName === '') {
            isValid = false;
            errors.lastName = 'Last name is required';
        }

        if (formData.lastName.length > 255) {
            isValid = false;
            errors.lastName = 'Last name must be less than 256 symbols';
        }

        if (formData.jiraKey === '') {
            isValid = false;
            errors.jiraKey = 'Jira key is required';
        }

        if (formData.jiraKey.length > 64) {
            isValid = false;
            errors.jiraKey = 'Jira key must be less than 64 symbols';
        }

        if (formData.skypeLogin === '') {
            isValid = false;
            errors.skypeLogin = 'Skype login is required';
        }

        if (formData.skypeLogin.length > 64) {
            isValid = false;
            errors.skypeLogin = 'Skype login must be less than 64 symbols';
        }

        if (formData.regionId === '') {
            isValid = false;
            errors.regionId = 'Region is required';
        }

        return [isValid, errors];
    };
}
