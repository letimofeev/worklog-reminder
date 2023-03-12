import {NotificationStatus} from "./NotificationStatus";

export class NotificationResponse {
    login: string;
    status: NotificationStatus;
    message: string;

    constructor(login: string, status: NotificationStatus, message: string) {
        this.login = login;
        this.status = status;
        this.message = message;
    }
}
