import {Inject, Injectable} from "@nestjs/common";
import {User} from "./user.model";
import {CreateUserDto} from "../dtos/create-user.dto";
import {UpdateUserDto} from "../dtos/update-user.dto";

@Injectable()
export class UserService {
    constructor(@Inject('USER_REPOSITORY') private userRepository: typeof User) {
    }

    async create(userDto: CreateUserDto): Promise<User> {
        return await this.userRepository.create(userDto)
    }

    async findAll(): Promise<User[]> {
        return await this.userRepository.findAll()
    }

    async findAllByLogins(logins: string[]): Promise<User[]> {
        return await this.userRepository.findAll({
            where: {login: logins}
        })
    }

    async findById(id: number): Promise<User> {
        return await this.userRepository.findByPk(id)
    }

    async update(userDto: UpdateUserDto): Promise<number> {
        const {id, ...columnsToUpdate} = userDto
        return this.userRepository.update(columnsToUpdate, {
            where: {id: id}
        }).then(value => value[0])
    }

    async deleteById(id: number): Promise<number> {
        return await this.userRepository.destroy({
            where: {id: id}
        })
    }
}