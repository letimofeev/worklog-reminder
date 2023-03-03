import {
    ExceptionFilter,
    Catch,
    ArgumentsHost,
    HttpException,
    HttpStatus, Logger,
} from '@nestjs/common';
import {HttpAdapterHost} from '@nestjs/core';
import {ApiError} from "./api-error";

@Catch()
export class UnhandledExceptionFilter implements ExceptionFilter {
    private readonly logger = new Logger(UnhandledExceptionFilter.name);

    constructor(private readonly httpAdapterHost: HttpAdapterHost) {
    }

    catch(exception: Error, host: ArgumentsHost): void {
        this.logger.error(`Unhandled exception ${exception.name}: ${exception.message}\n${exception.stack}`)

        const {httpAdapter} = this.httpAdapterHost;
        const ctx = host.switchToHttp();

        const httpStatus =
            exception instanceof HttpException
                ? exception.getStatus()
                : HttpStatus.INTERNAL_SERVER_ERROR;


        const apiError = new ApiError(httpStatus, 'Unhandled error')

        httpAdapter.reply(ctx.getResponse(), apiError, httpStatus);
    }
}
