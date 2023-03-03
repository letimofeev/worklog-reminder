import {ArgumentMetadata, Injectable, PipeTransform} from "@nestjs/common";
import {plainToInstance} from "class-transformer";
import {validate} from "class-validator";
import {ValidationApiSubError} from "../exceptions/handlers/validation.api-sub-error";
import {ApiError} from "../exceptions/handlers/api-error";
import {ValidationException} from "../exceptions/validation.exception";

@Injectable()
export class ValidationPipe implements PipeTransform<any> {
    async transform(value: any, metadata: ArgumentMetadata): Promise<any> {
        const obj = plainToInstance(metadata.metatype, value);
        const errors = await validate(obj);

        if (errors.length) {
            const validationErrors = errors.map(error =>
                Object.values(error.constraints).map(msg =>
                    new ValidationApiSubError(msg, error.value))
            )
            const apiError = ApiError.badRequest('Validation failed', validationErrors);
            throw new ValidationException(apiError);
        }
        return value;
    }
}