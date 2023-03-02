import {Inject, Injectable} from "@nestjs/common";
import {User} from "./user.model";
import {CreateUserDto} from "./dtos/create-user.dto";

@Injectable()
export class UserService {
    constructor(@Inject('USER_REPOSITORY') private userRepository: typeof User) {
    }

    async create(userDto: CreateUserDto): Promise<User> {
        return await this.userRepository.create(userDto)
    }

    async getAll() {
    }

    async getById(id) {
    }

    async update(user) {
    }

    async deleteById(id) {
    }
}