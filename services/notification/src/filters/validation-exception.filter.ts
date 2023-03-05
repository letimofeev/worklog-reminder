import {ArgumentsHost, Catch, ExceptionFilter} from "@nestjs/common";
import {Response} from "express";
import {ValidationException} from "../exceptions/validation.exception";

@Catch(ValidationException)
export class ValidationExceptionFilter implements ExceptionFilter {

    catch(exception: ValidationException, host: ArgumentsHost) {
        const ctx = host.switchToHttp();
        const response = ctx.getResponse<Response>();

        const apiError = exception.apiError

        response
            .status(apiError.status)
            .json(apiError);
    }
}
