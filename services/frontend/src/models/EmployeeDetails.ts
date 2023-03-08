export class EmployeeDetails {
    id: number;
    firstName: string;
    lastName: string;
    jiraKey: string;
    skypeLogin: string;
    notificationEnabled: boolean;
    botConnected: boolean;

    constructor(
        id: number,
        firstName: string,
        lastName: string,
        jiraKey: string,
        skypeLogin: string,
        notificationEnabled: boolean,
        botConnected: boolean
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jiraKey = jiraKey;
        this.skypeLogin = skypeLogin;
        this.notificationEnabled = notificationEnabled;
        this.botConnected = botConnected;
    }
}
