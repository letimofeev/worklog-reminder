import { Body, Controller, Delete, Get, HttpCode, Param, Patch, Post, Query } from "@nestjs/common";
import {UserService} from "./user.service";
import {CreateUserDto} from "./dtos/create-user.dto";
import {UpdateUserDto} from "./dtos/update-user.dto";
import {UpdateUserResponseDto} from "./dtos/update-user.response.dto";
import {User} from "./user.model";
import {DeleteUserResponseDto} from "./dtos/delete-user.response.dto";
import {UserIdParamValidationPipe} from "../../pipes/user-id-param.validation.pipe";
import {UserNotFoundException} from "../../exceptions/user-not-found.exception";
import {ApiError} from "../../api-errors/api-error";

@Controller('/api/users')
export class UserController {
    constructor(private userService: UserService) {
    }

    @Post()
    @HttpCode(204)
    create(@Body() userDto: CreateUserDto): Promise<User> {
        return this.userService.create(userDto);
    }

    @Get()
    findAll(@Query('login') logins: string): Promise<User[]> {
        if (logins) {
            const loginList = logins.split(',');
            return this.userService.findAllByLogins(loginList);
        }
        return this.userService.findAll();
    }

    @Get(':id')
    findById(@Param('id', UserIdParamValidationPipe) id: number): Promise<User> {
        return this.userService.findById(id)
            .then(user => {
                if (user != null) {
                    return user;
                }
                const apiError = ApiError.notFound(`User with id = '${id}' not found`);
                throw new UserNotFoundException(apiError);
            });
    }

    @Patch()
    update(@Body() userDto: UpdateUserDto): Promise<UpdateUserResponseDto> {
        return this.userService.update(userDto)
            .then(rowsUpdated => new UpdateUserResponseDto(rowsUpdated));
    }

    @Delete(':id')
    delete(@Param('id', UserIdParamValidationPipe) id: number): Promise<DeleteUserResponseDto> {
        return this.userService.deleteById(id)
            .then(rowsDeleted => new DeleteUserResponseDto(rowsDeleted));
    }
}
