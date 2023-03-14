import axios from "axios";

export default class WorklogDebtsService {
    static async getAllDetails() {
        console.log('Fetching worklog-debts/details')
        const response = await axios.get('http://localhost:8100/worklog-debts/details');
        console.log(`Received worklog-debts/details response, debts count: ${response.data.length}`);
        return response;
    }
}
