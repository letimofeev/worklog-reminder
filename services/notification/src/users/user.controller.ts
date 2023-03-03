import {Body, Controller, Delete, Get, HttpCode, Param, Patch, Post} from '@nestjs/common';
import {UserService} from "./user.service";
import {CreateUserQueryDto} from "./dtos/create-user.query.dto";
import {UpdateUserQueryDto} from "./dtos/update-user.query.dto";
import {RowsUpdatedResponseDto} from "./dtos/rows-updated.response.dto";
import {User} from "./user.model";
import {RowsDeletedResponseDto} from "./dtos/rows-deleted.response.dto";
import {UserIdParamValidationPipe} from "../pipes/user-id-param.validation.pipe";

@Controller('/api/users')
export class UserController {
    constructor(private userService: UserService) {}

    @Post()
    @HttpCode(204)
    create(@Body() userDto: CreateUserQueryDto): Promise<User> {
        return this.userService.create(userDto)
    }

    @Get()
    getAll(): Promise<User[]> {
        return this.userService.getAll();
    }

    @Get(':id')
    getById(@Param('id', UserIdParamValidationPipe) id: number): Promise<User> {
        return this.userService.getById(id);
    }

    @Patch()
    update(@Body() userDto: UpdateUserQueryDto): Promise<RowsUpdatedResponseDto> {
        return this.userService.update(userDto)
            .then(rowsUpdated => new RowsUpdatedResponseDto(rowsUpdated));
    }

    @Delete(':id')
    delete(@Param('id', UserIdParamValidationPipe) id: number): Promise<RowsDeletedResponseDto> {
        return this.userService.deleteById(id)
            .then(rowsDeleted => new RowsDeletedResponseDto(rowsDeleted));
    }
}
