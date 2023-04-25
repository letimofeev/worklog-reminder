import axios from "axios";

export default class RegionService {
    static async getAllRegions() {
        console.log('Fetching api/regions')
        // const response = await axios.get('http://localhost:8080/api/regions');
        // console.log(`Received api/regions response, regions count: ${response.data.length}`);
        // return response;
        await new Promise(resolve => setTimeout(resolve, 1000))
        return [
            {
                id: "b90d4673-09df-43aa-afc0-69e997c4c476",
                name: "Georgia"
            },
            {
                id: "b90d4673-09df-43aa-afc0-69e997c4c479",
                name: "Poland"
            }
        ]
    }
}