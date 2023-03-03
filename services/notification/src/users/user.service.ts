import {Inject, Injectable} from "@nestjs/common";
import {User} from "./user.model";
import {CreateUserQueryDto} from "./dtos/create-user.query.dto";
import {UpdateUserQueryDto} from "./dtos/update-user.query.dto";

@Injectable()
export class UserService {
    constructor(@Inject('USER_REPOSITORY') private userRepository: typeof User) {
    }

    async create(userDto: CreateUserQueryDto): Promise<User> {
        return await this.userRepository.create(userDto)
    }

    async getAll(): Promise<User[]> {
        return await this.userRepository.findAll()
    }

    async getById(id: number): Promise<User> {
        return await this.userRepository.findByPk(id)
    }

    async update(userDto: UpdateUserQueryDto): Promise<number> {
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