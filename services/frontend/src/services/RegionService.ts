import axios from "axios";

export default class RegionService {
    static async getAllRegions() {
        console.log('GET api/regions')
        const response = await axios.get('http://localhost:8080/api/regions');
        console.log(`Received api/regions response, regions count: ${response.data.length}`);
        return response.data;
    }
}