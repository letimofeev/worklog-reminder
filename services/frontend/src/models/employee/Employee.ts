import {EmployeeNotificationStatus} from "../worklogdebt/EmployeeNotificationStatus";
import {Region} from "../region/Region";

export interface Employee {
    id: number;
    firstName: string;
    lastName: string;
    jiraKey: string;
    skypeLogin: string;
    region: Region;
    notificationStatus: EmployeeNotificationStatus;
}
