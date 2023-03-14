import axios from "axios";

export default class WorklogDebtsService {
    static async getAllDetails() {
        console.log('Fetching worklog-debts/details')
        const host = process.env.NOTIFICATION_HOST || 'http://localhost:8080';
        const response = await axios.get(host + '/worklog-debts/details');
        console.log(`Received worklog-debts/details response, debts count: ${response.data.length}`);
        return response;
    }
}
