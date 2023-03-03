import {HttpException, HttpStatus} from "@nestjs/common";
import {ApiError} from "../dtos/api-error";

export class ValidationException extends HttpException {
    apiError: ApiError;

    constructor(response) {
        super(response, HttpStatus.BAD_REQUEST);
        this.apiError = response;
    }
}
