import {EmployeeWorklogDebts} from "../models/worklogdebt/EmployeeWorklogDebts";
import {EventSourceMessage, fetchEventSource} from "@microsoft/fetch-event-source";

export default class DebtsNotificationService {
    static async sendNotifications(worklogDebts: EmployeeWorklogDebts[],
                                   onMessage: (event: EventSourceMessage) => void,
                                   onError: (error: any) => void,
                                   onClose: () => void) {
        await fetchEventSource('http://localhost:8400/api/worklog-debts-notifications/custom', {
            method: 'POST',
            body: JSON.stringify(worklogDebts),
            headers: {
                'Content-Type': 'application/json'
            },

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
