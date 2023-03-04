import {HttpException} from "@nestjs/common";
import {ApiError} from "../dtos/api-error";

export class ValidationException extends HttpException {
    apiError: ApiError;

    constructor(response) {
        super(response, response.status);
        this.apiError = response;
    }
}
