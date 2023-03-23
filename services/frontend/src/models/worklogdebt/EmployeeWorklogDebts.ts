import {DayWorklogDebt} from "./DayWorklogDebt";
import {ExcludedDay} from "./ExcludedDay";

export interface EmployeeWorklogDebts {
    id: number;
    firstName: string;
    lastName: string;
    jiraKey: string;
    skypeLogin: string;
    notificationEnabled: boolean;
    botConnected: boolean;
    dateFrom: string;
    dateTo: string;
    worklogDebtsCount: number;
    excludedDays: ExcludedDay[];
    worklogDebts: DayWorklogDebt[];
}
