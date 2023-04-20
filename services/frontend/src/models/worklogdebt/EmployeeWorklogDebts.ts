import {DayWorklogDebt} from "./DayWorklogDebt";
import {ExcludedDay} from "./ExcludedDay";
import {Region} from "./Region";
import {EmployeeNotificationStatus} from "./EmployeeNotificationStatus";

export interface EmployeeWorklogDebts {
    id: number;
    firstName: string;
    lastName: string;
    jiraKey: string;
    skypeLogin: string;
    notificationStatus: EmployeeNotificationStatus;
    dateFrom: string;
    dateTo: string;
    region: Region;
    worklogDebtsCount: number;
    excludedDays: ExcludedDay[];
    worklogDebts: DayWorklogDebt[];
}
