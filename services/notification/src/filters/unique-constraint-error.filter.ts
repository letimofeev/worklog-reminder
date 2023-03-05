import {ArgumentsHost, Catch, ExceptionFilter} from "@nestjs/common";
import {ApiError} from "../dtos/api-error";
import {UniqueConstraintError} from "sequelize";
import {Response} from "express";
import {ValidationApiSubError} from "../dtos/validation.api-sub-error";

@Catch(UniqueConstraintError)
export class UniqueConstraintErrorFilter implements ExceptionFilter {

    catch(exception: UniqueConstraintError, host: ArgumentsHost) {
        const ctx = host.switchToHttp();
        const response = ctx.getResponse<Response>();

        const subErrors = exception.errors.map(error => new ValidationApiSubError(error.message, error.value))
        const apiError = ApiError.conflict('Uniqueness error', subErrors)

        response
            .status(apiError.status)
            .json(apiError);
    }
}
