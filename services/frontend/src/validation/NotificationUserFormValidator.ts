import {NotificationUser} from "../models/notification/NotificationUser";
import {NotificationUserFormErrors} from "./NotificationUserFormErrors";

export class NotificationUserFormValidator {
    static validateForm(formData: NotificationUser): [boolean, NotificationUserFormErrors] {
        let isValid = true;
        let errors = {} as NotificationUserFormErrors;

        if (formData.login === '') {
            isValid = false;
            errors.login = 'Skype login is required';
        }

        if (formData.login.length > 64) {
            isValid = false;
            errors.login = 'Skype login must be less than 65 symbols';
        }

        if (formData.displayName.length > 64) {
            isValid = false;
            errors.displayName = 'Display name must be less than 65 symbols';
        }

        return [isValid, errors];
    };
}
