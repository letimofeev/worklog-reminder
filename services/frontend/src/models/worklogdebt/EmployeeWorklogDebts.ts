import {DayWorklogDebt} from "./DayWorklogDebt";
import {ExcludedDay} from "./ExcludedDay";
import {EmployeeNotificationStatus} from "./EmployeeNotificationStatus";
import {Region} from "../region/Region";

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
