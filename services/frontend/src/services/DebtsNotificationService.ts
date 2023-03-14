import {EmployeeDetailsWorklogDebts} from "../models/worklogdebt/EmployeeDetailsWorklogDebts";
import {EventSourceMessage, fetchEventSource} from "@microsoft/fetch-event-source";
import {EmployeeDetailsMapper} from "../mappers/EmployeeDetailsMapper";

export default class DebtsNotificationService {
    static async sendNotifications(detailsWorklogDebts: EmployeeDetailsWorklogDebts[],
                                   onMessage: (event: EventSourceMessage) => void,
                                   onError: (error: any) => void,
                                   onClose: () => void) {
        const debts = EmployeeDetailsMapper.mapToEmployeeDebts(detailsWorklogDebts);
        await fetchEventSource('http://localhost:8100/stream-sse', {
            method: 'POST',
            body: JSON.stringify(debts),
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
