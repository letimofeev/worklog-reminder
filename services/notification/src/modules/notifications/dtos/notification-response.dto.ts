import {NotificationStatus} from "../notification.status";

export class NotificationResponseDto {
    login: string;
    status: NotificationStatus;
    message: string;

    constructor(login: string, status: NotificationStatus, message: string) {
        this.login = login;
        this.status = status;
        this.message = message;
    }

    static pass(login: string, message: string = 'Notification sent successfully') {
        return new NotificationResponseDto(login, NotificationStatus.PASS, message);
    }

    static failed(login: string, message: string = 'Notification failed') {
        return new NotificationResponseDto(login, NotificationStatus.FAILED, message);
    }
}
