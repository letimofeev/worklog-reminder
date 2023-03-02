import {Body, Controller, Delete, Get, HttpCode, Param, Patch, Post, UsePipes} from '@nestjs/common';
import {UserService} from "./user.service";
import {CreateUserDto} from "./dtos/create-user.dto";
import {UpdateUserDto} from "./dtos/update-user.dto";
import {RowsUpdatedDto} from "./dtos/rows-updated.dto";
import {User} from "./user.model";
import {RowsDeletedDto} from "./dtos/rows-deleted.dto";
import {ValidationPipe} from "../pipes/validation.pipe";

@Controller('/api/users')
export class UserController {
    constructor(private userService: UserService) {}

    @UsePipes(ValidationPipe)
    @Post()
    @HttpCode(204)
    create(@Body() userDto: CreateUserDto): Promise<User> {
        return this.userService.create(userDto)
    }

    @Get()
    getAll(): Promise<User[]> {
        return this.userService.getAll();
    }

    @Get(':id')
    getById(@Param('id') id: number): Promise<User> {
        return this.userService.getById(id);
    }

    @UsePipes(ValidationPipe)
    @Patch()
    update(@Body() userDto: UpdateUserDto): Promise<RowsUpdatedDto> {
        return this.userService.update(userDto)
            .then(rowsUpdated => new RowsUpdatedDto(rowsUpdated));
    }

    @Delete(':id')
    delete(@Param('id') id: number): Promise<RowsDeletedDto> {
        return this.userService.deleteById(id)
            .then(rowsDeleted => new RowsDeletedDto(rowsDeleted));
    }
}
