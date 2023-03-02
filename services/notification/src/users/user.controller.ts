import {Body, Controller, Get, Post} from '@nestjs/common';
import {UserService} from "./user.service";
import {CreateUserDto} from "./dtos/create-user.dto";

@Controller('/api/users')
export class UserController {
    constructor(private userService: UserService) {}

    @Post()
    create(@Body() userDto: CreateUserDto) {
        return this.userService.create(userDto)
    }

    @Get('/')
    getAll() {
        return this.userService.getAll();
    }
}