import {EmployeeDetailsWorklogDebts} from "../models/EmployeeDetailsWorklogDebts";
import {EventSourceMessage, fetchEventSource} from "@microsoft/fetch-event-source";

export default class DebtsNotificationService {
    static async sendNotifications(debts: EmployeeDetailsWorklogDebts[],
                                   onMessage: (event: EventSourceMessage) => void,
                                   onError: (error: any) => void,
                                   onClose: () => void) {
        const logins = debts.map(employeeDebts => employeeDebts.employeeDetails.skypeLogin).join(',');
        await fetchEventSource('http://localhost:8080/stream-sse?logins=' + logins, {
            async onopen(res) {
                if (res.ok && res.status === 200) {
                    console.log("Connection established ", res);
                } else if (
                    res.status >= 400 &&
                    res.status < 500 &&
                    res.status !== 429
                ) {
                    console.log("Client side error ", res);
                }
            },

            onmessage(event) {
                onMessage(event);
            },

            onclose() {
                onClose();
            },

            onerror(error) {
                onError(error);
            },
        });
    }
}
