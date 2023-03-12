import {NotificationStatus} from "./NotificationStatus";

export interface NotificationResponse {
    login: string;
    status: NotificationStatus;
    message: string;
}
