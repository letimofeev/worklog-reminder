import axios from "axios";
import {NotificationUser} from "../models/notification/NotificationUser";

export default class NotificationService {
    static async updateUser(user: NotificationUser): Promise<NotificationUser> {
        console.log('PATCH api/users');
        await axios.patch('http://localhost:8080/api/users', user);
        return user;
    }

    static async deleteUser(user: NotificationUser) {
        console.log('DELETE api/users');
        await axios.delete(`http://localhost:8080/api/users/${user.id}`);
    }

    static async getAllUsers() {
        console.log('GET api/users')
        const response = await axios.get('http://localhost:8080/api/users');
        return response.data;
    }
}
